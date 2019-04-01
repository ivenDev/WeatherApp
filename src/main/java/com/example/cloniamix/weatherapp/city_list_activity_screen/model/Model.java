package com.example.cloniamix.weatherapp.city_list_activity_screen.model;


import com.example.cloniamix.weatherapp.RoomDB.CitiesDB;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.mvp.contract.base_model.BaseModel;
import com.example.cloniamix.weatherapp.mvp.contract.base_model.POJO.CityWeather;
import com.example.cloniamix.weatherapp.mvp.contract.base_model.network.WeatherMapApi;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

// TODO: 27.03.2019 подумать, как сделать, чтобы в БД название города на русском
public class Model extends BaseModel<CityWeather> {

    private CitiesDB mDB;

    public Model() {
        mDB = CitiesDB.getINSTANCE();
        /*initDB();*/
    }

    @Override
    public Single<CityWeather> getWeatherWithCityName(String cityName) {
        return WeatherMapApi.getInstance().getApi().getWeatherWithCityName(cityName);
    }

    public Observable<List<City>> getCities(){
        return mDB.citiesDao().getAllCities();
    }

    public Completable insertCity(City city){
        return mDB.citiesDao().insertCity(city);
    }

    public Completable insertCities(List<City> cities){
        return mDB.citiesDao().insertCities(cities);
    }

    public Completable deleteCity(City city){
        return mDB.citiesDao().deleteCity(city);
    }

    public Completable updateCity(City city){
        return mDB.citiesDao().updateCity(city);
    }





    /*private void initDB(){
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

    }*/

}
