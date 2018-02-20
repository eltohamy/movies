package com.movies.interfaces;

import com.movies.models.MoviesListResponse;

/**
 * Created by Tohamy on 2/19/2018.
 */

public interface MoviesView {

    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

    void getMoviesListSuccess(MoviesListResponse moviesListResponse);

}
