package com.example.cloniamix.weatherapp.RoomDB;

import android.util.Log;

import com.example.cloniamix.weatherapp.RoomDB.Dao.CitiesDao;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.Utils;
import com.example.cloniamix.weatherapp.app.MyApp;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


// TODO: 20.03.2019 Реализовать миграцию версий БД
@Database(entities = {City.class},version = 1,exportSchema = false)
public abstract class CitiesDB extends RoomDatabase {

    private static volatile CitiesDB INSTANCE;

    public abstract CitiesDao citiesDao();

    public static CitiesDB getINSTANCE(){

        if (INSTANCE == null) {
            synchronized (CitiesDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(MyApp.getInstance(),
                            CitiesDB.class, "CityWeather.db")
                            //.allowMainThreadQueries() //исключает ошибку, если работать с БД в основном потоке
                            .build();

                    // FIXME: 29.03.2019 убрать предустановку, и добавить проверку БД. Если она пуста, то отобразить активность добавления своего города
                    CompositeDisposable disposable = new CompositeDisposable();

                    List<City> cities = new ArrayList<>();
                    cities.add(new City("Saransk"));
                    cities.add(new City("Moscow"));

                    disposable.add(INSTANCE.citiesDao().insertCities(cities)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(()-> Log.d(Utils.APP_TAG, "CitiesDB.initDB: Города добавлены")
                                    ,throwable -> Log.d(Utils.APP_TAG, "CitiesDB.initDB: Ошибка добавления"))
                    );

                    /*disposable.dispose();*/

            }
        }
    }
        return INSTANCE;
}
}
