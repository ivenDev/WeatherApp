package com.example.cloniamix.weatherapp.RoomDB.Dao;

import com.example.cloniamix.weatherapp.RoomDB.Entity.City;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface CitiesDao {

    @Query("SELECT * FROM cities")
    Flowable<List<City>> getAllCities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCity(City city);

    @Update
    Completable updateCity(City city);

    @Delete
    Completable deleteCity(City city);
}
