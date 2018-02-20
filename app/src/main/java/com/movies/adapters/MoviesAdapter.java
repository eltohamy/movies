package com.movies.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.movies.R;
import com.movies.control.Const;
import com.movies.interfaces.OnMovieClickListener;
import com.movies.models.Movie;

import java.util.ArrayList;

/**
 * Created by Tohamy on 2/19/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private final OnMovieClickListener onMovieClickListener;
    private ArrayList<Movie> movies;
    private Context context;

    public MoviesAdapter(Context context, ArrayList<Movie> movies, OnMovieClickListener onMovieClickListener) {
        this.movies = movies;
        this.onMovieClickListener = onMovieClickListener;
        this.context = context;
    }


    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder holder, int position) {
        holder.click(movies.get(position), onMovieClickListener);
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.movieReleaseDate.setText(movies.get(position).getReleaseDate());
        String imageURL = Const.IMAGES_BASE_URL + movies.get(position).getPosterPath();
        Uri uri = Uri.parse(imageURL);
        holder.moviePoster.setImageURI(uri);
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView movieTitle;
        public TextView movieReleaseDate;
        public SimpleDraweeView moviePoster;

        public ViewHolder(View itemView) {
            super(itemView);
            moviePoster = (SimpleDraweeView) itemView.findViewById(R.id.movie_poster);
            movieTitle = (TextView) itemView.findViewById(R.id.movie_title);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movie_release_date);

        }

        public void click(final Movie movie, final OnMovieClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMovieClickListener.onClick(movie);
                }
            });
        }
    }


}