package com.example.cloniamix.weatherapp.city_list_activity_screen.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cloniamix.weatherapp.R;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.Utils;
import com.example.cloniamix.weatherapp.mvp.contract.base_view.ICitiesListView;
import com.example.cloniamix.weatherapp.city_list_activity_screen.presenter.CitiesListPresenter;
import com.example.cloniamix.weatherapp.city_list_activity_screen.ui.adapter.WeatherAdapter;

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
        setContentView(R.layout.activity_main);

        init();
    }

    //здесь подписываем презентер на view
    @Override
    protected void onStart() {
        super.onStart();
        mCitiesListPresenter.subscribe(this);
        mCitiesListPresenter.loadDBData();
        mCitiesListPresenter.loadNetData();
    }

    //В этом методе освобождаем память
    @Override
    protected void onStop() {
        super.onStop();
        mCitiesListPresenter.unsubscribe();
        mCitiesListPresenter = null;
        mRecyclerView = null;
        mWeatherAdapter = null;
    }
    //endregion

    @Override
    public void showProgress() {
        Utils.setVisible(mProgressBar,true);
        Utils.setVisible(mRecyclerView,false);
    }


    @Override
    public void showToast(String massage){
        Toast.makeText(this,massage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView(List<City> cities) {
        if (mWeatherAdapter != null){
            mWeatherAdapter.setCities(cities);
        }
        Utils.setVisible(mRecyclerView,true);
        Utils.setVisible(mProgressBar, false);
    }

    public void updateDataFromNet(MenuItem view){
        mCitiesListPresenter.loadNetData();
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


