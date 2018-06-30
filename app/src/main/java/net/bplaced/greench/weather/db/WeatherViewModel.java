package net.bplaced.greench.weather.db;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class WeatherViewModel extends AndroidViewModel {

    private WeatherRepository mRepository;

    private LiveData<List<Weather>> mAllWords;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WeatherRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<Weather>> getAllWords() {
        return mAllWords;
    }

    public void insert(Weather weather) {
        mRepository.insert(weather);
    }

    public void allClean(){
        mRepository.clean();
    }
}
