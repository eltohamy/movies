package com.movies.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.movies.R;
import com.movies.deps.DaggerDeps;

import  com.movies.deps.Deps;
import  com.movies.networking.NetworkModule;
import java.io.File;

/**
 * Created by Tohamy on 2/19/2018.
 */

public class BaseActivity extends AppCompatActivity {

    Deps deps;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

    }

    public Deps getDeps() {
        return deps;
    }

    /*
    * replace fragment
    *
    * @param fragment
    */
    void replaceFragment(Fragment fragment) {
        if (!isDestroyed()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_body, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}
