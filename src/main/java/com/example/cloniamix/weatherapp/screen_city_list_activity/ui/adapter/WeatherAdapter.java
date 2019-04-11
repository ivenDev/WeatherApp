package com.example.cloniamix.weatherapp.screen_city_list_activity.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloniamix.weatherapp.R;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.Utils;
import com.example.cloniamix.weatherapp.screen_city_list_activity.presenter.CitiesListPresenter;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<City> mCities;

    public WeatherAdapter(/*List<City> cities*/) {
        /*mCities = cities;*/
    }

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

    public void setCities(List<City> cities){
        mCities = cities;
        notifyDataSetChanged();
    }

    static  class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        private City mCity;
        private TextView mCityName;
        private TextView mConditions;
        private TextView mTempNow;

        private WeatherViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            mCityName = itemView.findViewById(R.id.city_name_tv);
            mConditions = itemView.findViewById(R.id.conditions_tv);
            mTempNow = itemView.findViewById(R.id.temp_now_tv);
        }

        private void bindCity(City city){
            mCity = city;
            String tempNow = Double.toString(mCity.getTempNow());

            mCityName.setText(mCity.getCityName());
            mConditions.setText(mCity.getConditions());
            mTempNow.setText(tempNow);
        }

        @Override
        public void onClick(View v) {
            Utils.log("Короткое нажатие для: " + mCity.getCityName());
            Toast.makeText(itemView.getContext(),"переход на детализацию города " + mCity.getCityName(),Toast.LENGTH_SHORT).show();
        }

        // TODO: 11.04.2019 реализовать удаление через диалог подтверждения (в идеале еще получить доступ к методам активити)
        @Override
        public boolean onLongClick(View v) {
            Utils.log("Долгое нажатие сработало для: " + mCity.getCityName());
            Toast.makeText(itemView.getContext(),"Сейчас должен удалиться город " + mCity.getCityName(),Toast.LENGTH_SHORT).show();
            CitiesListPresenter presenter = new CitiesListPresenter();
            presenter.deleteCity(mCity);
            return true;
        }
    }
}
