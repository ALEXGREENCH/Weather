package net.bplaced.greench.weather.net;

import net.bplaced.greench.weather.pojo.current_weather.CurrentWeather;
import net.bplaced.greench.weather.pojo.forecast.ForecastDaily;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {

    @GET("/data/2.5/forecast/daily")
    Call<ForecastDaily> getForecastDaily(@Query("q") String city,
                                         @Query("mode") String mode,
                                         @Query("units") String units,
                                         @Query("cnt") String cnt,
                                         @Query("lang") String lang,
                                         @Query("appid") String appid);

    @GET("/data/2.5/weather")
    Call<ResponseBody> getWeather(@Query("q") String city,
                                  @Query("mode") String mode,
                                  @Query("units") String units,
                                  @Query("lang") String lang,
                                  @Query("appid") String appid);
}
