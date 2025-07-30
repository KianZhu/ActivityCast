package com.example.activitycast.model.unused;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HourlyUnits {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("temperature_2m")
    @Expose
    private String temperature2m;
    @SerializedName("rain")
    @Expose
    private String rain;
    @SerializedName("showers")
    @Expose
    private String showers;
    @SerializedName("snowfall")
    @Expose
    private String snowfall;
    @SerializedName("visibility")
    @Expose
    private String visibility;
    @SerializedName("wind_speed_10m")
    @Expose
    private String windSpeed10m;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature2m() {
        return temperature2m;
    }

    public void setTemperature2m(String temperature2m) {
        this.temperature2m = temperature2m;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getShowers() {
        return showers;
    }

    public void setShowers(String showers) {
        this.showers = showers;
    }

    public String getSnowfall() {
        return snowfall;
    }

    public void setSnowfall(String snowfall) {
        this.snowfall = snowfall;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWindSpeed10m() {
        return windSpeed10m;
    }

    public void setWindSpeed10m(String windSpeed10m) {
        this.windSpeed10m = windSpeed10m;
    }

}