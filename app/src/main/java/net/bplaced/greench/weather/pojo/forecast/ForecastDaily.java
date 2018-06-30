
package net.bplaced.greench.weather.pojo.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastDaily {

    private City city;
    private String cod;
    private Double message;
    private Integer cnt;
    @SerializedName("list")
    private List<WeatherList> weatherList;

    public City getCity() {
        return city;
    }

    public String getCod() {
        return cod;
    }

    public Double getMessage() {
        return message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public List<WeatherList> getWeatherList() {
        return weatherList;
    }
}
