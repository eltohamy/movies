package com.movies.presenters;

import com.movies.interfaces.MoviesView;
import com.movies.models.MoviesListResponse;
import com.movies.networking.NetworkError;
import com.movies.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Tohamy on 2/19/2018.
 */

public class MoviesPresenter {
    private final Service service;
    private final MoviesView view;
    private CompositeSubscription subscriptions;

    public MoviesPresenter(Service service, MoviesView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getMoviesList(int pageNum,String sortBy,String apiKey,boolean searchFlag) {
        view.showLoading();

        Subscription subscription = service.getMoviesList(new Service.GetMoviesListCallback() {
            @Override
            public void onSuccess(MoviesListResponse moviesListResponse) {
                view.hideLoading();
                view.getMoviesListSuccess(moviesListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                view.onFailure(networkError.getAppErrorMessage());
            }

        },pageNum,sortBy,apiKey,searchFlag);

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
