package com.example.cloniamix.weatherapp.mvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cloniamix.weatherapp.R;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<City> mCities;

    public WeatherAdapter(List<City> cities) {
        mCities = cities;
    }

    // TODO: 19.03.2019 В item_city_weather применить единый стиль и посмотреть какие значения рекомендует material design
    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city_weather,parent,false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        City city = mCities.get(position);
        holder.bindCity(city);
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    static  class WeatherViewHolder extends RecyclerView.ViewHolder{

        private City mCity;
        private TextView mCityName;
        private TextView mConditions;
        private TextView mTempNow;

        private WeatherViewHolder(View itemView) {
            super(itemView);
            mCityName = itemView.findViewById(R.id.city_name_tv);
            mConditions = itemView.findViewById(R.id.conditions_tv);
            mTempNow = itemView.findViewById(R.id.temp_now_tv);
        }

        private void bindCity(City city){
            mCity = city;
            String tempNow = Integer.toString(mCity.getTempNow());

            mCityName.setText(mCity.getCityName());
            mConditions.setText(mCity.getConditions());
            mTempNow.setText(tempNow);
        }
    }
}
