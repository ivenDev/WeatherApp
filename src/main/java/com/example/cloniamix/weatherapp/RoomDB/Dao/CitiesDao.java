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
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface CitiesDao {

    @Query("SELECT * FROM cities")
    Observable<List<City>> getAllCities();

    @Query("SELECT * FROM cities")
    Single<List<City>> getAllCitiesSingle();

    @Query("SELECT * FROM cities WHERE name = :cityName ")
    Observable<City> getCity(String cityName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCity(City city);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertCities(List<City> cities);

    @Update
    Completable updateCity(City city);

    @Delete
    Completable deleteCity(City city);
}
