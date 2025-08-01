package com.kianzhu.activitycast.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityResult {

    @SerializedName("results")
    @Expose
    private List<CityResultInd> results;
    @SerializedName("generationtime_ms")
    @Expose
    private Double generationtimeMs;

    public List<CityResultInd> getResults() {
        return results;
    }

    public void setResults(List<CityResultInd> results) {
        this.results = results;
    }

    public Double getGenerationtimeMs() {
        return generationtimeMs;
    }

    public void setGenerationtimeMs(Double generationtimeMs) {
        this.generationtimeMs = generationtimeMs;
    }

}