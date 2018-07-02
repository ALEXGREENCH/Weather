package net.bplaced.greench.weather.db;

import android.content.Context;
import android.os.AsyncTask;

import net.bplaced.greench.weather.AppUtils;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Weather.class}, version = 1, exportSchema = false)
public abstract class WeatherRoomDatabase extends RoomDatabase {

    public abstract WeatherDao wordDao();

    private static WeatherRoomDatabase INSTANCE;

    public static WeatherRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WeatherRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WeatherRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WeatherDao mDao;

        PopulateDbAsync(WeatherRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (mDao.getAllCities().getValue() == null){
                Weather weather = new Weather(AppUtils.getTimeMillis(),"Москва", "", "", "");
                mDao.insert(weather);
                weather = new Weather(AppUtils.getTimeMillis(),"Санкт-Петербург", "", "", "");
                mDao.insert(weather);
            }else {
                mDao.getAllCities();
            }

            //mDao.getAllCities();
            //mDao.deleteAll();
            //Weather word = new Weather("Hello");
            //mDao.insert(word);
            //word = new Weather("World");
            //mDao.insert(word);
            return null;
        }
    }




}
