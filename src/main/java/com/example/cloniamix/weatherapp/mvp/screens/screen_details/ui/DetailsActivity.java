package com.example.cloniamix.weatherapp.mvp.screens.screen_details.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cloniamix.weatherapp.R;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.Utils;
import com.example.cloniamix.weatherapp.mvp.contract.base_view.IBaseView;
import com.example.cloniamix.weatherapp.mvp.screens.screen_details.presenter.DetailsPresenter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity implements IBaseView {

    private SharedPreferences mSettings;
    private  String mName;

    private DetailsPresenter mDetailsPresenter;
    private TextView mCityName;
    private TextView mTempNow;
    private TextView mConditionsNow;
    private TextView mWind;
    private TextView mPressure;
    private TextView mHumidity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDetailsPresenter.subscribe(this);
        /*String cityName = getIntent().getStringExtra("cityName");*/

        mName = mSettings.getString(Utils.APP_PREFERENCES_CITY_NAME, "Saransk");
        mDetailsPresenter.loadData(mName);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDetailsPresenter.unsubscribe();
        mDetailsPresenter = null;
        mCityName = null;
        mTempNow = null;
        mConditionsNow = null;
        mWind = null;
        mPressure = null;
        mHumidity = null;
    }

    public void updateView(City city){
        mCityName.setText(city.getCityName());
        String tempNow = Double.toString(city.getTempNow());
        mTempNow.setText(tempNow);
        mConditionsNow.setText(city.getConditions());
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showToast(String message) {

    }

    public void goToActivity(Intent intent){
        startActivity(intent);
    }

    private void init(){

        mSettings = getSharedPreferences(Utils.APP_PREFERENCES, Context.MODE_PRIVATE);

        mDetailsPresenter = new DetailsPresenter();
        mCityName = findViewById(R.id.city_name_a_details);
        mTempNow = findViewById(R.id.temp_now_a_details);
        mConditionsNow = findViewById(R.id.conditions_now_a_details);
        mWind = findViewById(R.id.wind_a_details);
        mPressure = findViewById(R.id.pressure_a_details);
        mHumidity = findViewById(R.id.humidity_a_details);

        ImageView addCity = findViewById(R.id.add_new_city_image);
        addCity.setOnClickListener(v -> mDetailsPresenter.chooseAnotherCity());

        ImageView renewData = findViewById(R.id.renew_weather_image_view);
        renewData.setOnClickListener(v -> mDetailsPresenter.updateData(mName));
    }
}
