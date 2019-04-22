package com.example.cloniamix.weatherapp.mvp.screens.screen_details.presenter;

import android.content.Intent;
import android.util.Log;

import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.Utils;
import com.example.cloniamix.weatherapp.mvp.contract.base_model.POJO.CityWeather;
import com.example.cloniamix.weatherapp.mvp.contract.base_presenter.BasePresenter;
import com.example.cloniamix.weatherapp.mvp.model.Model;
import com.example.cloniamix.weatherapp.mvp.screens.screen_city_list_activity.ui.CitiesListActivity;
import com.example.cloniamix.weatherapp.mvp.screens.screen_details.ui.DetailsActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailsPresenter extends BasePresenter<DetailsActivity> {

    private Model mModel;
    private CompositeDisposable mCompositeDisposable;

    public DetailsPresenter() {
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

    public void loadData(String cityName){
        getView().showProgress(true);

        mCompositeDisposable.add(mModel.getCity(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(city -> getView().updateView(city)
                        ,throwable -> getView().showToast("Ошибка БД")
                )
        );
        updateData(cityName);
    }

    public void updateData(String cityName){

        getView().showProgress(true);

        mCompositeDisposable.add(mModel.getWeatherWithCityName(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCityInDB
                ,throwable -> {
                            Log.d(Utils.APP_TAG, "getDataFromApi: " + throwable);
                            getView().showToast("Ошибка связи");})
        );
    }

    private void updateCityInDB(CityWeather cityWeather) {
        getView().showProgress(true);

        City city = new City();
        city.setCityName(cityWeather.getName());
        city.setTempNow(cityWeather.getMain().getTemp());
        city.setConditions(cityWeather.getWeather().get(0).getDescription());
        city.setIcon(cityWeather.getWeather().get(0).getIcon());
        city.setWindSpeed(cityWeather.getWind().getSpeed());
        city.setPressure(cityWeather.getMain().getPressure());
        city.setHumidity(cityWeather.getMain().getHumidity());
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

    public void chooseAnotherCity(){
        Intent intent = new Intent(getView(), CitiesListActivity.class);
        getView().goToActivity(intent);
    }
}
