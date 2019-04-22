package com.example.cloniamix.weatherapp.mvp.screens.screen_details.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloniamix.weatherapp.R;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.Utils;
import com.example.cloniamix.weatherapp.mvp.contract.base_view.IBaseView;
import com.example.cloniamix.weatherapp.mvp.screens.screen_details.presenter.DetailsPresenter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity implements IBaseView {

    private SharedPreferences mSettings;
    private String mName;

    private DetailsPresenter mDetailsPresenter;

    private TextView mCityName;
    private TextView mTempNow;
    private TextView mConditionsNow;
    private ImageView mConditionsImage;
    private TextView mWind;
    private TextView mPressure;
    private TextView mHumidity;

    private ProgressBar mProgress;
    private LinearLayout mContainer;


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
        mName = mSettings.getString(Utils.APP_PREFERENCES_CITY_NAME, "Saransk");
        mDetailsPresenter.loadData(mName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSettings = null;
        mName = null;
        mDetailsPresenter.unsubscribe();
        mDetailsPresenter = null;
        mCityName = null;
        mTempNow = null;
        mConditionsNow = null;
        mConditionsImage = null;
        mWind = null;
        mPressure = null;
        mHumidity = null;

        mProgress = null;
        mContainer = null;
    }

    public void updateView(City city) {

        showProgress(false);

        String tempNow = Double.toString(city.getTempNow());
        String icon = "i" + city.getIcon();
        int id = getResources().getIdentifier(icon, "drawable", getPackageName());
        String windSpeed = "ветер: \n" +  city.getWindSpeed();
        String pressure ="давление: \n" +  city.getPressure();
        String humidity = "влажность: \n" + city.getHumidity();

        mCityName.setText(city.getCityName());
        mTempNow.setText(tempNow);
        mConditionsNow.setText(city.getConditions());
        mConditionsImage.setImageResource(id);
        mWind.setText(windSpeed);
        mPressure.setText(pressure);
        mHumidity.setText(humidity);
    }

    @Override
    public void showProgress(boolean show) {
        if (show){
            Utils.setVisible(mProgress,true);
            Utils.setVisible(mContainer,false);
        }else {
            Utils.setVisible(mProgress,false);
            Utils.setVisible(mContainer,true);
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void goToActivity(Intent intent) {
        startActivity(intent);
    }

    private void init() {

        mSettings = getSharedPreferences(Utils.APP_PREFERENCES, Context.MODE_PRIVATE);
        mDetailsPresenter = new DetailsPresenter();

        mCityName = findViewById(R.id.city_name_a_details);
        mTempNow = findViewById(R.id.temp_now_a_details);
        mConditionsNow = findViewById(R.id.conditions_now_a_details);
        mConditionsImage = findViewById(R.id.imageView_details);
        mWind = findViewById(R.id.wind_a_details);
        mPressure = findViewById(R.id.pressure_a_details);
        mHumidity = findViewById(R.id.humidity_a_details);
        ImageView addCity = findViewById(R.id.add_new_city_image);
        addCity.setOnClickListener(v -> mDetailsPresenter.chooseAnotherCity());
        ImageView renewData = findViewById(R.id.renew_weather_image_view);
        renewData.setOnClickListener(v -> mDetailsPresenter.updateData(mName));
        mProgress = findViewById(R.id.detail_progress_bar);
        mContainer = findViewById(R.id.detail_container);
    }
}
