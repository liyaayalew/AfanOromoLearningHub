package com.example.afan_oromo_learning.practice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.lessons.VocabularyActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class PracticeHubActivity extends AppCompatActivity {
    private MaterialCardView cardSpeaking, cardListening, cardVocabulary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_hub);

        initViews();
        setupToolbar();
        setupClickListeners();
    }

    private void initViews() {
        cardSpeaking = findViewById(R.id.cardSpeaking);
        cardListening = findViewById(R.id.cardListening);
        cardVocabulary = findViewById(R.id.cardVocabulary);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("Practice Hub");
    }

    private void setupClickListeners() {
        cardSpeaking.setOnClickListener(v -> {
            startActivity(new Intent(PracticeHubActivity.this, SpeakingPracticeActivity.class));
        });

        cardListening.setOnClickListener(v -> {
            startActivity(new Intent(PracticeHubActivity.this, ListeningPracticeActivity.class));
        });

        cardVocabulary.setOnClickListener(v -> {
            startActivity(new Intent(PracticeHubActivity.this, VocabularyActivity.class));
        });
    }
}