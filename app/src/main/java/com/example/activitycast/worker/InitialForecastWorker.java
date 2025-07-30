package com.example.activitycast.worker;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.activitycast.model.ActivityReq;
import com.example.activitycast.model.CityResultInd;
import com.example.activitycast.model.WeatherHourly;
import com.example.activitycast.model.unused.WeatherResult;
import com.example.activitycast.repository.Repository;
import com.example.activitycast.view.adapter.LocationAdapter;
import com.example.activitycast.viewmodel.MyViewModel;

import java.io.IOException;
import java.util.List;

public class InitialForecastWorker extends Worker {
    private Repository repository;
    private ActivityReq activityReq;
    private WeatherHourly weatherHourly;

    public InitialForecastWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repository = new Repository((Application) context.getApplicationContext());
    }

    @SuppressLint("WrongThread")
    @NonNull
    @Override
    public Result doWork() {
        activityReq = repository.getNewestActivityReq();
        System.out.println(activityReq.getName());
        System.out.println(activityReq.getLatitude());
        System.out.println(activityReq.getLongitude());
        System.out.println(activityReq.getDateStringISO());
//        weatherHourly = repository.getWeatherMutableLiveData((float)activityReq.getLatitude(), (float)activityReq.getLongitude(), activityReq.getDateStringISO());
//        repository.getWeatherMutableLiveData((float)activityReq.getLatitude(), (float)activityReq.getLongitude(), activityReq.getDateStringISO()).observe(getApplicationContext(), new Observer<WeatherResult>() {
//            @Override
//            public void onChanged(WeatherResult weatherResult) {
//                weatherHourly = weatherResult.getHourly();
//            }
//        });
        WeatherResult weatherResult;
        try {
            weatherResult = repository.getWeatherResult(activityReq.getLatitude(), activityReq.getLongitude(), activityReq.getDateStringISO());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(weatherResult.getHourly().getTemperature2m().get(0));
        return Result.success();
    }
}
