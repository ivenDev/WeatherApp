package com.example.cloniamix.weatherapp.RoomDB;

import android.content.Context;

import com.example.cloniamix.weatherapp.RoomDB.Dao.CitiesDao;
import com.example.cloniamix.weatherapp.RoomDB.Entity.City;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {City.class},version = 1,exportSchema = false)
public abstract class CitiesDB extends RoomDatabase {

    private static volatile CitiesDB INSTANCE;

    public abstract CitiesDao citiesDao();

    public static CitiesDB getINSTANCE(Context context){

        if (INSTANCE == null) {
            synchronized (CitiesDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CitiesDB.class, "Sample.db")
                            .allowMainThreadQueries() //исключает ошибку, если работать с БД в основном потоке
                            .build();
            }
        }
    }
        return INSTANCE;
}
}
