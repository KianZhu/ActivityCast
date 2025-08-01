package com.kianzhu.activitycast.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "activityreq_table")
public class ActivityReq implements Parcelable {

    public ActivityReq() {
    }

//    protected ActivityReq(Parcel in) {
//        id = in.readInt();
//        name = in.readString();
//        latitude = in.readFloat();
//        longitude = in.readFloat();
//        year = in.readInt();
//        month = in.readInt();
//        date = in.readInt();
//        startHour = in.readInt();
//        endHour = in.readInt();
//        minTemp = in.readInt();
//        maxTemp = in.readInt();
//        rain = in.readByte() != 0;
//        snow = in.readByte() != 0;
//        visibility = in.readInt();
//        windLow = in.readByte() != 0;
//        windSet = in.readByte() != 0;
//        aqi = in.readInt();
//        notes = in.readString();
//        dateStringISO = in.readString();
//        isConflict = in.readByte() != 0;
//        minTempForecasted = in.readDouble();
//        maxTempForecasted = in.readDouble();
//        rainForecasted = in.readByte() != 0;
//        snowForecasted = in.readByte() != 0;
//        visibilityForecasted = in.readInt();
//        windSpeedForecasted = in.readDouble();
//        aqiForecasted = in.readInt();
//    }

//    public static final Creator<ActivityReq> CREATOR = new Creator<ActivityReq>() {
//        @Override
//        public ActivityReq createFromParcel(Parcel in) {
//            return new ActivityReq(in);
//        }
//
//        @Override
//        public ActivityReq[] newArray(int size) {
//            return new ActivityReq[size];
//        }
//    };

    protected ActivityReq(Parcel in) {
        id = in.readInt();
        name = in.readString();
        latitude = in.readFloat();
        longitude = in.readFloat();
        year = in.readInt();
        month = in.readInt();
        date = in.readInt();
        startHour = in.readInt();
        endHour = in.readInt();
        minTemp = in.readInt();
        maxTemp = in.readInt();
        rain = in.readByte() != 0;
        snow = in.readByte() != 0;
        visibility = in.readInt();
        windLow = in.readByte() != 0;
        windSet = in.readByte() != 0;
        aqi = in.readInt();
        notes = in.readString();
        isConflict = in.readByte() != 0;
        minTempForecasted = in.readDouble();
        maxTempForecasted = in.readDouble();
        rainForecasted = in.readByte() != 0;
        snowForecasted = in.readByte() != 0;
        visibilityForecasted = in.readInt();
        windSpeedForecasted = in.readDouble();
        aqiForecasted = in.readInt();
        aqiAvailable = in.readByte() != 0;
        dateStringISO = in.readString();
    }

