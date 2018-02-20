package com.movies.activities;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.movies.R;
import com.movies.control.Const;
import com.movies.interfaces.MovieDetailsView;
import com.movies.models.MovieDetailsResponse;
import com.movies.networking.Service;
import com.movies.presenters.MovieDetailsPresenter;

import javax.inject.Inject;

/**
 * Created by Tohamy on 2/19/2018.
 */

public class MovieDetailsActivity extends BaseActivity implements MovieDetailsView {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private int id;
    @Inject
    public Service service;
    private MovieDetailsPresenter presenter;
    private SimpleDraweeView moviePoster;
    private TextView movieTitle;
    private TextView movieReleaseDate;
    private TextView movieDetails;
    private NestedScrollView scrollView;
    private ProgressBar progressBar;
    private FrameLayout emptyLayout;
    private TextView noResultText;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);
        setContentView(R.layout.activity_movie_details);

        id = getIntent().getIntExtra(Const.ID_KEY, -1);
        title = getIntent().getStringExtra(Const.TITLE_KEY);
        initUI();

        presenter = new MovieDetailsPresenter(service, this);
        presenter.getMovieDetails(id, Const.API_KEY);


    }

    private void initUI() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);

        toolbarTitle.setText(title);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(MovieDetailsActivity.this, R.color.colorPrimaryDark));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        scrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        moviePoster = (SimpleDraweeView) findViewById(R.id.movie_poster);
        movieTitle = (TextView) findViewById(R.id.movie_title);
        movieReleaseDate = (TextView) findViewById(R.id.movie_release_date);
        movieDetails = (TextView) findViewById(R.id.movie_details);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        emptyLayout = (FrameLayout) findViewById(R.id.empty_layout);
        noResultText = (TextView) findViewById(R.id.no_result_text);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {
        scrollView.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(appErrorMessage))
            noResultText.setText(appErrorMessage);
        emptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void getMovieDetailsSuccess(MovieDetailsResponse movieDetailsResponse) {
        emptyLayout.setVisibility(View.GONE);
        if (movieDetailsResponse != null) {
            updateUI(movieDetailsResponse);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void updateUI(MovieDetailsResponse movieDetailsResponse) {
        movieTitle.setText(movieDetailsResponse.getTitle());
        movieReleaseDate.setText(movieDetailsResponse.getReleaseDate());
        movieDetails.setText(movieDetailsResponse.getOverview());
        String imageURL = Const.IMAGES_BASE_URL + movieDetailsResponse.getPosterPath();
        Uri uri = Uri.parse(imageURL);
        moviePoster.setImageURI(uri);
    }
}
