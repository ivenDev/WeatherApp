package com.example.cloniamix.weatherapp.mvp.model;


import com.example.cloniamix.weatherapp.RoomDB.CitiesDB;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;

import java.util.List;

// TODO: 21.03.2019 реализовать RxJava
public class Model {

    private CitiesDB mDB;

    public Model() {
        mDB = CitiesDB.getINSTANCE();
        initDB();
    }

    public List<City> getCities(){
        return mDB.citiesDao().getAllCities();
    }

    public void insertCiry(City city){
        mDB.citiesDao().addCity(city);
    }

    public void deleteCity(City city){
        mDB.citiesDao().deleteCity(city);
    }

    public void updateCity(List<City> cities){
        mDB.citiesDao().updateCities(cities);
    }

    // FIXME: 20.03.2019 ВРЕМЕННО!!!убрать после внедрения RxJava
    private void initDB(){
        City city = new City();
        city.setCityName("Саранск");
        city.setConditions("Солнце");
        city.setTempNow(24);

        City city2 = new City();
        city2.setCityName("Москва");
        city2.setConditions("Облачно");
        city2.setTempNow(15);

        mDB.citiesDao().addCity(city);
        mDB.citiesDao().addCity(city2);
    }
}
