package com.example.afan_oromo_learning.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.auth.LoginActivity;
import com.example.afan_oromo_learning.home.HomeActivity;
import com.example.afan_oromo_learning.utils.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            SharedPrefManager prefs = SharedPrefManager.getInstance(this);

            if (prefs.isFirstLaunch()) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            } else if (prefs.isLoggedIn()) {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }

            finish();
        }, SPLASH_DURATION);
    }
}