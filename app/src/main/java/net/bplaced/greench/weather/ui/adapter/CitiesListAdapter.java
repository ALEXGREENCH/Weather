package net.bplaced.greench.weather.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.bplaced.greench.weather.R;
import net.bplaced.greench.weather.db.Weather;
import net.bplaced.greench.weather.net.ApiClient;
import net.bplaced.greench.weather.pojo.current_weather.CurrentWeather;
import net.bplaced.greench.weather.ui.IMainView;
import net.bplaced.greench.weather.ui.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.bplaced.greench.weather.Constants.API_KEY;

public class CitiesListAdapter extends RecyclerView.Adapter<CitiesListAdapter.HistoryViewHolder> {

    private List<Weather> weatherList;
    private Context c;
    private IMainView mainView;
    private boolean firstLinkDb = true;
    private int lastPosition;

    public CitiesListAdapter(MainActivity activity){
        this.c = activity.getApplicationContext();
        mainView = activity;
        weatherList = new ArrayList<>();
    }

    public void setCities(List<Weather> weathers) {
        weatherList = weathers;
        lastPosition = (getItemCount() - 1);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        setAnimation(holder.itemView, position);

        if (firstLinkDb) {
            String name = weatherList.get(0).getCity_name();
            mainView.setCity(name);
            firstLinkDb = false;
        }

        Weather weather = weatherList.get(position);

        TextView text_city = holder.itemView.findViewById(R.id.city);
        text_city.setText(weather.getCity_name());

        TextView text_temperature = holder.itemView.findViewById(R.id.temperature);
        text_temperature.setText(weather.getTemp().isEmpty() ? "…" : weather.getTemp());

        ImageView img_status = holder.itemView.findViewById(R.id.img_status);
        //img_status.setImageDrawable(weather.getImg_status());

        TextView text_status = holder.itemView.findViewById(R.id.status);
        text_status.setText(weather.getStatus().isEmpty() ? "…" : weather.getStatus());

        load_data(weather.getCity_name(), text_temperature, img_status, text_status);

        holder.itemView.setOnClickListener(view -> mainView.setCity(weather.getCity_name()));
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder{
        HistoryViewHolder(View itemView) {
            super(itemView);
        }
    }


    private void load_data(String city, TextView view_t, ImageView img, TextView view_status) {
        /*
        Log.i("TAG", "city name = " + city);
        ApiClient.getRequestInterface().getWeather(city, "json", "metric", "ru", API_KEY).enqueue(new Callback<CurrentWeather>() {
                @Override
                public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                    CurrentWeather currentWeather = response.body();

                    String icon = currentWeather.getWeather().get(0).getIcon();

                    String url_icon = "http://openweathermap.org/img/w/" + icon + ".png";
                    System.out.println("Ссылка на иконку: " + url_icon);
                    //Glide.with(c)
                    //        .load(url_icon)
                    //        .into(img);
                    Picasso.get().load(url_icon).into(img);



                    Integer t = currentWeather.getMain().getTemp();
                    view_t.setText(t > 0 ? "+" + String.valueOf(t) : String.valueOf(t));

                    view_status.setText(currentWeather.getWeather().get(0).getDescription());
                }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                Log.i("TAG", "city error = " + city);
                Log.i("TAG", "error = " + t.getMessage());

            }
        });
        */

        ApiClient.getRequestInterface().getWeather(city, "json", "metric", "ru", API_KEY).enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray array_weather = object.getJSONArray("weather");
                    JSONObject object_weather = array_weather.getJSONObject(0);
                    String icon = object_weather.getString("icon");


                    //String url_icon = "http://openweathermap.org/img/w/" + icon + ".png";
                    //Picasso.get().load(url_icon).into(img);

                    img.setImageResource(getNameResIcon(icon));

                    String description = object_weather.getString("description");
                    String cap_description = description.substring(0, 1).toUpperCase() + description.substring(1);
                    view_status.setText(cap_description);

                    JSONObject mainObj = object.getJSONObject("main");
                    Integer t = mainObj.getInt("temp");
                    if (t > 0){
                        view_t.setText("+" + String.valueOf(t));
                        view_t.setTextColor(Color.parseColor("#ff9800"));
                    }else {
                        view_t.setText(String.valueOf(t));
                        view_t.setTextColor(Color.parseColor("#00e5ff"));
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private Integer getNameResIcon(String s){
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


    private void setAnimation(View viewToAnimate, int position) {
        Log.i("TAG", "position = " + position + ", lastPosition = " + lastPosition);

        if (position >= lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(c, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
