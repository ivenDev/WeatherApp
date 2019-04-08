package com.example.cloniamix.weatherapp.screen_add_new_city_activity.ui;

import android.os.Bundle;

import com.example.cloniamix.weatherapp.R;
import com.example.cloniamix.weatherapp.mvp.contract.base_view.IAddnewCityView;
import com.example.cloniamix.weatherapp.screen_add_new_city_activity.presenter.AddNewCityPresenter;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewCityActivity extends AppCompatActivity implements IAddnewCityView {

    private AddNewCityPresenter mPresenter;


    //region lifeCycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_city);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.subscribe(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unsubscribe();
        mPresenter = null;
    }
    //endregion

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showToast(String message) {

    }

    private void init(){
        mPresenter = new AddNewCityPresenter();
    }
}
