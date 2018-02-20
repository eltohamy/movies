/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.movies.fragments;

import android.arch.lifecycle.ViewModel;

import com.movies.MovieDataSource;
import com.movies.models.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by Tohamy on 2/19/2018.
 */

public class MovieViewModel extends ViewModel {

    private final MovieDataSource movieDataSource;

    private Movie mMovie;

    private List<Movie> mMovies;

    public MovieViewModel(MovieDataSource movieDataSource) {
        this.movieDataSource = movieDataSource;
    }

    public Flowable<String> getMovieTitle() {
        return movieDataSource.getMovie()
                .map(movie -> {
                    mMovie = movie;
                    return movie.getTitle();
                });

    }

    public Completable insertMovie(Movie movie) {
        return Completable.fromAction(() -> {
            mMovie = movie;
            movieDataSource.insertOrUpdateMovie(movie);
        });
    }


    public Completable insertMovies(List<Movie> movies) {
        return Completable.fromAction(() -> {
            mMovies = movies;
            movieDataSource.insertOrUpdateMovies(movies);
        });
    }
}
