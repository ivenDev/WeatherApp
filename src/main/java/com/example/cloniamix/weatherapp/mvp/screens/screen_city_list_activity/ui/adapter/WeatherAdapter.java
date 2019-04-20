package com.example.cloniamix.weatherapp.mvp.screens.screen_city_list_activity.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloniamix.weatherapp.R;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.Utils;
import com.example.cloniamix.weatherapp.mvp.screens.screen_city_list_activity.presenter.CitiesListPresenter;
import com.example.cloniamix.weatherapp.mvp.screens.screen_city_list_activity.ui.CitiesListActivity;
import com.example.cloniamix.weatherapp.mvp.screens.screen_details.ui.DetailsActivity;

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

            Context context = itemView.getContext();
            Utils.log("Короткое нажатие для: " + mCity.getCityName());
            /*Toast.makeText(itemView.getContext(),"переход на детализацию города " + mCity.getCityName(),Toast.LENGTH_SHORT).show();*/

            SharedPreferences settings = context.getSharedPreferences(Utils.APP_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(Utils.APP_PREFERENCES_CITY_NAME, mCity.getCityName()).apply();

            Intent intent = new Intent(context, DetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            /*intent.putExtra("cityName",mCity.getCityName());*/
            context.startActivity(intent);

        }

        // TODO: 14.04.2019 настроить AlertDialog. рассмотреть вариант с DialogFragment (в идеале еще получить доступ к методам активити)
        @Override
        public boolean onLongClick(View v) {

            Context context =(CitiesListActivity) (itemView.getContext());

            Utils.log("Долгое нажатие сработало для: " + mCity.getCityName());
            Toast.makeText(context,"Сейчас должен удалиться город " + mCity.getCityName(),Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Удаление города")
                    .setMessage("Вы уверенны?")
                    .setCancelable(true)
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (((CitiesListActivity) (context)).getCitiesListPresenter() != null) {
                                ((CitiesListActivity) itemView.getContext()).getCitiesListPresenter().deleteCity(mCity);
                            /*CitiesListPresenter presenter = new CitiesListPresenter();
                            presenter.deleteCity(mCity);*/
                            }
                        }
                    })
                    .setOnCancelListener(DialogInterface::cancel);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        }
    }
}
