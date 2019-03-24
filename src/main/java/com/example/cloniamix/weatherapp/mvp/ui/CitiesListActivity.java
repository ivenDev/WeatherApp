package com.example.cloniamix.weatherapp.mvp.ui;


import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

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
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCitiesListPresenter = new CitiesListPresenter();
        mRecyclerView = findViewById(R.id.cities_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressBar = findViewById(R.id.progressBar);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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
    public void setProgress(boolean showProgress) {
        if (showProgress){
            mRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }else {
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }


    public void showToast(String massage){
        Toast.makeText(this,massage,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void updateView(List<City> cities) {
       if (mWeatherAdapter == null){
           mWeatherAdapter = new WeatherAdapter(cities);
           mRecyclerView.setAdapter(mWeatherAdapter);
       }else {
           mWeatherAdapter.setCities(cities);
           mWeatherAdapter.notifyDataSetChanged();
       }
    }
}


