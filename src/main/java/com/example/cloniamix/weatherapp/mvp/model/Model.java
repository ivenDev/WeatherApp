package com.example.cloniamix.weatherapp.mvp.model;


import android.util.Log;

import com.example.cloniamix.weatherapp.RoomDB.CitiesDB;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class Model {

    public static final String TAG = "weatherAppTag";
    private CitiesDB mDB;

    public Model() {
        mDB = CitiesDB.getINSTANCE();
        initDB();
    }

    public Flowable<List<City>> getCities(){
        return mDB.citiesDao().getAllCities();
    }

    public Completable insertCiry(City city){
        return mDB.citiesDao().insertCity(city);
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

        CompositeDisposable disposable = new CompositeDisposable();

        disposable.add(mDB.citiesDao().insertCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()-> Log.d(TAG, "initDB: Город: " + city.getCityName() + " добавлен")
                        ,throwable -> Log.d(TAG, "initDB: Ошибка добавления"))
        );
        disposable.add(mDB.citiesDao().insertCity(city2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()-> Log.d(TAG, "initDB: Город: " + city2.getCityName() + " добавлен")
                        ,throwable -> Log.d(TAG, "initDB: Ошибка добавления"))
        );

    }

}
