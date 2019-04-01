package com.example.cloniamix.weatherapp.city_list_activity_screen.presenter;

import android.util.Log;

import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.Utils;
import com.example.cloniamix.weatherapp.mvp.contract.base_presenter.BasePresenter;
import com.example.cloniamix.weatherapp.mvp.contract.base_view.ICitiesListView;
import com.example.cloniamix.weatherapp.city_list_activity_screen.model.Model;
import com.example.cloniamix.weatherapp.mvp.contract.base_model.POJO.CityWeather;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CitiesListPresenter extends BasePresenter<ICitiesListView> {

    private Model mModel;
    private List<City> mCities;
    private CompositeDisposable mCompositeDisposable;

    public CitiesListPresenter() {
        mModel = new Model();
        mCompositeDisposable = new CompositeDisposable();
        mCities = new ArrayList<>();

    }

    public void loadDBData(){
        getDataFromDB();
    }

    public void loadNetData(){
        getDataFromApi();
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
        getView().showProgress();
        Disposable disposable = mModel.getCities()
                .subscribeOn(Schedulers.io())
                /*.delay(2, TimeUnit.SECONDS)*/
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateUI
                        ,this::handleError);

        mCompositeDisposable.add(disposable);
    }

    private void getDataFromApi(){
        getView().showProgress();
        Disposable disposable = Flowable.fromArray(getCitiesName(mCities))
                .subscribeOn(Schedulers.io())
                /*.map(this::getCitiesName)*/
                .flatMap(Flowable::fromArray)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::queryAPI);

                mCompositeDisposable.add(disposable);
    }

    private void queryAPI(String s) {

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
        Utils.log("update UI");
        getView().updateView(cities);
    }

    private void handleError(Throwable throwable){
        Utils.log(throwable.toString());
        getView().showToast("DB error");
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

}
