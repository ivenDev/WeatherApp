package com.example.cloniamix.weatherapp.RoomDB.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cities")
public class City {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int cityId;
    private String cityName;
    private String conditions;
    private int tempNow;

    //region getters & setters
    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

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

    public int getTempNow() {
        return tempNow;
    }

    public void setTempNow(int tempNow) {
        this.tempNow = tempNow;
    }
    //endregion
}
