package net.bplaced.greench.weather.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.bplaced.greench.weather.AppUtils;
import net.bplaced.greench.weather.R;
import net.bplaced.greench.weather.db.Weather;
import net.bplaced.greench.weather.db.WeatherViewModel;
import net.bplaced.greench.weather.net.ApiClient;
import net.bplaced.greench.weather.pojo.forecast.ForecastDaily;
import net.bplaced.greench.weather.pojo.forecast.WeatherList;
import net.bplaced.greench.weather.ui.adapter.CitiesListAdapter;
import net.bplaced.greench.weather.ui.adapter.ForecastAdapter;
import net.bplaced.greench.weather.ui.view.CenteredToolbar;

import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.bplaced.greench.weather.Constants.API_KEY;

public class MainActivity extends AppCompatActivity implements IMainView {

    //RequestInterface requestInterface;

    //String city = "Москва";

    private CenteredToolbar toolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;


    RecyclerView list_cities, list_forcast;
    CitiesListAdapter citiesListAdapter;
    ForecastAdapter forecastAdapter;
    WeatherViewModel _weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(drawerToggle);

        list_cities = findViewById(R.id.list_cities);
        list_forcast = findViewById(R.id.list_forcast);
        list_cities.setLayoutManager(new LinearLayoutManager(this));
        list_forcast.setLayoutManager(new LinearLayoutManager(this));

        citiesListAdapter = new CitiesListAdapter(this);

        Button btn_add_city = findViewById(R.id.btn_add_city);
        btn_add_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();

        list_cities.setAdapter(citiesListAdapter);
        listener_db();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.open_settings, menu);
        return true;
    }


    void listener_db(){
        _weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        _weatherViewModel.getAllWords().observe(this, data -> citiesListAdapter.setCities(data));
    }

    @Override
    public void setCity(String city) {
        if (forecastAdapter != null)
            forecastAdapter.clear();

        toolbar.setTitle(city);
        mDrawer.closeDrawer(GravityCompat.START);
        load_data_forecast(city);
    }

    private void load_data_forecast(String name_city) {
        ApiClient.getRequestInterface().getForecastDaily(name_city, "json", "metric", "7", "ru", API_KEY)
                .enqueue(new Callback<ForecastDaily>() {
                    @Override
                    public void onResponse(Call<ForecastDaily> call, Response<ForecastDaily> response) {
                        ForecastDaily forecastDaily = response.body();
                        int code = response.code();
                        if (code == 200) {
                            forecastAdapter = new ForecastAdapter(forecastDaily, getApplicationContext());
                            list_forcast.setAdapter(forecastAdapter);
                            //List<WeatherList> weatherList = forecastDaily.getWeatherList();
                            //tvCity.setText(forecastDaily.getCity().getName());
                    /*
                    for(WeatherList w : weatherList){
                        Log.i("TAG", "Дата: " + getDate(w.getDt()));
                        Log.i("TAG", "Минимальная: " + w.getTemp().getMax());
                        Log.i("TAG", "Максимальная: " + w.getTemp().getMin());
                        Log.i("TAG", "Утром: " + w.getTemp().getMorn());
                        Log.i("TAG", "Днём: " + w.getTemp().getDay());
                        Log.i("TAG", "Вечером: " + w.getTemp().getEve());
                        Log.i("TAG", "Ночью: " + w.getTemp().getNight());
                        Log.i("TAG", "Скорость ветра: " + w.getSpeed());
                        Log.i("TAG", w.getWeather().get(0).getDescription());
                    }
                    */
                        }else {
                            Toast.makeText(MainActivity.this, "Ошибка!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ForecastDaily> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Ошибка!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void addCityToDB(String name_city) {
        _weatherViewModel.insert(new Weather(AppUtils.getTimeMillis(), name_city, "", "", ""));
    }

    private void showDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SearchDialogFragment searchDialogFragment = new SearchDialogFragment();
        //searchDialogFragment.setCancelable(false);
        searchDialogFragment.show(fm, "fragment_edit_name");
    }
}
