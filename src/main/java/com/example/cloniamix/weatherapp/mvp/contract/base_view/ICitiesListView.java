package com.example.cloniamix.weatherapp.mvp.contract.base_view;

import com.example.cloniamix.weatherapp.RoomDB.Entity.City;

import java.util.List;

public interface ICitiesListView {

    void showProgress();
    void showToast(String message);
    void updateView(List<City> cities);
}
