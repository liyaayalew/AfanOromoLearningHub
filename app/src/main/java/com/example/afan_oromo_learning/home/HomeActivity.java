package com.example.afan_oromo_learning.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.lessons.LessonListActivity;
import com.example.afan_oromo_learning.profile.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new DashboardFragment());
        }
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        
        // Used if-else instead of switch to avoid constant expression error
        if (itemId == R.id.nav_home) {
            loadFragment(new DashboardFragment());
            return true;
        } else if (itemId == R.id.nav_learn) {
            startActivity(new Intent(HomeActivity.this, LessonListActivity.class));
            return true;
        } else if (itemId == R.id.nav_profile) {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            return true;
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
