package com.example.cloniamix.weatherapp.mvp.presenter;

import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.mvp.model.Model;
import com.example.cloniamix.weatherapp.mvp.ui.CitiesListActivity;

import java.util.List;

public class CitiesListPresenter {

    private Model mModel;
    private List<City> mCities;
    private CitiesListActivity mView;

    public CitiesListPresenter() {
        mModel = new Model();
        mCities = mModel.getCities();
    }

    /**вызывать в onStart() activity*/
    public void attach(CitiesListActivity view){
        mView = view;
        mView.updateView(mCities);
    }

    /** вызывать в onStop() activity*/
    public void detach(){
        mView = null;
    }
}
