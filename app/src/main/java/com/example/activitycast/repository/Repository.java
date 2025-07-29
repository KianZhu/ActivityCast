package com.example.activitycast.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.activitycast.model.ActivityReq;
import com.example.activitycast.model.CityResult;
import com.example.activitycast.model.CityResultInd;
import com.example.activitycast.room.ActivityReqDao;
import com.example.activitycast.room.ActivityReqDatabase;
import com.example.activitycast.serviceapi.ApiService;
import com.example.activitycast.serviceapi.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;

public class Repository {
    private final ActivityReqDao activityReqDao;
    ExecutorService executor;
    Handler handler;
    private ArrayList<CityResultInd> cities = new ArrayList<>();
    private MutableLiveData<List<CityResultInd>> cityMutableLiveData = new MutableLiveData<>();
    private Application application;

    public Repository(Application application)
    {
        this.application = application;
        ActivityReqDatabase activityReqDatabase = ActivityReqDatabase.getInstance(application);
        this.activityReqDao = activityReqDatabase.getActivityReqDao();
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
    }

    public void addActivityReq(ActivityReq activityReq)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                activityReqDao.insert(activityReq);
            }
        });
    }

    public void deleteActivityReq(ActivityReq activityReq)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                activityReqDao.delete(activityReq);
            }
        });
    }

    public void deleteAllActivityReq() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                activityReqDao.deleteAll();
            }
        });
    }

    public LiveData<List<ActivityReq>> getAllActivityReq()
    {
        return activityReqDao.getAllActivityReq();
    }

    public LiveData<ActivityReq> getNewestActivityReq()
    {
        return activityReqDao.getNewestActivityReq();
    }

    public MutableLiveData<List<CityResultInd>> getCityMutableLiveData(String cityName)
    {
        ApiService locationApiService = RetrofitInstance.getLocationApiService();
        Call<CityResult> call = locationApiService.getCityResults(cityName);
        call.enqueue(new Callback<CityResult>() {
            @Override
            public void onResponse(Call<CityResult> call, retrofit2.Response<CityResult> response) {
                CityResult cityResult = response.body();
                if (cityResult != null && cityResult.getResults() != null)
                {
                    cities = (ArrayList<CityResultInd>) cityResult.getResults();
                    cityMutableLiveData.setValue(cities);
                }
            }

            @Override
            public void onFailure(Call<CityResult> call, Throwable t) {
                Toast.makeText(application, "Search failed, try again later", Toast.LENGTH_SHORT).show();
            }

        });
        return cityMutableLiveData;
    }
}
