package com.movies.deps;

import com.movies.activities.HomeActivity;
import com.movies.activities.MovieDetailsActivity;
import com.movies.activities.SearchResultsActivity;
import com.movies.fragments.MoviesFragment;
import com.movies.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Tohamy on 2/19/2018.
 */

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {

    void inject(HomeActivity homeActivity);

    void inject(MoviesFragment moviesFragment);

    void inject(MovieDetailsActivity movieDetailsActivity);

}