package com.example.cloniamix.weatherapp.weatherApi;

import com.example.cloniamix.weatherapp.RoomDB.Entity.City;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IWeatherMapService {

    @GET("weather?q={cityName}&APPID=f38474a55533419d791782971413a0ca&units=metric&lang=ru")
    Call<City> getWeatherWithCityName(@Path("cityName")String cityName);

}
