package com.example.cloniamix.weatherapp.mvp.screens.screen_details.presenter;

import com.example.cloniamix.weatherapp.mvp.contract.base_presenter.BasePresenter;
import com.example.cloniamix.weatherapp.mvp.model.Model;
import com.example.cloniamix.weatherapp.mvp.screens.screen_details.ui.DetailsActivity;

import io.reactivex.Scheduler;
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
        mCompositeDisposable.add(mModel.getCity(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(city -> getView().updateView(city)
                        ,throwable -> getView().showToast("Ошибка БД")
                )
        );
    }
}
