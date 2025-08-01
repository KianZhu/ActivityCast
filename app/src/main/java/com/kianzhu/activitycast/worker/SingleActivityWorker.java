package com.kianzhu.activitycast.worker;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.kianzhu.activitycast.model.ActivityReq;
import com.kianzhu.activitycast.model.AqiHourly;
import com.kianzhu.activitycast.model.WeatherHourly;
import com.kianzhu.activitycast.model.unused.AqiResult;
import com.kianzhu.activitycast.model.unused.WeatherResult;
import com.kianzhu.activitycast.repository.Repository;

import java.io.IOException;
import java.util.List;

public class SingleActivityWorker extends Worker {
    private Repository repository;
    private ActivityReq activityReq;
    private WeatherHourly weatherHourly;

    public SingleActivityWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repository = new Repository((Application) context.getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {
        int id = getInputData().getInt("id", -1);
        ActivityReq activityReq;
        if (id != -1)
        {
            activityReq = repository.getActivityReqById(id);
        }
        else
        {
            activityReq = repository.getNewestActivityReq();
        }
        System.out.println(activityReq.getName());
        System.out.println(activityReq.getLatitude());
        System.out.println(activityReq.getLongitude());
        System.out.println(activityReq.getDateStringISO());
        WeatherResult weatherResult;
        AqiResult aqiResult;

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

        repository.updateActivityReq(activityReq);
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
        if (maxAqi > activityReq.getAqi()) activityReq.setConflict(true);
        return maxAqi;
    }
}
