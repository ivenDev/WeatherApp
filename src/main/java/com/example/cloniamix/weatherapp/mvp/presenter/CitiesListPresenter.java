package com.example.cloniamix.weatherapp.mvp.presenter;

import android.util.Log;

import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.mvp.model.Model;
import com.example.cloniamix.weatherapp.mvp.ui.CitiesListActivity;
import com.example.cloniamix.weatherapp.weatherApi.WeatherMapApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

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

        mView.setProgress(true);
        /*getDataFromApi("Саранск");*/
        /*getDataFromApi("Москва");*/
        getDataFromDB();


    }

    private void getDataFromDB(){
        mCompositeDisposable.add(mModel.getCities()
                .delay(1,TimeUnit.SECONDS)// FIXME: 21.03.2019 ВРЕМЕННО!!! убрать после реализации запроса в сеть
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                    mCities = cities;
                    mView.setProgress(false);
                    mView.updateView(mCities);})
        );
    }

    /*private void getDataFromApi(String cityName){
        mCompositeDisposable.add(WeatherMapApi.getInstance()
                .getApi()
                .getWeatherWithCityName(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(city -> {
                    mCitiesFromNet.add(city);
                    mCompositeDisposable.add(mModel.insertCiry(city)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(()-> Log.d(TAG, "getDataFromApi: город: " + city.getCityName() +
                                    " добален из интернета")
                                    ,throwable -> Log.d(TAG, "getDataFromApi: ошибка добавления города из интернета")
                            )
                    );
                    },
                        throwable -> {
                    Log.d(TAG, "getDataFromApi: " + throwable);
                    mView.showToast("Ошибка связи");})
        );
    }*/

    /** вызывать в onStop() activity*/
    public void detach(){
        mCompositeDisposable.dispose();
        mView = null;
        mCities = null;
        mCitiesFromNet = null;
    }
}
