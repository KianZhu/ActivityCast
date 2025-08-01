package com.kianzhu.activitycast.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityResultInd {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("latitude")
    @Expose
    private Float latitude;
    @SerializedName("longitude")
    @Expose
    private Float longitude;
    @SerializedName("elevation")
    @Expose
    private Integer elevation;
    @SerializedName("feature_code")
    @Expose
    private String featureCode;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("admin1_id")
    @Expose
    private Integer admin1Id;
    @SerializedName("admin2_id")
    @Expose
    private Integer admin2Id;
    @SerializedName("admin3_id")
    @Expose
    private Integer admin3Id;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("admin1")
    @Expose
    private String admin1;
    @SerializedName("admin2")
    @Expose
    private String admin2;
    @SerializedName("admin3")
    @Expose
    private String admin3;
    @SerializedName("postcodes")
    @Expose
    private List<String> postcodes;

    private String stateCountry;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getAdmin1Id() {
        return admin1Id;
    }

    public void setAdmin1Id(Integer admin1Id) {
        this.admin1Id = admin1Id;
    }

    public Integer getAdmin2Id() {
        return admin2Id;
    }

    public void setAdmin2Id(Integer admin2Id) {
        this.admin2Id = admin2Id;
    }

    public Integer getAdmin3Id() {
        return admin3Id;
    }

    public void setAdmin3Id(Integer admin3Id) {
        this.admin3Id = admin3Id;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAdmin1() {
        return admin1;
    }

    public void setAdmin1(String admin1) {
        this.admin1 = admin1;
    }

    public String getAdmin2() {
        return admin2;
    }

    public void setAdmin2(String admin2) {
        this.admin2 = admin2;
    }

    public String getAdmin3() {
        return admin3;
    }

    public void setAdmin3(String admin3) {
        this.admin3 = admin3;
    }

    public List<String> getPostcodes() {
        return postcodes;
    }

    public void setPostcodes(List<String> postcodes) {
        this.postcodes = postcodes;
    }

    public String getStateCountry() {
        return admin1 + ", " + country;
    }

    public void setStateCountry(String stateCountry) {
        this.stateCountry = stateCountry;
    }
}