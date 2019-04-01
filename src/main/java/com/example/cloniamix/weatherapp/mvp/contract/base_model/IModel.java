package com.example.cloniamix.weatherapp.mvp.contract.base_model;

import io.reactivex.Single;

interface IModel<D> {
    Single<D> getWeatherWithCityName(String cityName);
}
