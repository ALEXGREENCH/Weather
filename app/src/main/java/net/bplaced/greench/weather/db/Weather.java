package net.bplaced.greench.weather.db;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

@Entity(tableName = "weather_table")
public class Weather {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "date")
    private String date;

    @NonNull
    @ColumnInfo(name = "city_name")
    private String city_name;

    @NonNull
    @ColumnInfo(name = "temp")
    private String temp;

    @NonNull
    @ColumnInfo(name = "img_status")
    private String img_status;

    @NonNull
    @ColumnInfo(name = "status")
    private String status;

    public Weather(String date, String city_name, String temp, String img_status, String status) {
        this.date = date;
        this.city_name = city_name;
        this.temp = temp;
        this.img_status = img_status;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getTemp() {
        return temp;
    }

    public String getImg_status() {
        return img_status;
    }

    public String getStatus() {
        return status;
    }
}
