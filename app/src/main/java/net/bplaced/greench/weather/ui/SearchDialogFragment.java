package net.bplaced.greench.weather.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.bplaced.greench.weather.R;
import net.bplaced.greench.weather.net.ApiClient;
import net.bplaced.greench.weather.pojo.forecast.ForecastDaily;
import net.bplaced.greench.weather.pojo.forecast.WeatherList;

import java.util.List;

import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.bplaced.greench.weather.Constants.API_KEY;

public class SearchDialogFragment extends DialogFragment {


    private EditText et_search;
    private IMainView mainView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_add_city, container, false);
        mainView = (MainActivity) getActivity();

        et_search = v.findViewById(R.id.input_city);
        Button btn_search = v.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(view -> {
            if (et_search.getText().length() != 0){
                req_weather(et_search.getText().toString());
            }
        });

        return v;
    }


    private void req_weather(String city){
        ApiClient.getRequestInterface()
                .getForecastDaily(city, "json", "metric", "4", "ru", API_KEY)
                .enqueue(new Callback<ForecastDaily>() {
            @Override
            public void onResponse(Call<ForecastDaily> call, Response<ForecastDaily> response) {
                ForecastDaily forecastDaily = response.body();
                int code = response.code();
                if (code == 200) {
                    List<WeatherList> weatherList = forecastDaily.getWeatherList();

                    //tvCity.setText(forecastDaily.getCity().getName());

                    for(WeatherList w : weatherList){
                        //Log.i("TAG", "Дата: " + getDate(w.getDt()));
                        Log.i("TAG", "Минимальная: " + w.getTemp().getMax());
                        Log.i("TAG", "Максимальная: " + w.getTemp().getMin());
                        Log.i("TAG", "Утром: " + w.getTemp().getMorn());
                        Log.i("TAG", "Днём: " + w.getTemp().getDay());
                        Log.i("TAG", "Вечером: " + w.getTemp().getEve());
                        Log.i("TAG", "Ночью: " + w.getTemp().getNight());
                        Log.i("TAG", "Скорость ветра: " + w.getSpeed());
                        Log.i("TAG", w.getWeather().get(0).getDescription());
                    }

                    mainView.addCityToDB(et_search.getText().toString());
                    getDialog().dismiss();

                }else {
                    Toast.makeText(getActivity(), "Ошибка!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ForecastDaily> call, Throwable t) {
                //
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}
