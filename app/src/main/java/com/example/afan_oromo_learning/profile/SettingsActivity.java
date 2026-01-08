package com.example.afan_oromo_learning.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.home.HomeActivity;
import com.example.afan_oromo_learning.utils.SharedPrefManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;


public class SettingsActivity extends AppCompatActivity {
    private SwitchMaterial switchNotifications, switchSound;
    private SharedPrefManager prefs;
    private Button btnReset; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = SharedPrefManager.getInstance(this);
        initViews();
        setupToolbar();
        loadSettings();
        setupClickListeners();
    }

    private void initViews() {
        switchNotifications = findViewById(R.id.switchNotifications);
        switchSound = findViewById(R.id.switchSound);
        btnReset = findViewById(R.id.btnResetProgress);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("Settings");
    }

    private void loadSettings() {
        switchNotifications.setChecked(prefs.getNotificationsEnabled());
        switchSound.setChecked(prefs.getSoundEnabled());
    }

    private void setupClickListeners() {
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.setNotificationsEnabled(isChecked);
        });

        switchSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.setSoundEnabled(isChecked);
        });

        btnReset.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Reset Progress")
                .setMessage("Are you sure you want to reset all progress? This cannot be undone.")
                .setPositiveButton("Reset", (dialog, which) -> {
                    SharedPrefManager.getInstance(this).resetProgress();
                    Toast.makeText(this, "Progress reset successfully", Toast.LENGTH_SHORT).show();
                    // Restart app or go to home
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });
    }
}