    public static final Creator<ActivityReq> CREATOR = new Creator<ActivityReq>() {
        @Override
        public ActivityReq createFromParcel(Parcel in) {
            return new ActivityReq(in);
        }

        @Override
        public ActivityReq[] newArray(int size) {
            return new ActivityReq[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public boolean isRain() {
        return rain;
    }

    public void setRain(boolean rain) {
        this.rain = rain;
    }

    public boolean isSnow() {
        return snow;
    }

    public void setSnow(boolean snow) {
        this.snow = snow;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public boolean isWindLow() {
        return windLow;
    }

    public void setWindLow(boolean windLow) {
        this.windLow = windLow;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isWindSet() {
        return windSet;
    }

    public void setWindSet(boolean windSet) {
        this.windSet = windSet;
    }

    public String getDateStringISO() {
        String monthText = Integer.toString(month);
        String dateText = Integer.toString(date);
        if (month < 10) monthText = "0" + monthText;
        if (date < 10) dateText = "0" + dateText;
        return year + "-" + monthText + "-" + dateText;
    }

    public void setDateStringISO(String dateStringISO) {
        this.dateStringISO = dateStringISO;
    }

    public boolean isConflict() {
        return isConflict;
    }

    public void setConflict(boolean conflict) {
        isConflict = conflict;
    }

    public double getMinTempForecasted() {
        return minTempForecasted;
    }

    public void setMinTempForecasted(double minTempForecasted) {
        this.minTempForecasted = minTempForecasted;
    }

    public double getMaxTempForecasted() {
        return maxTempForecasted;
    }

    public void setMaxTempForecasted(double maxTempForecasted) {
        this.maxTempForecasted = maxTempForecasted;
    }

    public boolean isRainForecasted() {
        return rainForecasted;
    }

    public void setRainForecasted(boolean rainForecasted) {
        this.rainForecasted = rainForecasted;
    }

    public boolean isSnowForecasted() {
        return snowForecasted;
    }

    public void setSnowForecasted(boolean snowForecasted) {
        this.snowForecasted = snowForecasted;
    }

    public int getVisibilityForecasted() {
        return visibilityForecasted;
    }

    public void setVisibilityForecasted(int visibilityForecasted) {
        this.visibilityForecasted = visibilityForecasted;
    }

    public double getWindSpeedForecasted() {
        return windSpeedForecasted;
    }

    public void setWindSpeedForecasted(double windSpeedForecasted) {
        this.windSpeedForecasted = windSpeedForecasted;
    }

    public int getAqiForecasted() {
        return aqiForecasted;
    }

    public void setAqiForecasted(int aqiForecasted) {
        this.aqiForecasted = aqiForecasted;
    }

    public boolean isAqiAvailable() {
        return aqiAvailable;
    }

    public void setAqiAvailable(boolean aqiAvailable) {
        this.aqiAvailable = aqiAvailable;
    }

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "latitude")
    private float latitude;

    @ColumnInfo(name = "longitude")
    private float longitude;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "month")
    private int month;

    @ColumnInfo(name = "date")
    private int date;

    @ColumnInfo(name = "startHour")
    private int startHour;

    @ColumnInfo(name = "endHour")
    private int endHour;

    @ColumnInfo(name = "minTemp")
    private int minTemp;

    @ColumnInfo(name = "maxTemp")
    private int maxTemp;

    @ColumnInfo(name = "rain")
    private boolean rain;

    @ColumnInfo(name = "snow")
    private boolean snow;

    @ColumnInfo(name = "visibility")
    private int visibility;

    @ColumnInfo(name = "windLow")
    private boolean windLow;

    @ColumnInfo(name = "windSet")
    private boolean windSet;

    @ColumnInfo(name = "aqi")
    private int aqi;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "isConflict")
    private boolean isConflict;

    @ColumnInfo(name = "minTempForecasted")
    private double minTempForecasted;

    @ColumnInfo(name = "maxTempForecasted")
    private double maxTempForecasted;

    @ColumnInfo(name = "rainForecasted")
    private boolean rainForecasted;

    @ColumnInfo(name = "snowForecasted")
    private boolean snowForecasted;

    @ColumnInfo(name = "visibilityForecasted")
    private int visibilityForecasted;

    @ColumnInfo(name = "windLowForecasted")
    private double windSpeedForecasted;

    @ColumnInfo(name = "aqiForecasted")
    private int aqiForecasted;

    @ColumnInfo(name = "aqiAvailable")
    private boolean aqiAvailable;

    private String dateStringISO;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(date);
        dest.writeInt(startHour);
        dest.writeInt(endHour);
        dest.writeInt(minTemp);
        dest.writeInt(maxTemp);
        dest.writeByte((byte) (rain ? 1 : 0));
        dest.writeByte((byte) (snow ? 1 : 0));
        dest.writeInt(visibility);
        dest.writeByte((byte) (windLow ? 1 : 0));
        dest.writeByte((byte) (windSet ? 1 : 0));
        dest.writeInt(aqi);
        dest.writeString(notes);
        dest.writeByte((byte) (isConflict ? 1 : 0));
        dest.writeDouble(minTempForecasted);
        dest.writeDouble(maxTempForecasted);
        dest.writeByte((byte) (rainForecasted ? 1 : 0));
        dest.writeByte((byte) (snowForecasted ? 1 : 0));
        dest.writeInt(visibilityForecasted);
        dest.writeDouble(windSpeedForecasted);
        dest.writeInt(aqiForecasted);
        dest.writeByte((byte) (aqiAvailable ? 1 : 0));
        dest.writeString(dateStringISO);
    }


//    @Override
//    public int describeContents() {
//        return 0;
//    }


//    @Override
//    public void writeToParcel(@NonNull Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeString(name);
//        dest.writeFloat(latitude);
//        dest.writeFloat(longitude);
//        dest.writeInt(year);
//        dest.writeInt(month);
//        dest.writeInt(date);
//        dest.writeInt(startHour);
//        dest.writeInt(endHour);
//        dest.writeInt(minTemp);
//        dest.writeInt(maxTemp);
//        dest.writeByte((byte) (rain ? 1 : 0));
//        dest.writeByte((byte) (snow ? 1 : 0));
//        dest.writeInt(visibility);
//        dest.writeByte((byte) (windLow ? 1 : 0));
//        dest.writeByte((byte) (windSet ? 1 : 0));
//        dest.writeInt(aqi);
//        dest.writeString(notes);
//        dest.writeString(dateStringISO);
//        dest.writeByte((byte) (isConflict ? 1 : 0));
//        dest.writeDouble(minTempForecasted);
//        dest.writeDouble(maxTempForecasted);
//        dest.writeByte((byte) (rainForecasted ? 1 : 0));
//        dest.writeByte((byte) (snowForecasted ? 1 : 0));
//        dest.writeInt(visibilityForecasted);
//        dest.writeDouble(windSpeedForecasted);
//        dest.writeInt(aqiForecasted);
//        dest.writeByte((byte) (aqiAvailable ? 1 : 0));
//    }


}
