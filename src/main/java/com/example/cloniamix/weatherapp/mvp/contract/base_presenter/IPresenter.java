package com.example.cloniamix.weatherapp.mvp.contract.base_presenter;

import com.example.cloniamix.weatherapp.mvp.contract.base_view.ICitiesListView;

public interface IPresenter<V extends ICitiesListView> {

    void subscribe(V view);

    void unsubscribe();
}
