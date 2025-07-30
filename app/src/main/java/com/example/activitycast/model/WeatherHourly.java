package com.example.activitycast.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherHourly {

    @SerializedName("time")
    @Expose
    private List<String> time;
    @SerializedName("temperature_2m")
    @Expose
    private List<Double> temperature2m;
    @SerializedName("rain")
    @Expose
    private List<Float> rain;
    @SerializedName("showers")
    @Expose
    private List<Float> showers;
    @SerializedName("snowfall")
    @Expose
    private List<Float> snowfall;
    @SerializedName("visibility")
    @Expose
    private List<Integer> visibility;
    @SerializedName("wind_speed_10m")
    @Expose
    private List<Double> windSpeed10m;

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Double> getTemperature2m() {
        return temperature2m;
    }

    public void setTemperature2m(List<Double> temperature2m) {
        this.temperature2m = temperature2m;
    }

    public List<Float> getRain() {
        return rain;
    }

    public void setRain(List<Float> rain) {
        this.rain = rain;
    }

    public List<Float> getShowers() {
        return showers;
    }

    public void setShowers(List<Float> showers) {
        this.showers = showers;
    }

    public List<Float> getSnowfall() {
        return snowfall;
    }

    public void setSnowfall(List<Float> snowfall) {
        this.snowfall = snowfall;
    }

    public List<Integer> getVisibility() {
        return visibility;
    }

    public void setVisibility(List<Integer> visibility) {
        this.visibility = visibility;
    }

    public List<Double> getWindSpeed10m() {
        return windSpeed10m;
    }

    public void setWindSpeed10m(List<Double> windSpeed10m) {
        this.windSpeed10m = windSpeed10m;
    }

}