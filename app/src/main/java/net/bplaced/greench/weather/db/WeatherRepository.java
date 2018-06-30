package net.bplaced.greench.weather.db;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;


public class WeatherRepository {

    private WeatherDao mWeatherDao;
    private LiveData<List<Weather>> mAllWords;

    WeatherRepository(Application application) {
        WeatherRoomDatabase db = WeatherRoomDatabase.getDatabase(application);
        mWeatherDao = db.wordDao();
        mAllWords = mWeatherDao.getAllCities();
    }

    LiveData<List<Weather>> getAllWords() {
        return mAllWords;
    }

    public void insert(Weather weather) {
        new insertAsyncTask(mWeatherDao).execute(weather);
    }

    public void clean(){
        new cleanAsyncTask(mWeatherDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Weather, Void, Void> {

        private WeatherDao mAsyncTaskDao;

        insertAsyncTask(WeatherDao weatherDao) {
            mAsyncTaskDao = weatherDao;
        }

        @Override
        protected Void doInBackground(Weather... weathers) {
            mAsyncTaskDao.insert(weathers[0]);
            return null;
        }
    }

    private static class cleanAsyncTask extends AsyncTask<Void, Void, Void> {

        private WeatherDao mAsyncTaskDao;

        cleanAsyncTask(WeatherDao weatherDao) {
            mAsyncTaskDao = weatherDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
