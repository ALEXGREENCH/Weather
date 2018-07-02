package net.bplaced.greench.weather.ui.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.bplaced.greench.weather.AppUtils;
import net.bplaced.greench.weather.R;
import net.bplaced.greench.weather.pojo.forecast.ForecastDaily;
import net.bplaced.greench.weather.pojo.forecast.WeatherList;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForcastViewHolder>{


    private List<WeatherList> weatherList;
    private Context c;

    public ForecastAdapter(ForecastDaily forecastDaily, Context c) {
        weatherList = forecastDaily.getWeatherList();
        this.c = c;
    }


    public void clear() {
        if (weatherList != null) {
            final int size = weatherList.size();
            weatherList.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    @NonNull
    @Override
    public ForcastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ForcastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ForcastViewHolder holder, int position) {
        Log.i("TAG", "pos = " + position);
        Log.i("TAG", "dt = " + getDate(weatherList.get(position).getDt()));

        Double min = weatherList.get(position).getTemp().getMin();
        Integer int_min = min.intValue();
        Double max = weatherList.get(position).getTemp().getMax();
        Integer int_max = max.intValue();

        Log.i("TAG", "dt = " + getDate(weatherList.get(position).getDt()));

        String text_min = int_min > 0 ? "+"+String.valueOf(int_min) : String.valueOf(int_min);
        String text_max = int_max > 0 ? "+"+String.valueOf(int_max) : String.valueOf(int_max);

        TextView text_dt = holder.itemView.findViewById(R.id.dt);
        text_dt.setText(getDate(weatherList.get(position).getDt()));

        ImageView icon = holder.itemView.findViewById(R.id.icon);
        icon.setImageResource(AppUtils.getNameResIcon(weatherList.get(position).getWeather().get(0).getIcon(), c));

        TextView text_temp = holder.itemView.findViewById(R.id.temp);
        text_temp.setText(text_min+"…"+text_max);

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    static class ForcastViewHolder extends RecyclerView.ViewHolder{
        ForcastViewHolder(View itemView) {
            super(itemView);
        }
    }

    private String getDate(long timeStamp){
        try{
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timeStamp * 1000L);
            return  DateFormat.format("dd/MM", cal).toString();
        }
        catch(Exception ex){
            return "xx";
        }
    }
}
