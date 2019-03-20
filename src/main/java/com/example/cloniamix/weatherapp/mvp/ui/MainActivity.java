package com.example.cloniamix.weatherapp.mvp.ui;


import android.os.Bundle;

import com.example.cloniamix.weatherapp.R;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.mvp.ui.adapter.WeatherAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private WeatherAdapter mWeatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.cities_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // FIXME: 20.03.2019 ВРЕМЕННО!!! Убрать этот метод после реализации БД
        initData();
    }

    private void initData() {
        City city = new City();
        city.setCityName("Саранск");
        city.setConditions("Солнце");
        city.setTempNow(24);

        City city2 = new City();
        city2.setCityName("Москва");
        city2.setConditions("Облачно");
        city2.setTempNow(15);

        ArrayList<City> cities = new ArrayList<>();
        cities.add(city);
        cities.add(city2);
        cities.add(city);
        cities.add(city2);
        cities.add(city);
        cities.add(city2);
        cities.add(city);
        cities.add(city2);
        cities.add(city);
        cities.add(city);
        cities.add(city2);
        cities.add(city);
        cities.add(city2);
        cities.add(city);
        cities.add(city2);

        mWeatherAdapter = new WeatherAdapter(cities);
        mRecyclerView.setAdapter(mWeatherAdapter);
    }
}

// TODO: 20.03.2019 Реализовать MVP(Moxy)

