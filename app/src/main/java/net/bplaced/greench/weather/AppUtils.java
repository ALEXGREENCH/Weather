package net.bplaced.greench.weather;

import android.content.Context;
import android.util.Log;

public class AppUtils {

    public static String getTimeMillis() {
        Long tsLong = System.currentTimeMillis();
        return tsLong.toString();
    }


    public static Integer getNameResIcon(String s, Context c){
        String res_name = "";
        switch (s){
            case "01d":
                res_name = "clear_sky";
                break;
            case "01n":
                res_name = "clear_sky_n";
                break;
            case "02d":
                res_name = "few_clouds";
                break;
            case "02n":
                res_name = "few_clouds_n";
                break;
            case "03d":
                res_name = "scattered_clouds";
                break;
            case "03n":
                res_name = "scattered clouds";
                break;
            case "04d":
                res_name = "broken_clouds";
                break;
            case "04n":
                res_name = "broken clouds";
                break;
            case "09d":
                res_name = "shower_rain";
                break;
            case "09n":
                res_name = "shower_rain";
                break;
            case "10d":
                res_name = "rain";
                break;
            case "10n":
                res_name = "rain_n";
                break;
            case "11d":
                res_name = "thunderstorm";
                break;
            case "11n":
                res_name = "thunderstorm";
                break;
            case "13d":
                res_name = "snow";
                break;
            case "13n":
                res_name = "snow";
                break;
            case "50d":
                res_name = "mist";
                break;
            case "50n":
                res_name = "mist";
                break;
        }
        Log.i("TAG", "img = " + res_name);

        return c.getResources().getIdentifier(res_name,"drawable", c.getPackageName());
    }
}
