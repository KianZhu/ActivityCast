package com.example.activitycast.serviceapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit locationRetrofit = null;
    private static Retrofit weatherRetrofit = null;
    private static String LOCATION_BASE_URL = "https://geocoding-api.open-meteo.com/v1/";
    private static String WEATHER_BASE_URL = "https://api.open-meteo.com/v1/";
    //https://api.open-meteo.com/v1/forecast?latitude=45.4112&longitude=-75.6981&hourly=temperature_2m,rain,showers,snowfall,visibility,wind_speed_10m&timezone=auto&start_date=2025-08-02&end_date=2025-08-02


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

    public static ApiService getWeatherApiService()
    {
        if (weatherRetrofit == null)
        {
            weatherRetrofit = new Retrofit.Builder()
                    .baseUrl(WEATHER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return weatherRetrofit.create(ApiService.class);
    }
}
