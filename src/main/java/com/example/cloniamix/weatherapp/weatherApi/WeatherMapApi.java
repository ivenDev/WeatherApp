package com.example.cloniamix.weatherapp.weatherApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherMapApi {

    /*public final static String API_KAY = "f38474a55533419d791782971413a0ca";*/
    private final static String BASE_URL = "https://api.openweathermap.org/data/2.5/"; /*https://api.openweathermap.org/data/2.5/weather?q=Саранск&APPID=f38474a55533419d791782971413a0ca&units=metric&lang=ru*/
    private static WeatherMapApi sWeatherMapApi;
    private static IWeatherMapService sWeatherMapService;
    private Retrofit mRetrofit;
    private WeatherMapApi(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        /*sWeatherMapService = mRetrofit.create(IWeatherMapService.class);*/
    }

    public static WeatherMapApi getInstance(){
        if (sWeatherMapApi == null){
            sWeatherMapApi = new WeatherMapApi();
        }
        return sWeatherMapApi;
    }

    public IWeatherMapService getApi() {
        return mRetrofit.create(IWeatherMapService.class);
    }
}
