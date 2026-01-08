package com.example.afan_oromo_learning.practice;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.afan_oromo_learning.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class ListeningPracticeActivity extends AppCompatActivity {
    private TextView tvInstruction, tvPhrase;
    private MaterialButton btnPlayAudio, btnOption1, btnOption2, btnOption3, btnOption4;
    private MediaPlayer mediaPlayer;
    private List<String[]> phrases = new ArrayList<>();
    private int currentPhrase = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_practice);

        initViews();
        setupToolbar();
        loadPhrases();
        setupClickListeners();
        displayCurrentPhrase();
    }

    private void initViews() {
        tvInstruction = findViewById(R.id.tvInstruction);
        tvPhrase = findViewById(R.id.tvPhrase);
        btnPlayAudio = findViewById(R.id.btnPlayAudio);
        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("Listening Practice");
    }

    private void loadPhrases() {
        phrases.clear();
        phrases.add(new String[]{"Akkam", "Hello", "Goodbye", "Thank you", "Please"});
        phrases.add(new String[]{"Galatoomaa", "Thank you", "Hello", "Goodbye", "Please"});
        phrases.add(new String[]{"Nagaa", "Goodbye", "Hello", "Thank you", "Please"});
    }

    private void displayCurrentPhrase() {
        if (currentPhrase < phrases.size()) {
            String[] phrase = phrases.get(currentPhrase);
            tvPhrase.setText("Listen to the audio");
            btnOption1.setText(phrase[1]);
            btnOption2.setText(phrase[2]);
            btnOption3.setText(phrase[3]);
            btnOption4.setText(phrase[4]);
        } else {
            tvPhrase.setText("Practice Completed!");
            tvInstruction.setText("Great job! You've completed all exercises.");
            disableButtons();
        }
    }

    private void setupClickListeners() {
        btnPlayAudio.setOnClickListener(v -> {
            playAudio();
        });

        btnOption1.setOnClickListener(v -> checkAnswer(btnOption1.getText().toString()));
        btnOption2.setOnClickListener(v -> checkAnswer(btnOption2.getText().toString()));
        btnOption3.setOnClickListener(v -> checkAnswer(btnOption3.getText().toString()));
        btnOption4.setOnClickListener(v -> checkAnswer(btnOption4.getText().toString()));
    }

    private void playAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // Play different audio based on phrase
        int audioResource = 0;
        switch (currentPhrase) {
            case 0: audioResource = 0; break;
            case 1: audioResource = 0; break;
            case 2: audioResource = 0; break;
        }

        mediaPlayer = MediaPlayer.create(this, audioResource);
        mediaPlayer.start();
    }

    private void checkAnswer(String selectedAnswer) {
        if (currentPhrase < phrases.size()) {
            String[] phrase = phrases.get(currentPhrase);
            String correctAnswer = phrase[1]; // First option is always correct

            if (selectedAnswer.equals(correctAnswer)) {
                tvInstruction.setText("Correct! ✓");
                currentPhrase++;
                new android.os.Handler().postDelayed(() -> {
                    tvInstruction.setText("Listen carefully and choose the correct meaning");
                    displayCurrentPhrase();
                }, 1000);
            } else {
                tvInstruction.setText("Incorrect. Try again!");
            }
        }
    }

    private void disableButtons() {
        btnPlayAudio.setEnabled(false);
        btnOption1.setEnabled(false);
        btnOption2.setEnabled(false);
        btnOption3.setEnabled(false);
        btnOption4.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}