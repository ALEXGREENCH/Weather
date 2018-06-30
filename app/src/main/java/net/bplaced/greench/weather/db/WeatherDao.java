package net.bplaced.greench.weather.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface WeatherDao {

    @Insert
    void insert(Weather weather);

    //@Insert
    //void insert(WeatherList<WeatherDay> pets);


    @Query("DELETE FROM weather_table")
    void deleteAll();

    @Query("SELECT * from weather_table ORDER BY date ASC")
    LiveData<List<Weather>> getAllCities();
}
