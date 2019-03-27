package com.example.cloniamix.weatherapp.weatherApi;

import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.weatherApi.POJO.CityWeather;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IWeatherMapService {

    @GET("weather?&APPID=f38474a55533419d791782971413a0ca&units=metric&lang=ru")
    Single<CityWeather> getWeatherWithCityName(@Query("q") String cityName);
    //(@Query("q") String cityName), а q={cityName} убрать
}
