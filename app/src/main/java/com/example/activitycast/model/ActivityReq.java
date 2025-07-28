package com.example.activitycast.model;

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

    protected ActivityReq(Parcel in) {
        id = in.readInt();
        name = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
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
        return "" + year + "-" + month + "-" + date;
    }

    public void setDateStringISO(String dateStringISO) {
        this.dateStringISO = dateStringISO;
    }

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

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

    private String dateStringISO;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
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
        dest.writeString(dateStringISO);
    }
}
