
package net.bplaced.greench.weather.pojo.forecast;

import java.util.List;

public class WeatherList {

    private Integer dt, humidity, deg, clouds;
    private Temp temp;
    private Double pressure, speed, rain;
    private List<Weather> weather;

    public Integer getDt() {
        return dt;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Integer getDeg() {
        return deg;
    }

    public Integer getClouds() {
        return clouds;
    }

    public Temp getTemp() {
        return temp;
    }

    public Double getPressure() {
        return pressure;
    }

    public Double getSpeed() {
        return speed;
    }

    public Double getRain() {
        return rain;
    }

    public List<Weather> getWeather() {
        return weather;
    }
}
