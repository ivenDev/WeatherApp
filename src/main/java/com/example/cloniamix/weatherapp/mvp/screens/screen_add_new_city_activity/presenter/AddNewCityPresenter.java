package com.example.cloniamix.weatherapp.mvp.screens.screen_add_new_city_activity.presenter;

import android.util.Log;

import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.Utils;
import com.example.cloniamix.weatherapp.mvp.contract.base_model.POJO.CityWeather;
import com.example.cloniamix.weatherapp.mvp.contract.base_presenter.BasePresenter;
import com.example.cloniamix.weatherapp.mvp.screens.screen_add_new_city_activity.ui.AddNewCityActivity;
import com.example.cloniamix.weatherapp.mvp.model.Model;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddNewCityPresenter extends BasePresenter<AddNewCityActivity> {

    private Model mModel;
    private CompositeDisposable mCompositeDisposable;

    public AddNewCityPresenter() {
        mModel = new Model();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        mModel = null;
        mCompositeDisposable.clear();
        mCompositeDisposable = null;
    }

    public void onSearchBtnClicked(){
        if (getView().getCityName() != null){
            String cityName = getView().getCityName();
            searchCityWeather(cityName);
        }else getView().showToast("Введите название города");
    }

    public void onCancelBtnClicked(){
        getView().goToActivity();
    }


    private void searchCityWeather(String cityName){
        mCompositeDisposable.add(mModel.getWeatherWithCityName(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cityWeather -> {
                    insertCityInDB(cityWeather);
                    getView().goToActivity();
                    Utils.log("Город: " + cityWeather.getName() + "Температура: " + cityWeather.getMain().getTemp());
                        }
                        , throwable -> {
                    Utils.log(throwable.toString());
                    getView().showToast("Город не найден");
                        }
                ));
    }

    private void insertCityInDB(CityWeather cityWeather) {
        getView().showProgress(true);

        City city = new City();
        city.setCityName(cityWeather.getName());
        city.setTempNow(cityWeather.getMain().getTemp());
        city.setConditions("");
        mCompositeDisposable.add(mModel.insertCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()-> Log.d(Utils.APP_TAG
                        , "Город  " + city.getCityName() + " добавлен")
                        ,throwable -> Log.d(Utils.APP_TAG
                                , "Ошибка добавления города "
                                        + city.getCityName()+ throwable.toString() ))
        );
    }



}
