package com.example.cloniamix.weatherapp.mvp.presenter;

import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.mvp.model.Model;
import com.example.cloniamix.weatherapp.mvp.ui.CitiesListActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class CitiesListPresenter {

    private Model mModel;
    private List<City> mCities;
    private CitiesListActivity mView;
    private CompositeDisposable mCompositeDisposable;

    public CitiesListPresenter() {
        mModel = new Model();
        mCompositeDisposable = new CompositeDisposable();

    }

    /**вызывать в onStart() activity*/
    public void attach(CitiesListActivity view){
        mView = view;

        mView.setProgress(true);
        mCompositeDisposable.add(mModel.getCities()
                .delay(1,TimeUnit.SECONDS)// FIXME: 21.03.2019 ВРЕМЕННО!!! убрать после реализации запроса в сеть
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                    mCities = cities;
                    mView.setProgress(false);
                    mView.updateView(mCities);})
        );

    }

    private void getDataFromDB(){

    }

    /** вызывать в onStop() activity*/
    public void detach(){
        mCompositeDisposable.dispose();
        mView = null;
    }
}
