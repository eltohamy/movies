package com.movies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.movies.deps.DaggerDeps;
import com.movies.deps.Deps;
import com.movies.networking.NetworkModule;

import java.io.File;

/**
 * Created by Tohamy on 2/19/2018.
 */

public class BaseFragment extends Fragment {

    Deps deps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getActivity().getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

    }

    public Deps getDeps() {
        return deps;
    }
}

