package com.example.activitycast.serviceapi;

import com.example.activitycast.model.CityResult;
import com.example.activitycast.model.unused.AqiResult;
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

    @GET("air-quality")
    Call<AqiResult> getAqiResults(@Query("latitude") float latitude,
                                  @Query("longitude") float longitude,
                                  @Query("hourly") String hourly,
                                  @Query("timezone") String timezone,
                                  @Query("start_date") String startDate,
                                  @Query("end_date") String endDate);

}
