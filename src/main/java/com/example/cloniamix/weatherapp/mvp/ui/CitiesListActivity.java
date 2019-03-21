package com.example.cloniamix.weatherapp.mvp.ui;


import android.os.Bundle;

import com.example.cloniamix.weatherapp.R;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.mvp.contract.ICitiesListView;
import com.example.cloniamix.weatherapp.mvp.presenter.CitiesListPresenter;
import com.example.cloniamix.weatherapp.mvp.ui.adapter.WeatherAdapter;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CitiesListActivity extends AppCompatActivity implements ICitiesListView {

    CitiesListPresenter mCitiesListPresenter;
    private RecyclerView mRecyclerView;
    private WeatherAdapter mWeatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCitiesListPresenter = new CitiesListPresenter();
        mRecyclerView = findViewById(R.id.cities_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    @Override
    protected void onStart() {
        super.onStart();
        mCitiesListPresenter.attach(this);
    }

    /**в этом методе освобождаем память */
    @Override
    protected void onStop() {
        super.onStop();
        mCitiesListPresenter.detach();
        mCitiesListPresenter = null;
        mRecyclerView = null;
        mWeatherAdapter = null;
    }

    @Override
    public void updateView(List<City> cities) {
        mWeatherAdapter = new WeatherAdapter(cities);
        mRecyclerView.setAdapter(mWeatherAdapter);
    }


}


