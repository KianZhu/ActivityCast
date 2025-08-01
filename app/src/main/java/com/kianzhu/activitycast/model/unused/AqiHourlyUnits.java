package com.kianzhu.activitycast.model.unused;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AqiHourlyUnits {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("us_aqi")
    @Expose
    private String usAqi;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsAqi() {
        return usAqi;
    }

    public void setUsAqi(String usAqi) {
        this.usAqi = usAqi;
    }

}