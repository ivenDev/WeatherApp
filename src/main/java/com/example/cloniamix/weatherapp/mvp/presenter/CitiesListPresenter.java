package com.example.cloniamix.weatherapp.mvp.presenter;

import android.util.Log;

import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.mvp.model.Model;
import com.example.cloniamix.weatherapp.mvp.ui.CitiesListActivity;
import com.example.cloniamix.weatherapp.weatherApi.WeatherMapApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

// TODO: 27.03.2019 чистка кода
public class CitiesListPresenter {

    private Model mModel;
    private List<City> mCities;
    private List<City> mCitiesFromNet;
    private CitiesListActivity mView;
    private CompositeDisposable mCompositeDisposable;
    private final String TAG = "MyLogTag";

    public CitiesListPresenter() {
        mModel = new Model();
        mCompositeDisposable = new CompositeDisposable();
        mCities = new ArrayList<>();
        mCitiesFromNet = new ArrayList<>();

    }

    /**вызывать в onStart() activity*/
    public void attach(CitiesListActivity view){
        mView = view;
// TODO: 27.03.2019 сделать проверку на наличие БД, если она есть, то обновить данные, если нет,то загрузить два города
        mView.setProgress(true);
        getDataFromDB();

    }


    private void getDataFromDB(){
        mCompositeDisposable.add(mModel.getCities()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                    mCities = cities;
                    mView.setProgress(false);
                    mView.updateView(mCities);
                    for (City city:mCities){
                        getDataFromApi(city.getCityName());
                    }})
        );
    }

    private void getDataFromApi(String cityName){

       /* WeatherMapApi.getInstance().getApi().getWeatherWithCityName(cityName);*/

        mCompositeDisposable.add(WeatherMapApi.getInstance()
                .getApi()
                .getWeatherWithCityName(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cityWeather -> {
                    City city = new City();
                    city.setCityName(cityWeather.getName());
                    city.setTempNow(cityWeather.getMain().getTemp());
                    city.setConditions("");
                    /*mCitiesFromNet.add(city);*/
                    mModel.updateCity(city);

                    }
                    ,throwable -> {
                    Log.d(TAG, "getDataFromApi: " + throwable);
                    mView.showToast("Ошибка связи");})
        );


    }

    private void get() {
        mCompositeDisposable.add(mModel.insertCity(mCitiesFromNet.get(0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()-> Log.d(TAG, "getDataFromApi: город: " + mCitiesFromNet.get(0).getCityName() +
                                " добален из интернета")
                        ,throwable -> Log.d(TAG, "getDataFromApi: ошибка добавления города из интернета")
                )
        );
    }

    /** вызывать в onStop() activity*/
    public void detach(){
        mCompositeDisposable.dispose();
        mView = null;
        mCities = null;
        mCitiesFromNet = null;
    }


    private  List<String> getCitiesName(List<City> cities){
        ArrayList<String> citiesName = new ArrayList<>();
        for (City city: cities){
            citiesName.add(city.getCityName());
        }
        return citiesName;
    }

    private String getCityName(City city){
        return city.getCityName();
    }
}
