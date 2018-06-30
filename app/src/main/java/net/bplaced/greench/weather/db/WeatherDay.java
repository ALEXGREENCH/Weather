package net.bplaced.greench.weather.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_day_table")
public class WeatherDay {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;

    @NonNull
    private String city, dt, t_min, t_max, t_morn,
            t_day, t_eve, t_night, w_speed;

    public WeatherDay(Integer id, String city, String dt, String t_min, String t_max, String t_morn, String t_day, String t_eve, String t_night, String w_speed) {
        this.id = id;
        this.city = city;
        this.dt = dt;
        this.t_min = t_min;
        this.t_max = t_max;
        this.t_morn = t_morn;
        this.t_day = t_day;
        this.t_eve = t_eve;
        this.t_night = t_night;
        this.w_speed = w_speed;
    }

    public Integer getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getDt() {
        return dt;
    }

    public String getT_min() {
        return t_min;
    }

    public String getT_max() {
        return t_max;
    }

    public String getT_morn() {
        return t_morn;
    }

    public String getT_day() {
        return t_day;
    }

    public String getT_eve() {
        return t_eve;
    }

    public String getT_night() {
        return t_night;
    }

    public String getW_speed() {
        return w_speed;
    }
}
