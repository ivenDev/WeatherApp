package com.example.cloniamix.weatherapp.mvp.contract.base_view;

import android.os.Bundle;

import com.example.cloniamix.weatherapp.mvp.contract.base_presenter.BasePresenter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity<V extends IBaseView,P extends BasePresenter<V>> extends AppCompatActivity/* implements IBaseView*/  {

    public P mPresenter;
    V view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.subscribe(view);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unsubscribe();
        mPresenter = null;
    }

    public abstract void init();
}
