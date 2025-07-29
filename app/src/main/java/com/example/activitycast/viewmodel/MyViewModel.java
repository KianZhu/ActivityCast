package com.example.activitycast.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.activitycast.model.ActivityReq;
import com.example.activitycast.model.CityResultInd;
import com.example.activitycast.repository.Repository;

import java.util.List;

public class MyViewModel extends AndroidViewModel {
    private Repository myRepository;
    private LiveData<List<ActivityReq>> allActivityReq;
    private MutableLiveData<List<CityResultInd>> cityMutableLiveData;

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

    public MutableLiveData<List<CityResultInd>> getCityMutableLiveData(String cityName) {
        cityMutableLiveData = myRepository.getCityMutableLiveData(cityName);
        return cityMutableLiveData;
    }

    public LiveData<ActivityReq> getNewestActivityReq()
    {
        return myRepository.getNewestActivityReq();
    }

    public void setCityMutableLiveData(MutableLiveData<List<CityResultInd>> cityMutableLiveData) {
        this.cityMutableLiveData = cityMutableLiveData;
    }

}
