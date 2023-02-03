package com.example.myapplication.room_database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.room_database.TelevisionModel.Television;
import com.example.myapplication.room_database.TelevisionModel.TelevisionDao;

@Database(entities = {Television.class}, version = 3)
public abstract class TelevisionDatabase extends RoomDatabase {
    private static TelevisionDatabase instance;

    public abstract TelevisionDao televisionDao();

    public static synchronized TelevisionDatabase getInstance(Context context) {
        if (instance == null) {
            instance =
                    Room.databaseBuilder(context.getApplicationContext(),
                                    TelevisionDatabase.class, "course_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(TelevisionDatabase instance) {
            TelevisionDao dao = instance.televisionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
