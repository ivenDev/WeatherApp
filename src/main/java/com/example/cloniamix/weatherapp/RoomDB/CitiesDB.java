package com.example.cloniamix.weatherapp.RoomDB;

import android.util.Log;

import com.example.cloniamix.weatherapp.RoomDB.Dao.CitiesDao;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.Const;
import com.example.cloniamix.weatherapp.app.MyApp;

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
                    CompositeDisposable disposable = new CompositeDisposable();
                    disposable.add(INSTANCE.citiesDao().insertCity(new City("Saransk"))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(()-> Log.d(Const.APP_TAG, "initDB: Город: Saransk добавлен")
                                    ,throwable -> Log.d(Const.APP_TAG, "initDB: Ошибка добавления"))
                    );
                    disposable.add(INSTANCE.citiesDao().insertCity(new City("Moscow"))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(()-> Log.d(Const.APP_TAG, "initDB: Город: Moscow добавлен")
                                    ,throwable -> Log.d(Const.APP_TAG, "initDB: Ошибка добавления"))
                    );

                    disposable.dispose();

            }
        }
    }
        return INSTANCE;
}
}
