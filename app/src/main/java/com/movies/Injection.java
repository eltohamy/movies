package com.movies;

import android.content.Context;

import com.movies.fragments.ViewModelFactory;
import com.movies.persistence.LocalMovieDataSource;
import com.movies.persistence.MoviesDatabase;

/**
 * Created by Tohamy on 2/19/18.
 */

public class Injection {

    public static MovieDataSource provideUserDataSource(Context context) {
        MoviesDatabase database = MoviesDatabase.getInstance(context);
        return new LocalMovieDataSource(database.movieDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        MovieDataSource dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);
    }
}
