package com.kianzhu.activitycast.worker;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.kianzhu.activitycast.R;
import com.kianzhu.activitycast.model.ActivityReq;
import com.kianzhu.activitycast.model.AqiHourly;
import com.kianzhu.activitycast.model.WeatherHourly;
import com.kianzhu.activitycast.model.unused.AqiResult;
import com.kianzhu.activitycast.model.unused.WeatherResult;
import com.kianzhu.activitycast.repository.Repository;
import com.kianzhu.activitycast.view.MainActivity;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MultiActivityWorker extends Worker {

    ArrayList<ActivityReq> activityReqs = new ArrayList<>();
    Repository repository;
    private static final String CHANNEL_ID = "notify_channel";

    public MultiActivityWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repository = new Repository((Application) context.getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {
        Boolean needsNotification = getInputData().getBoolean("needsNotification", false);
        WeatherResult weatherResult;
        AqiResult aqiResult;
        List<ActivityReq> activityReqsList = repository.getAllActivityReqNonLD();
        activityReqs.addAll(activityReqsList);

        if (activityReqs.isEmpty())
        {
            WorkManager.getInstance(getApplicationContext())
                    .cancelUniqueWork("NotifyUserWork");
            return Result.success();
        }

        int activitesProcessed=0;

        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue(); // Month is 1-indexed (1 for January, 12 for December)
        int day = currentDate.getDayOfMonth();
        for (ActivityReq activityReq : activityReqs)
        {
            //Check if is outdated
            if ((year > activityReq.getYear()) ||
                    (year == activityReq.getYear() && month > activityReq.getMonth()) ||
                    (year == activityReq.getYear() && month == activityReq.getMonth() && day > activityReq.getDate())){
                continue;
            }
            activitesProcessed++;

            Boolean isConflict = activityReq.isConflict();
            try {
                weatherResult = repository.getWeatherResult(activityReq.getLatitude(), activityReq.getLongitude(), activityReq.getDateStringISO());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            WeatherHourly weatherHourly = weatherResult.getHourly();
            activityReq.setConflict(false);
            activityReq.setMinTempForecasted(getMinTemp(weatherHourly, activityReq));
            activityReq.setMaxTempForecasted(getMaxTemp(weatherHourly, activityReq));
            activityReq.setRainForecasted(getRain(weatherHourly, activityReq));
            activityReq.setSnowForecasted(getSnow(weatherHourly, activityReq));
            activityReq.setVisibilityForecasted(getMinVisibility(weatherHourly, activityReq));
            activityReq.setWindSpeedForecasted(getWorstWindSpeed(weatherHourly, activityReq));

            if (activityReq.isAqiAvailable())
            {
                try {
                    aqiResult = repository.getAqiResult(activityReq.getLatitude(), activityReq.getLongitude(), activityReq.getDateStringISO());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                AqiHourly aqiHourly = aqiResult.getHourly();
                activityReq.setAqiForecasted(getAqi(aqiHourly, activityReq));
            }

            Boolean newIsConflict = activityReq.isConflict();
            repository.updateActivityReq(activityReq);

            if (needsNotification && (isConflict != newIsConflict))
            {
                String title;
                if (newIsConflict){
                    title = "Updated forecast: UNIDEAL for one of your activities";
                } else {
                    title = "Updated forecast: ideal now for one of your activities";
                }
                createNotificationChannel();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                PendingIntent pendingIntent = PendingIntent.getActivity(
                        getApplicationContext(),
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher_foreground2)
                        .setContentTitle(title)
                        .setContentText("Check new forecast now")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)   // attach pending intent
                        .setAutoCancel(true);    ;
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                notificationManager.notify((int) System.currentTimeMillis(), builder.build());
                needsNotification = false;
            }
        }
        if (activitesProcessed == 0)
        {
            WorkManager.getInstance(getApplicationContext())
                    .cancelUniqueWork("NotifyUserWork");
        }
        return Result.success();
    }


    private double getMinTemp(WeatherHourly weatherHourly, ActivityReq activityReq)
    {
        List <Double> tempList = weatherHourly.getTemperature2m();
        Double minTemp = 1000.0;
        for (int i = activityReq.getStartHour(); i <= activityReq.getEndHour(); i++)
        {
            Double temp = tempList.get(i);
            if (temp < minTemp) minTemp = temp;
        }
        if ((minTemp.intValue()) < activityReq.getMinTemp()) activityReq.setConflict(true);
        return minTemp;
    }

    private double getMaxTemp(WeatherHourly weatherHourly, ActivityReq activityReq)
    {
        List <Double> tempList = weatherHourly.getTemperature2m();
        Double maxTemp = -1000.0;
        for (int i = activityReq.getStartHour(); i <= activityReq.getEndHour(); i++)
        {
            Double temp = tempList.get(i);
            if (temp > maxTemp) maxTemp = temp;
        }
        if (maxTemp.intValue() > activityReq.getMaxTemp()) activityReq.setConflict(true);
        return maxTemp;
    }

    private boolean getRain(WeatherHourly weatherHourly, ActivityReq activityReq)
    {
        List <Float> rainList = weatherHourly.getRain();
        for (int i = activityReq.getStartHour(); i <= activityReq.getEndHour(); i++)
        {
            Float rainMM = rainList.get(i);
            if (rainMM != 0)
            {
                if (activityReq.isRain()) activityReq.setConflict(true);
                return true;
            }
        }
        return false;
    }

    private boolean getSnow(WeatherHourly weatherHourly, ActivityReq activityReq)
    {
        List <Float> snowList = weatherHourly.getSnowfall();
        for (int i = activityReq.getStartHour(); i <= activityReq.getEndHour(); i++)
        {
            Float snowMM = snowList.get(i);
            if (snowMM != 0)
            {
                if (activityReq.isSnow()) activityReq.setConflict(true);
                return true;
            }
        }
        return false;
    }

    private int getMinVisibility(WeatherHourly weatherHourly, ActivityReq activityReq)
    {
        List <Integer> visList = weatherHourly.getVisibility();
        Integer minVis = 1000000;
        for (int i = activityReq.getStartHour(); i <= activityReq.getEndHour(); i++)
        {
            Integer vis = visList.get(i);
            if (vis < minVis) minVis = vis;
        }
        if (minVis < activityReq.getVisibility()) activityReq.setConflict(true);
        return minVis;
    }

    private double getWorstWindSpeed(WeatherHourly weatherHourly, ActivityReq activityReq)
    {
        List <Double> windList = weatherHourly.getWindSpeed10m();
        if (!activityReq.isWindSet()) return weatherHourly.getWindSpeed10m().get(activityReq.getStartHour());
        else
        {
            if (activityReq.isWindLow())
            {
                Double maxWindSpeed = -1.0;
                for (int i = activityReq.getStartHour(); i <= activityReq.getEndHour(); i++)
                {
                    Double windSpeed = windList.get(i);
                    if (windSpeed > maxWindSpeed) maxWindSpeed = windSpeed;
                }
                if (maxWindSpeed >= 10) activityReq.setConflict(true);
                return maxWindSpeed;
            }
            else
            {
                Double minWindSpeed = 1000.0;
                for (int i = activityReq.getStartHour(); i <= activityReq.getEndHour(); i++)
                {
                    Double windSpeed = windList.get(i);
                    if (windSpeed < minWindSpeed) minWindSpeed = windSpeed;
                }
                if (minWindSpeed < 10) activityReq.setConflict(true);
                return minWindSpeed;
            }
        }
    }

    private int getAqi(AqiHourly aqiHourly, ActivityReq activityReq)
    {
        List<Integer> aqiList = aqiHourly.getUsAqi();
        Integer maxAqi = -1;
        for (int i = activityReq.getStartHour(); i <= activityReq.getEndHour(); i++)
        {
            Integer aqi = aqiList.get(i);
            if (aqi == null) continue;
            if (aqi > maxAqi) maxAqi = aqi;
        }
        if (maxAqi == -1) activityReq.setAqiAvailable(false);
        return maxAqi;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notify Channel";
            String description = "Channel for periodic notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager manager = getApplicationContext().getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
