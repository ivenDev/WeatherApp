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


 /** Класс модели. Здесь реализованаы методы работы с БД и запросов в сеть*/

// TODO: 27.03.2019 подумать, как сделать, чтобы в БД название города на русском
public class Model extends BaseModel<CityWeather> {

    private CitiesDB mDB;

    public Model() {
        mDB = CitiesDB.getINSTANCE();
    }

     //region network methods
     //Метод запроса в сеть по названию города. В ответе приходит Single<CityWeather> объект
    @Override
    public Single<CityWeather> getWeatherWithCityName(String cityName) {
        return WeatherMapApi.getInstance().getApi().getWeatherWithCityName(cityName);
    }
     //endregion

     //region methods for work with DB
     //Метод запроса в БД для получения списка всех городов находящейся в ней.
    public Observable<List<City>> getCities(){
        return mDB.citiesDao().getAllCities();
    }

    public Single<List<City>> getCitiesSingle(){
        return mDB.citiesDao().getAllCitiesSingle();
    }

    //Метод добавления нового объекта City в БД.
    public Completable insertCity(City city){
        return mDB.citiesDao().insertCity(city);
    }

     //Метод добавления списка новых объектов City в БД.
    public Completable insertCities(List<City> cities){
        return mDB.citiesDao().insertCities(cities);
    }

    //Метод удаления города из БД при совпадении Primary Key(название города) во входящем объекте
    // и в БД
    public Completable deleteCity(City city){
        return mDB.citiesDao().deleteCity(city);
    }

    //Метод обновления города в БД, если есть совпадения по Primary Key(название города)
    public Completable updateCity(City city){
        return mDB.citiesDao().updateCity(city);
    }
     //endregion





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
