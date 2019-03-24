package com.example.cloniamix.weatherapp.weatherApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherMapApi {

    /*public final static String API_KAY = "f38474a55533419d791782971413a0ca";*/
    private final static String BASE_URL = "api.openweathermap.org/data/2.5/";
    private static WeatherMapApi sWeatherMapApi;
    private static IWeatherMapService sWeatherMapService;

    private WeatherMapApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sWeatherMapService = retrofit.create(IWeatherMapService.class);
    }

    public static WeatherMapApi getInstance(){
        if (sWeatherMapApi == null){
            sWeatherMapApi = new WeatherMapApi();
        }
        return sWeatherMapApi;
    }

    public static IWeatherMapService getWeatherMapService() {
        return sWeatherMapService;
    }
}
