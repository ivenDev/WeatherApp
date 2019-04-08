package com.example.cloniamix.weatherapp.mvp.contract.base_presenter;

import com.example.cloniamix.weatherapp.mvp.contract.base_view.IBaseView;

public interface IPresenter<V extends IBaseView> {

    void subscribe(V view);

    void unsubscribe();
}
