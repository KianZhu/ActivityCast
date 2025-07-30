package com.example.activitycast.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AqiHourly {

    @SerializedName("time")
    @Expose
    private List<String> time;
    @SerializedName("us_aqi")
    @Expose
    private List<Integer> usAqi;

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Integer> getUsAqi() {
        return usAqi;
    }

    public void setUsAqi(List<Integer> usAqi) {
        this.usAqi = usAqi;
    }

}