package com.example.cloniamix.weatherapp.mvp.screens.screen_city_list_activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cloniamix.weatherapp.R;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.Utils;
import com.example.cloniamix.weatherapp.mvp.contract.base_view.ICitiesListView;
import com.example.cloniamix.weatherapp.mvp.screens.screen_add_new_city_activity.ui.AddNewCityActivity;
import com.example.cloniamix.weatherapp.mvp.screens.screen_city_list_activity.presenter.CitiesListPresenter;
import com.example.cloniamix.weatherapp.mvp.screens.screen_city_list_activity.ui.adapter.WeatherAdapter;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/** Активити экрана списка городов с данными погоды(название города, температура на данный момент)*/

public class CitiesListActivity extends AppCompatActivity implements ICitiesListView {

    private CitiesListPresenter mCitiesListPresenter;
    private RecyclerView mRecyclerView;
    private WeatherAdapter mWeatherAdapter;
    private ProgressBar mProgressBar;

    //region lifeCycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_list);

        init();
    }

    //здесь подписываем презентер на view
    @Override
    protected void onStart() {
        super.onStart();
        mCitiesListPresenter.subscribe(this);
        mCitiesListPresenter.loadData();
    }


    //В этом методе освобождаем память
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCitiesListPresenter.unsubscribe();
        mCitiesListPresenter = null;
        mRecyclerView = null;
        mWeatherAdapter = null;
    }
    //endregion

    @Override
    public void showProgress(boolean show) {
        if (show){
            Utils.setVisible(mProgressBar,true);
            Utils.setVisible(mRecyclerView,false);
        }else {
            Utils.setVisible(mProgressBar,false);
            Utils.setVisible(mRecyclerView,true);
        }

    }


    @Override
    public void showToast(String massage){
        showProgress(true);
        Toast.makeText(this,massage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView(List<City> cities) {
        if (mWeatherAdapter != null){
            mWeatherAdapter.setCities(cities);
        }
        showProgress(false);
    }

    public void goToActivity(){
        startActivity(new Intent(this, AddNewCityActivity.class));
    }
    public void goToActivity(Intent intent){
        startActivity(intent);
    }

    public CitiesListPresenter getCitiesListPresenter() {
        if (mCitiesListPresenter != null){
            return mCitiesListPresenter;
        }
        return null;
    }

    private void init() {
        mCitiesListPresenter = new CitiesListPresenter();
        mProgressBar = findViewById(R.id.progressBar);

        mRecyclerView = findViewById(R.id.cities_list_rv);
        mWeatherAdapter = new WeatherAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mWeatherAdapter);

        ImageView addCity = findViewById(R.id.add_new_city_image);
        addCity.setOnClickListener(v -> mCitiesListPresenter.addNewCity());

        ImageView renewData = findViewById(R.id.renew_weather_image_view);
        renewData.setOnClickListener(v -> mCitiesListPresenter.loadNetData());

    }
}


