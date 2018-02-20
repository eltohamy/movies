package com.movies.interfaces;

import com.movies.models.MovieDetailsResponse;

/**
 * Created by Tohamy on 2/20/2018.
 */

public interface MovieDetailsView {

    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

    void getMovieDetailsSuccess(MovieDetailsResponse movieDetailsResponse);

}
