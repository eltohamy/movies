package com.movies.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.movies.models.Movie;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Tohamy on 2/19/18.
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movies LIMIT 1")
    Flowable<Movie> getMovie();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);


    @Query("DELETE FROM Movies")
    void deleteAllMovies();

}
