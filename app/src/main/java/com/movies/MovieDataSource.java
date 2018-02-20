package com.movies;

import com.movies.models.Movie;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Tohamy on 2/19/18.
 */

public interface MovieDataSource {

    Flowable<Movie> getMovie();

    void insertOrUpdateMovie(Movie movie);

    void insertOrUpdateMovies(List<Movie> movies);

    void deleteAllMovies();
}
