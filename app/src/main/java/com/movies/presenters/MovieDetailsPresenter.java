package com.movies.presenters;

import com.movies.interfaces.MovieDetailsView;
import com.movies.models.MovieDetailsResponse;
import com.movies.networking.NetworkError;
import com.movies.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Tohamy on 2/20/2018.
 */

public class MovieDetailsPresenter {

    private final Service service;
    private final MovieDetailsView view;
    private CompositeSubscription subscriptions;

    public MovieDetailsPresenter(Service service, MovieDetailsView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getMovieDetails(int id,String apiKey) {
        view.showLoading();

        Subscription subscription = service.getMovieDetails(new Service.GetMovieDetailsCallback() {
            @Override
            public void onSuccess(MovieDetailsResponse moviesSearchResponse) {
                view.hideLoading();
                view.getMovieDetailsSuccess(moviesSearchResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                view.onFailure(networkError.getAppErrorMessage());
            }

        },id,apiKey);

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
