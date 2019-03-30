package com.example.cloniamix.weatherapp.mvp.ui;


import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cloniamix.weatherapp.R;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.Utils;
import com.example.cloniamix.weatherapp.mvp.contract.ICitiesListView;
import com.example.cloniamix.weatherapp.mvp.presenter.CitiesListPresenter;
import com.example.cloniamix.weatherapp.mvp.ui.adapter.WeatherAdapter;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CitiesListActivity extends AppCompatActivity implements ICitiesListView {

    private CitiesListPresenter mCitiesListPresenter;
    private RecyclerView mRecyclerView;
    private WeatherAdapter mWeatherAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCitiesListPresenter.attach(this);
        mCitiesListPresenter.loadDBData();
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
    public void showProgress() {
        Utils.setVisible(mProgressBar,true);
        Utils.setVisible(mRecyclerView,false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }




    public void showToast(String massage){
        Toast.makeText(this,massage,Toast.LENGTH_SHORT).show();
    }


    private void init() {
        mCitiesListPresenter = new CitiesListPresenter();
        mProgressBar = findViewById(R.id.progressBar);

        mRecyclerView = findViewById(R.id.cities_list_rv);
        mWeatherAdapter = new WeatherAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mWeatherAdapter);

    }

    public void updateView(List<City> cities) {
       if (mWeatherAdapter != null){
           mWeatherAdapter.setCities(cities);
       }
       Utils.setVisible(mRecyclerView,true);
       Utils.setVisible(mProgressBar, false);
    }
}


