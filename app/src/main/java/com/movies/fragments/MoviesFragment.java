package com.movies.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.movies.Injection;
import com.movies.R;
import com.movies.activities.MovieDetailsActivity;
import com.movies.adapters.MarginDecoration;
import com.movies.adapters.MoviesAdapter;
import com.movies.control.Const;
import com.movies.interfaces.MoviesView;
import com.movies.interfaces.OnMovieClickListener;
import com.movies.models.Movie;
import com.movies.models.MoviesListResponse;
import com.movies.networking.Service;
import com.movies.presenters.MoviesPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tohamy on 2/19/2018.
 */

public class MoviesFragment extends BaseFragment implements MoviesView, OnMoreListener, OnMovieClickListener {

    private static final String TAG = MoviesFragment.class.getSimpleName();
    private String sortBy;
    private View rootView;
    private int pageNum = 1;
    private boolean shouldLoadMore;
    private SwipeRefreshLayout refreshLayout;
    private SuperRecyclerView moviesRecycler;
    @Inject
    public Service service;
    private MoviesPresenter presenter;
    private ArrayList<Movie> movies = new ArrayList<>();
    private MoviesAdapter moviesAdapter;
    private FrameLayout emptyLayout;
    private TextView noResultText;
    private ViewModelFactory mViewModelFactory;
    private MovieViewModel mViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private boolean searchFlag;

    public MoviesFragment newInstance(String sortBy) {
        MoviesFragment moviesFragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(Const.SORT_BY_KEY, sortBy);
        moviesFragment.setArguments(args);
        return moviesFragment;
    }

    public MoviesFragment newInstance(String query, boolean searchFlag) {
        MoviesFragment moviesFragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(Const.SORT_BY_KEY, query);
        args.putBoolean(Const.SEARCH_KEY, searchFlag);
        moviesFragment.setArguments(args);
        return moviesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDeps().inject(this);
        rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        sortBy = getArguments().getString(Const.SORT_BY_KEY);
        searchFlag = getArguments().getBoolean(Const.SEARCH_KEY, false);
        pageNum = 1;
        shouldLoadMore = false;
        initUI();

        presenter = new MoviesPresenter(service, this);
        presenter.getMoviesList(pageNum, sortBy, Const.API_KEY, searchFlag);

        mViewModelFactory = Injection.provideViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MovieViewModel.class);

        return rootView;
    }

    private void initUI() {
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getMoviesList(pageNum, sortBy, Const.API_KEY, searchFlag);
                refreshLayout.setEnabled(false);
            }
        });
        refreshLayout.setEnabled(false);
        moviesRecycler = (SuperRecyclerView) rootView.findViewById(R.id.movies_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        moviesRecycler.setLayoutManager(gridLayoutManager);
        moviesRecycler.addItemDecoration(new MarginDecoration(getActivity()));
        moviesRecycler.setupMoreListener(this, 1);
        emptyLayout = (FrameLayout) rootView.findViewById(R.id.empty_layout);
        noResultText = (TextView) rootView.findViewById(R.id.no_result_text);
    }

    @Override
    public void showLoading() {
        emptyLayout.setVisibility(View.GONE);
        if (pageNum == 1)
            refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onFailure(String appErrorMessage) {
        shouldLoadMore = false;
        if (moviesRecycler != null) {
            moviesRecycler.getMoreProgressView().setVisibility(View.GONE);
            moviesRecycler.getProgressView().setVisibility(View.GONE);
            moviesRecycler.getEmptyView().setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(appErrorMessage))
            noResultText.setText(appErrorMessage);
        emptyLayout.setVisibility(View.VISIBLE);
        refreshLayout.setEnabled(true);
    }

    @Override
    public void getMoviesListSuccess(MoviesListResponse moviesListResponse) {
        emptyLayout.setVisibility(View.GONE);
        if (moviesListResponse != null) {
            if (moviesListResponse != null && !moviesListResponse.getMovies().isEmpty()) {

                mDisposable.add(mViewModel.insertMovies(moviesListResponse.getMovies())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> updateUI(moviesListResponse.getMovies()),
                                throwable -> Log.e(TAG, "Unable to insert movies", throwable)));

            } else {
                if (pageNum == 1) {
                    if (moviesRecycler != null) {
                        moviesRecycler.getMoreProgressView().setVisibility(View.GONE);
                        moviesRecycler.getProgressView().setVisibility(View.GONE);
                        moviesRecycler.setPaddingRelative(0, 0, 0, 0);
                        moviesRecycler.getEmptyView().setVisibility(View.VISIBLE);
                    }
                } else {
                    if (moviesRecycler != null) {
                        moviesRecycler.getMoreProgressView().setVisibility(View.GONE);
                    }
                }
                shouldLoadMore = false;
            }
        }
    }

    private void updateUI(List<Movie> newMovies) {
        shouldLoadMore = true;
        for (Movie movie : newMovies) {
            movies.add(movie);
        }
        if (pageNum == 1) {
            moviesAdapter = new MoviesAdapter(getActivity(), movies, this);
            if (moviesRecycler != null)
                moviesRecycler.setAdapter(moviesAdapter);
        }
        if (moviesAdapter != null)
            moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore,
                            int maxLastVisiblePosition) {
        loadMore();
    }

    private void loadMore() {
        if (shouldLoadMore) {
            if (moviesRecycler != null)
                moviesRecycler.getMoreProgressView().setVisibility(View.VISIBLE);
            pageNum++;
            presenter.getMoviesList(pageNum, sortBy, Const.API_KEY, searchFlag);
        } else {
            if (moviesRecycler != null)
                moviesRecycler.getMoreProgressView().setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra(Const.ID_KEY, movie.getId());
        intent.putExtra(Const.TITLE_KEY, movie.getTitle());
        startActivity(intent);
    }
}
