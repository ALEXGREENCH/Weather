package net.bplaced.greench.weather.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public CitiesListAdapter(MainActivity activity){
        this.c = activity.getApplicationContext();
        mainView = activity;
        weatherList = new ArrayList<>();
    }

    public void setCities(List<Weather> weathers) {
        weatherList = weathers;
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
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray array_weather = object.getJSONArray("weather");
                    JSONObject object_weather = array_weather.getJSONObject(0);
                    String icon = object_weather.getString("icon");
                    String url_icon = "http://openweathermap.org/img/w/" + icon + ".png";
                    Picasso.get().load(url_icon).into(img);
                    String description = object_weather.getString("description");
                    view_status.setText(description);

                    JSONObject mainObj = object.getJSONObject("main");
                    Integer t = mainObj.getInt("temp");
                    view_t.setText(t > 0 ? "+" + String.valueOf(t) : String.valueOf(t));

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


}
