package com.example.activitycast.serviceapi;

import com.example.activitycast.model.CityResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search")
    Call<CityResult> getCityResults(@Query("name") String cityName);
}
