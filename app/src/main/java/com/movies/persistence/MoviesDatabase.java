package com.movies.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.movies.models.Movie;

/**
 * Created by Tohamy on 2/19/18.
 */

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    private static volatile MoviesDatabase INSTANCE;

    public abstract MovieDao movieDao();

    public static MoviesDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MoviesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoviesDatabase.class, "Movies.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}