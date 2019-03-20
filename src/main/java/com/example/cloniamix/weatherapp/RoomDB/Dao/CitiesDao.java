package com.example.cloniamix.weatherapp.RoomDB.Dao;

import com.example.cloniamix.weatherapp.RoomDB.Entity.City;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CitiesDao {

    @Query("SELECT * FROM cities")
    List<City> getAllCities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCities(List<City> cities);
}
