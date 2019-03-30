package com.example.cloniamix.weatherapp.mvp.presenter;

import android.util.Log;

import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.Utils;
import com.example.cloniamix.weatherapp.mvp.model.Model;
import com.example.cloniamix.weatherapp.mvp.ui.CitiesListActivity;
import com.example.cloniamix.weatherapp.weatherApi.POJO.CityWeather;
import com.example.cloniamix.weatherapp.weatherApi.WeatherMapApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CitiesListPresenter {

    private Model mModel;
    private List<City> mCities;
    private CitiesListActivity mView;
    private CompositeDisposable mCompositeDisposable;

    public CitiesListPresenter() {
        mModel = new Model();
        mCompositeDisposable = new CompositeDisposable();
        mCities = new ArrayList<>();

    }

    public void loadDBData(){
        getDataFromDB();
        getDataFromApi();
    }

    public void loadNetData(){

    }

    //region methods with view lifecycle
    /**вызывать в onStart() activity*/
    public void attach(CitiesListActivity view){
        mView = view;

    }

    /** вызывать в onStop() activity*/
    public void detach() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
            mView = null;
            mCities = null;

    }
    //endregion


    //region for work with model
    private void getDataFromDB(){
        mView.showProgress();
        Disposable disposable = mModel.getCities()
                .subscribeOn(Schedulers.io())
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateUI
                        ,this::handleError);

        mCompositeDisposable.add(disposable);
    }

    private void getDataFromApi(){

        Disposable disposable = mModel.getCities()
                .subscribeOn(Schedulers.io())
                .map(this::getCitiesName)
                .flatMap(Observable::fromArray)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> queryAPI(s));

        /*Disposable disposable = WeatherMapApi.getInstance()
                .getApi()
                .getWeatherWithCityName(cityName)
                .subscribeOn(Schedulers.io())
                .delay(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cityWeather -> {
                    City city = new City();
                    city.setCityName(cityWeather.getName());
                    city.setTempNow(cityWeather.getMain().getTemp());
                    city.setConditions("");
                    updateCityInDB(city);
                    }
                    ,throwable -> {
                    Log.d(Utils.APP_TAG, "getDataFromApi: " + throwable);
                    mView.showToast("Ошибка связи");});

        */mCompositeDisposable.add(disposable);
    }

    private void queryAPI(String s) {

        Disposable disposable = WeatherMapApi.getInstance()
                .getApi()
                .getWeatherWithCityName(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCityInDB
                        ,throwable -> {
                            Log.d(Utils.APP_TAG, "getDataFromApi: " + throwable);
                            mView.showToast("Ошибка связи");});

        mCompositeDisposable.add(disposable);
    }


    private void updateCityInDB(CityWeather cityWeather) {

        City city = new City();
        city.setCityName(cityWeather.getName());
        city.setTempNow(cityWeather.getMain().getTemp());
        city.setConditions("");
        mCompositeDisposable.add(mModel.updateCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()-> Log.d(Utils.APP_TAG
                        , "CityListPresenter.updateCityInDB: Данные города " + city.getCityName() + " обновлены")
                ,throwable -> Log.d(Utils.APP_TAG
                                , "CityListPresenter.updateCityInDB: Ошибка обновления города "
                                        + city.getCityName()+ throwable.toString() ))
        );
    }
    //endregion

    private void updateUI(@NonNull List<City> cities){
        mCities = cities;
        mView.updateView(cities);
    }

    private void handleError(Throwable throwable){
        Utils.log(throwable.toString());
        mView.showToast("DB error");
    }

    private String[] getCitiesName(List<City> cities){
        ArrayList<String> citiesName = new ArrayList<>();
        for (City city: cities){
            citiesName.add(city.getCityName());
        }
        String[] citiesNameString = new String[citiesName.size()];

        for (int i = 0; i<citiesName.size(); i++) {
            citiesNameString[i] = citiesName.get(i);
        }

        return citiesNameString;
    }

    private String getCityName(City city){
        return city.getCityName();
    }
}
