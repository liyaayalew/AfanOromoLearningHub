package com.example.afan_oromo_learning.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.home.HomeActivity;
import com.example.afan_oromo_learning.utils.SharedPrefManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private MaterialButton btnSignup, btnLogin, btnForgetPassword;
    private SharedPrefManager prefs;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = SharedPrefManager.getInstance(this);
        initViews();
        setupClickListeners();

        // Initialize SharedPrefManager
        SharedPrefManager prefs = SharedPrefManager.getInstance(this);
        prefs.setFirstLaunch(false);
        prefs.setLearningLanguage("oromo");
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        btnForgetPassword = findViewById(R.id.btnForgetPassword);
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (validateInput(email, password)) {
                prefs.setUserLoggedIn(true);
                prefs.setUserEmail(email);

                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        });

        btnSignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });

        btnForgetPassword.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(this)
                .setTitle("Forgot Password")
                .setMessage("Enter your email to receive reset instructions")
                .setView(R.layout.dialog_forgot_password)
                .setPositiveButton("Send", (dialog, which) -> {
                    // Mock success
                    Toast.makeText(this, 
                        "Reset instructions sent (simulated)", Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            return false;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            return false;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            return false;
        }

        return true;
    }
}