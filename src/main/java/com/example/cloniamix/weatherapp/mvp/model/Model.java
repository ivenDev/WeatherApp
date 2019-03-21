package com.example.cloniamix.weatherapp.mvp.model;


import com.example.cloniamix.weatherapp.RoomDB.CitiesDB;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

// TODO: 21.03.2019 реализовать RxJava
// TODO: 21.03.2019 реализовать реактивное добавление в БД
public class Model {

    private CitiesDB mDB;

    public Model() {
        mDB = CitiesDB.getINSTANCE();
        initDB();
    }

    public Flowable<List<City>> getCities(){
        return mDB.citiesDao().getAllCities();
    }

    public void insertCiry(City city){
        mDB.citiesDao().addCity(city);
    }

    public Completable deleteCity(City city){
        return mDB.citiesDao().deleteCity(city);
    }

    public Completable updateCity(List<City> cities){
        return mDB.citiesDao().updateCities(cities);
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
