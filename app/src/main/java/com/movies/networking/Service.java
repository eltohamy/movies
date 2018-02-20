package com.movies.networking;

import com.movies.models.MovieDetailsResponse;
import com.movies.models.MoviesListResponse;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Tohamy on 2/19/2018.
 */

public class Service {
    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getMoviesList(final GetMoviesListCallback callback, int pageNum, String sortBy, String apiKey, boolean searchFlag) {
        if (searchFlag)
            return networkService.searchMovies(pageNum, sortBy, apiKey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends MoviesListResponse>>() {
                        @Override
                        public Observable<? extends MoviesListResponse> call(Throwable throwable) {
                            return Observable.error(throwable);
                        }
                    })
                    .subscribe(new Subscriber<MoviesListResponse>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            callback.onError(new NetworkError(e));

                        }

                        @Override
                        public void onNext(MoviesListResponse moviesListResponse) {
                            callback.onSuccess(moviesListResponse);

                        }
                    });
        else return networkService.getMoviesList(pageNum, sortBy, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MoviesListResponse>>() {
                    @Override
                    public Observable<? extends MoviesListResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<MoviesListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(MoviesListResponse moviesListResponse) {
                        callback.onSuccess(moviesListResponse);

                    }
                });
    }

    public interface GetMoviesListCallback {
        void onSuccess(MoviesListResponse moviesListResponse);

        void onError(NetworkError networkError);
    }

    public Subscription getMovieDetails(final GetMovieDetailsCallback callback, int id, String apiKey) {
        return networkService.getMovieDetails(id, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MovieDetailsResponse>>() {
                    @Override
                    public Observable<? extends MovieDetailsResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<MovieDetailsResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(MovieDetailsResponse movieDetailsResponse) {
                        callback.onSuccess(movieDetailsResponse);

                    }
                });
    }

    public interface GetMovieDetailsCallback {
        void onSuccess(MovieDetailsResponse movieDetailsResponse);

        void onError(NetworkError networkError);
    }
}
