package com.movies.control;

import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Tohamy on 2/19/2018.
 */

public class AppControl extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Fabric.with(this, new Crashlytics());
    }
}
