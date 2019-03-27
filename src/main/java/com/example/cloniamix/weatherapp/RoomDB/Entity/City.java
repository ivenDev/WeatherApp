package com.example.cloniamix.weatherapp.RoomDB.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cities")
public class City {

    /*@PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int cityId;*/
    @PrimaryKey
    @ColumnInfo(name = "name")
    @NonNull
    private String cityName;
    @ColumnInfo(name = "description")
    private String conditions;
    @ColumnInfo(name = "temp")
    private double tempNow;

    //region getters & setters
    /*public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }*/

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public double getTempNow() {
        return tempNow;
    }

    public void setTempNow(double tempNow) {
        this.tempNow = tempNow;
    }
    //endregion
}
