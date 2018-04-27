package com.m2.montano.j.localizadorcajeroscochalav2.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

import com.m2.montano.j.localizadorcajeroscochalav2.MainActivity;
import com.m2.montano.j.localizadorcajeroscochalav2.R;

/**
 * Created by Juan on 9/8/2017.
 */

public class SplashScreenActivity extends Activity {
    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //orientacion
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        },SPLASH_SCREEN_DELAY);
    }
}
