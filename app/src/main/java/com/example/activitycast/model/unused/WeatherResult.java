package com.example.activitycast.model.unused;

import com.example.activitycast.model.WeatherHourly;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherResult {

    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("generationtime_ms")
    @Expose
    private Double generationtimeMs;
    @SerializedName("utc_offset_seconds")
    @Expose
    private Integer utcOffsetSeconds;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("timezone_abbreviation")
    @Expose
    private String timezoneAbbreviation;
    @SerializedName("elevation")
    @Expose
    private Integer elevation;
    @SerializedName("hourly_units")
    @Expose
    private HourlyUnits hourlyUnits;
    @SerializedName("hourly")
    @Expose
    private WeatherHourly hourly;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getGenerationtimeMs() {
        return generationtimeMs;
    }

    public void setGenerationtimeMs(Double generationtimeMs) {
        this.generationtimeMs = generationtimeMs;
    }

    public Integer getUtcOffsetSeconds() {
        return utcOffsetSeconds;
    }

    public void setUtcOffsetSeconds(Integer utcOffsetSeconds) {
        this.utcOffsetSeconds = utcOffsetSeconds;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezoneAbbreviation() {
        return timezoneAbbreviation;
    }

    public void setTimezoneAbbreviation(String timezoneAbbreviation) {
        this.timezoneAbbreviation = timezoneAbbreviation;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public HourlyUnits getHourlyUnits() {
        return hourlyUnits;
    }

    public void setHourlyUnits(HourlyUnits hourlyUnits) {
        this.hourlyUnits = hourlyUnits;
    }

    public WeatherHourly getHourly() {
        return hourly;
    }

    public void setHourly(WeatherHourly hourly) {
        this.hourly = hourly;
    }

}