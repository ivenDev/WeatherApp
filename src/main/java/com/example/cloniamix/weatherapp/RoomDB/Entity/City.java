package com.example.cloniamix.weatherapp.RoomDB.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cities")
public class City {

    @PrimaryKey
    @ColumnInfo(name = "name")
    @NonNull
    private String cityName = ""; //Название города

    @ColumnInfo(name = "description")
    private String conditions; //Описание погодных условий

    @ColumnInfo(name = "icon")
    private String icon; //Код иконки

    @ColumnInfo(name = "temp")
    private double tempNow; //Температура сейчас

    @ColumnInfo(name = "speed")
    private double windSpeed;

    @ColumnInfo(name = "pressure")
    private double pressure;

    @ColumnInfo(name = "humidity")
    private int humidity;


    public City() {
    }

    public City(@NonNull String cityName) {
        this.cityName = cityName;
    }

    //region getters & setters
    @NonNull
    public String getCityName() {
        return cityName;
    }

    public void setCityName(@NonNull String cityName) {
        this.cityName = cityName;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTempNow() {
        return tempNow;
    }

    public void setTempNow(double tempNow) {
        this.tempNow = tempNow;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    //endregion
}
