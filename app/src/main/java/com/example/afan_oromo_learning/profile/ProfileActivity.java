package com.example.afan_oromo_learning.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.auth.LoginActivity;
import com.example.afan_oromo_learning.utils.SharedPrefManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvName, tvEmail, tvLevel, tvStreak, tvWords;
    private MaterialButton btnSettings, btnLogout;
    private SharedPrefManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        prefs = SharedPrefManager.getInstance(this);
        initViews();
        setupToolbar();
        loadProfileData();
        setupClickListeners();
    }

    private void initViews() {
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvLevel = findViewById(R.id.tvLevel);
        tvStreak = findViewById(R.id.tvStreak);
        tvWords = findViewById(R.id.tvWords);
        btnSettings = findViewById(R.id.btnSettings);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("My Profile");
    }

    private void loadProfileData() {
        tvName.setText(prefs.getUserName());
        tvEmail.setText(prefs.getUserEmail());
        tvLevel.setText("Level: " + prefs.getUserLevel());
        tvStreak.setText("Learning Streak: " + prefs.getLearningStreak() + " Lessons");
        tvWords.setText("Words Learned: " + prefs.getWordsLearned());
    }

    private void setupClickListeners() {
        btnSettings.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, SettingsActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            prefs.clearUserData();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });
    }
}