package com.example.activitycast.serviceapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit locationRetrofit = null;
    private static String LOCATION_BASE_URL = "https://geocoding-api.open-meteo.com/v1/";

    public static ApiService getLocationApiService()
    {
        if (locationRetrofit == null)
        {
            locationRetrofit = new Retrofit.Builder()
                    .baseUrl(LOCATION_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return locationRetrofit.create(ApiService.class);
    }
}
