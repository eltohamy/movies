package com.movies.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.movies.R;
import com.movies.control.Const;
import com.movies.fragments.MoviesFragment;

/**
 * Created by Tohamy on 2/20/2018.
 */

public class SearchResultsActivity extends BaseActivity {

    private static final String TAG = SearchResultsActivity.class.getSimpleName();
    private String query;
    private Toolbar toolbar;
    private MoviesFragment moviesFragment;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        query = getIntent().getStringExtra(Const.SORT_BY_KEY);
        initUI();
        replacePopularMoviesFragment();

    }

    private void initUI() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);

        toolbarTitle.setText(query);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(SearchResultsActivity.this, R.color.colorPrimaryDark));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void replacePopularMoviesFragment() {
        toolbar.setTitle(R.string.popular_movies);
        if (moviesFragment == null) {
            moviesFragment = new MoviesFragment().newInstance(query, true);
        }
        replaceFragment(moviesFragment);
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
}

