package com.example.activitycast.serviceapi;

import com.example.activitycast.model.CityResult;
import com.example.activitycast.model.unused.WeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search")
    Call<CityResult> getCityResults(@Query("name") String cityName);

    @GET("forecast")
    Call<WeatherResult> getWeatherResults(@Query("latitude") float latitude,
                                          @Query("longitude") float longitude,
                                          @Query("hourly") String hourly,
                                          @Query("timezone") String timezone,
                                          @Query("start_date") String startDate,
                                          @Query("end_date") String endDate);
    //https://api.open-meteo.com/v1/forecast?latitude=45.4112&longitude=-75.6981&hourly=temperature_2m,rain,showers,snowfall,visibility,wind_speed_10m&timezone=auto&start_date=2025-08-02&end_date=2025-08-02
}
