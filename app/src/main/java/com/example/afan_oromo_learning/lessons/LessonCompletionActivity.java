package com.example.afan_oromo_learning.lessons;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.home.HomeActivity;
import com.example.afan_oromo_learning.utils.SharedPrefManager;
import com.google.android.material.button.MaterialButton;

public class LessonCompletionActivity extends AppCompatActivity {
    private TextView tvLessonTitle, tvWordsLearned;
    private MaterialButton btnContinue, btnReview;
    private SharedPrefManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_completion);

        prefs = SharedPrefManager.getInstance(this);
        initViews();
        setupData();
        setupClickListeners();
    }

    private void initViews() {
        tvLessonTitle = findViewById(R.id.tvLessonTitle);
        tvWordsLearned = findViewById(R.id.tvWordsLearned);
        btnContinue = findViewById(R.id.btnContinue);
        btnReview = findViewById(R.id.btnReview);
    }

    private void setupData() {
        String lessonTitle = getIntent().getStringExtra("lesson_title");
        int wordsLearned = getIntent().getIntExtra("words_learned", 5);

        tvLessonTitle.setText(lessonTitle + " Completed!");
        tvWordsLearned.setText(wordsLearned + " new words learned");

        // Update streak
        int currentStreak = prefs.getLearningStreak();
        prefs.setLearningStreak(currentStreak + 1);
    }

    private void setupClickListeners() {
        btnContinue.setOnClickListener(v -> {
            startActivity(new Intent(LessonCompletionActivity.this, HomeActivity.class));
            finish();
        });

        btnReview.setOnClickListener(v -> {
            finish();
        });
    }
}