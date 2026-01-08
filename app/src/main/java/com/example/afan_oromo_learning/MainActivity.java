package com.example.afan_oromo_learning;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.afan_oromo_learning.auth.LoginActivity;
import com.example.afan_oromo_learning.home.HomeActivity;
import com.example.afan_oromo_learning.utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPrefManager prefs = SharedPrefManager.getInstance(this);

        if (prefs.isLoggedIn()) {
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }

        finish();
    }
}