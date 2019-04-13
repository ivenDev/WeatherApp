package com.example.cloniamix.weatherapp.mvp.screens.screen_city_list_activity.presenter;

import android.util.Log;

import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.Utils;
import com.example.cloniamix.weatherapp.mvp.contract.base_presenter.BasePresenter;
import com.example.cloniamix.weatherapp.mvp.screens.screen_city_list_activity.model.Model;
import com.example.cloniamix.weatherapp.mvp.contract.base_model.POJO.CityWeather;
import com.example.cloniamix.weatherapp.mvp.screens.screen_city_list_activity.ui.CitiesListActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/** Слой Презентер при реализации архитектуры MVP. Является презентером для CityListActivity. */

public class CitiesListPresenter extends BasePresenter<CitiesListActivity> {

    private Model mModel;
    private List<City> mCities;
    private CompositeDisposable mCompositeDisposable;//убрать в родителя

    public CitiesListPresenter() {
        mModel = new Model();
        mCompositeDisposable = new CompositeDisposable();
        mCities = new ArrayList<>();

    }

    public void loadData(){
        getDataFromDB();
        getDataFromApi();
    }

    public void loadNetData(){
        getDataFromApi();
    }

    //перейти на новую активити или отобразить диалог
    public void addNewCity(){
        getView().goToActivity();
    }

    //region methods with view lifecycle
    @Override
    public void unsubscribe() {
        super.unsubscribe();

        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        mCities = null;
    }
    //endregion


    //region for work with model
    private void getDataFromDB(){
        getView().showProgress(true);
        Disposable disposable = mModel.getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateUI
                        ,this::handleError);

        mCompositeDisposable.add(disposable);
    }

    private void getDataFromApi(){
        getView().showProgress(true);

        Disposable disposable = mModel.getCitiesSingle()
                .subscribeOn(Schedulers.io())
                .map(this::getStringCitiesName)
                .flatMapObservable(Observable::fromArray)
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(this::queryAPI
                        ,throwable -> getView().showToast("нет данных")
                )

                ;

        mCompositeDisposable.add(disposable);
    }

    private void queryAPI(String s) {
        getView().showProgress(true);

        Disposable disposable = mModel.getWeatherWithCityName(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCityInDB
                        ,throwable -> {
                            Log.d(Utils.APP_TAG, "getDataFromApi: " + throwable);
                            getView().showToast("Ошибка связи");});

        mCompositeDisposable.add(disposable);
    }


    private void updateCityInDB(CityWeather cityWeather) {
        getView().showProgress(true);

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

    public void deleteCity(City city){
        mCompositeDisposable.add(mModel.deleteCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()-> /*getView().showToast("Удалено")*/ Utils.log("City deleted")
                        ,throwable -> /*getView().showToast("Ошибка удаления")*/Utils.log("Ошибка удаления")
                ));
    }
    //endregion

    private void updateUI(@NonNull List<City> cities){
        mCities = cities;
        Utils.log("update UI");
        getView().updateView(cities);
    }

    private void handleError(Throwable throwable){
        Utils.log(throwable.toString());
        getView().showToast("DB error");
    }


    // FIXME: 02.04.2019 может перенести в модель?
    private String[] getStringCitiesName(List<City> cities) {
        ArrayList<String> citiesName = new ArrayList<>();
        for (City city : cities) {
            citiesName.add(city.getCityName());
        }
        String[] citiesNameString = new String[citiesName.size()];

        for (int i = 0; i < citiesName.size(); i++) {
            citiesNameString[i] = citiesName.get(i);
        }

        return citiesNameString;
    }

}
