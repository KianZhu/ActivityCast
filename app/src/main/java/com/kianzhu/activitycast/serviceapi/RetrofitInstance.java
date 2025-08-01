package com.kianzhu.activitycast.serviceapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit locationRetrofit = null;
    private static Retrofit weatherRetrofit = null;
    private static Retrofit aqiRetrofit = null;
    private static String LOCATION_BASE_URL = "https://geocoding-api.open-meteo.com/v1/";
    private static String WEATHER_BASE_URL = "https://api.open-meteo.com/v1/";
    private static String AQI_BASE_URL = "https://air-quality-api.open-meteo.com/v1/";



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

    public static ApiService getApiService()
    {
        if (aqiRetrofit == null)
        {
            aqiRetrofit = new Retrofit.Builder()
                    .baseUrl(AQI_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return aqiRetrofit.create(ApiService.class);
    }
}
