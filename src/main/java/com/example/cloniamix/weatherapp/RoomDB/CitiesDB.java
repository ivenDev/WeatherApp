package com.example.cloniamix.weatherapp.RoomDB;

import com.example.cloniamix.weatherapp.RoomDB.Dao.CitiesDao;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;
import com.example.cloniamix.weatherapp.app.MyApp;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


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
                            CitiesDB.class, "Sample.db")
                            //.allowMainThreadQueries() //исключает ошибку, если работать с БД в основном потоке
                            .build();
            }
        }
    }
        return INSTANCE;
}
}
