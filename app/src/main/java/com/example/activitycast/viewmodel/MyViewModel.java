package com.example.activitycast.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.activitycast.model.ActivityReq;
import com.example.activitycast.repository.Repository;

import java.util.List;

public class MyViewModel extends AndroidViewModel {
    private Repository myRepository;
    private LiveData<List<ActivityReq>> allActivityReq;

    public MyViewModel(@NonNull Application application)
    {
        super(application);
        this.myRepository = new Repository(application);
    }

    public LiveData<List<ActivityReq>> getAllActivityReq()
    {
        allActivityReq = myRepository.getAllActivityReq();
        return allActivityReq;
    }

    public void addNewActivityReq(ActivityReq activityReq)
    {
        myRepository.addActivityReq(activityReq);
    }

    public void deleteActivityReq(ActivityReq activityReq)
    {
        myRepository.deleteActivityReq(activityReq);
    }

    public void deleteAllActivityReq()
    {
        myRepository.deleteAllActivityReq();
    }

}
