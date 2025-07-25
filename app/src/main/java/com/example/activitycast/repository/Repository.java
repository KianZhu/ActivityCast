package com.example.activitycast.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.example.activitycast.model.ActivityReq;
import com.example.activitycast.room.ActivityReqDao;
import com.example.activitycast.room.ActivityReqDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final ActivityReqDao activityReqDao;
    ExecutorService executor;
    Handler handler;

    public Repository(Application application)
    {
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

    public LiveData<List<ActivityReq>> getAllActivityReq()
    {
        return activityReqDao.getAllActivityReq();
    }
}
