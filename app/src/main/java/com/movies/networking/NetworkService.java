package com.movies.networking;

import com.movies.models.MovieDetailsResponse;
import com.movies.models.MoviesListResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Tohamy on 2/19/2018.
 */

public interface NetworkService {

    @GET("discover/movie")
    Observable<MoviesListResponse> getMoviesList(@Query("page") int page, @Query("sort_by") String sortBy, @Query("api_key") String apiKey);

    @GET("search/movie")
    Observable<MoviesListResponse> searchMovies(@Query("page") int page, @Query("query") String query, @Query("api_key") String apiKey);

    @GET("movie/{id}")
    Observable<MovieDetailsResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

}
