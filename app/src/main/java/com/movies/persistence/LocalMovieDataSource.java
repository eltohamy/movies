package com.movies.persistence;

import com.movies.MovieDataSource;
import com.movies.models.Movie;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Tohamy on 2/19/18.
 */

public class LocalMovieDataSource implements MovieDataSource {

    private final MovieDao movieDao;

    public LocalMovieDataSource(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public Flowable<Movie> getMovie() {
        return movieDao.getMovie();
    }

    @Override
    public void insertOrUpdateMovie(Movie movie) {
        movieDao.insertMovie(movie);
    }

    @Override
    public void insertOrUpdateMovies(List<Movie> movies) {
        movieDao.insertMovies(movies);
    }

    @Override
    public void deleteAllMovies() {
        movieDao.deleteAllMovies();
    }


}
