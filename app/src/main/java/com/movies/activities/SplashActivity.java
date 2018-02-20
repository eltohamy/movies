package com.movies.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;

import com.movies.R;

/**
 * Created by Tohamy on 2/19/18.
 */

public class SplashActivity extends BaseActivity {

    private static final long SPLASH_TIME = 3000;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        logo = (ImageView) findViewById(R.id.logo);

        startAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        }, SPLASH_TIME);
    }

    private void startAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator animator5 = ObjectAnimator.ofFloat(logo, "alpha", 0, 1f);
        animator5.setRepeatCount(0);
        animator5.setDuration(1000);


        ObjectAnimator animator6 = ObjectAnimator.ofFloat(logo, "scaleX", 0, 1f);
        animator6.setRepeatCount(0);
        animator6.setDuration(1000);

        ObjectAnimator animator7 = ObjectAnimator.ofFloat(logo, "scaleY", 0, 1f);
        animator7.setRepeatCount(0);
        animator7.setDuration(1000);

        set.playTogether(animator5, animator6, animator7);

        set.start();
    }
}
