package com.example.afan_oromo_learning.lessons;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends AppCompatActivity {
    private TextView tvLessonTitle, tvWordOromo, tvWordEnglish, tvPronunciation;
    private Button btnNext, btnComplete;
    private SharedPrefManager prefs;
    private List<String> vocabulary = new ArrayList<>();
    private int currentWordIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        
        prefs = SharedPrefManager.getInstance(this);
        
        // Initialize views
        tvLessonTitle = findViewById(R.id.tvLessonTitle);
        tvWordOromo = findViewById(R.id.tvWordOromo);
        tvWordEnglish = findViewById(R.id.tvWordEnglish);
        tvPronunciation = findViewById(R.id.tvPronunciation);
        btnNext = findViewById(R.id.btnNext);
        btnComplete = findViewById(R.id.btnComplete);
        
        // ========== FIX: GET lessonId FIRST ==========
        String lessonId = getIntent().getStringExtra("lesson_id");
        String lessonTitle = getIntent().getStringExtra("lesson_title");
        // =============================================
        
        // Now you can use lessonId
        String nextButtonText = getNextButtonText(lessonId);
        vocabulary = getVocabularyForLesson(lessonId);
        btnNext.setText(nextButtonText);
        
        // Set lesson title from intent
        if (lessonTitle != null) {
            tvLessonTitle.setText(lessonTitle);
        }
        
        // ========== REMOVE THIS DUPLICATE CODE ==========
        // You're adding vocabulary twice! Once in getVocabularyForLesson()
        // and again here. Remove this block:
        /*
        if ("lesson_1".equals(lessonId)) {
            // Alphabet lesson vocabulary
            vocabulary.add("A,a - First letter - ah");
            vocabulary.add("B,b - Second letter - beh");
            vocabulary.add("C,c - Third letter - cheh");
            vocabulary.add("D,d - Fourth letter - deh");
        } else if ("lesson_2".equals(lessonId)) {
            // Greetings lesson vocabulary
            vocabulary.add("Akkam - Hello - ah-kahm");
            vocabulary.add("Nagaa - Goodbye - nah-gah");
            vocabulary.add("Galatoomaa - Thank you - gah-lah-too-mah");
            vocabulary.add("Akkam jirta? - How are you? - ah-kahm jeer-tah");
        }
        */
        // ================================================
        
        // Display first vocabulary item
        displayVocabulary(currentWordIndex);
        
        btnNext.setOnClickListener(v -> {
            if (currentWordIndex < vocabulary.size() - 1) {
                currentWordIndex++;
                displayVocabulary(currentWordIndex);
            } else {
                Toast.makeText(this, "You've reviewed all items!", Toast.LENGTH_SHORT).show();
            }
        });

        btnComplete.setOnClickListener(v -> {
            // Use the lessonId variable that's already declared
            int wordsInThisLesson = vocabulary.size();
            
            // Use the new method that handles everything correctly
            prefs.completeLesson(lessonId, wordsInThisLesson);
            
            // Go to completion screen
            Intent intent = new Intent(this, LessonCompletionActivity.class);
            intent.putExtra("words_learned", wordsInThisLesson);
            intent.putExtra("lesson_title", lessonTitle);
            intent.putExtra("lesson_id", lessonId);
            startActivity(intent);
            finish();
        });
    }
    
    private String getNextButtonText(String lessonId) {
        if (lessonId == null) return "Next";
        
        switch (lessonId) {
            case "lesson_1": // The Alphabet
                return "Next Letter";
            case "lesson_2": // Basic Greetings
                return "Next Greeting";
            case "lesson_3": // Numbers 1-10
                return "Next Number";
            case "lesson_4": // Common Phrases
                return "Next Phrase";
            case "lesson_5": // Family Members
                return "Next Member";
            default:
                return "Next";
        }
    }
    
    // Also update vocabulary based on lesson type
    private List<String> getVocabularyForLesson(String lessonId) {
        List<String> vocabulary = new ArrayList<>();
        
        if (lessonId == null) {
            vocabulary.add("Sample - Translation - Pronunciation");
            return vocabulary;
        }
        
        switch (lessonId) {
            case "lesson_1": // Alphabet
                vocabulary.add("A,a - First letter - ah");
                vocabulary.add("B,b - Second letter - beh");
                vocabulary.add("C,c - Third letter - cheh");
                vocabulary.add("D,d - Fourth letter - deh");
                vocabulary.add("E,e - Fifth letter - eh");
                break;
            case "lesson_2": // Greetings
                vocabulary.add("Akkam - Hello - ah-kahm");
                vocabulary.add("Nagaa - Goodbye - nah-gah");
                vocabulary.add("Galatoomaa - Thank you - gah-lah-too-mah");
                vocabulary.add("Akkam jirta? - How are you? - ah-kahm jeer-tah");
                vocabulary.add("Nagaatti - See you later - nah-gah-tee");
                break;
            case "lesson_3": // Numbers
                vocabulary.add("Tokko - One - tok-ko");
                vocabulary.add("Lamma - Two - lam-ma");
                vocabulary.add("Sadi - Three - sah-dee");
                vocabulary.add("Afur - Four - ah-foor");
                vocabulary.add("Shan - Five - shan");
                break;
            case "lesson_4": // Common Phrases
                vocabulary.add("Maaloo - Please - mah-loh");
                vocabulary.add("Dhiifama - Excuse me - dee-fah-mah");
                vocabulary.add("Nageenya - Good night - nah-geen-ya");
                vocabulary.add("Baga nagaan dhuftan - Welcome - bah-gah nah-gahn doof-tan");
                vocabulary.add("Hin danda'amu - I don't understand - hin dan-dah-moo");
                break;
            case "lesson_5": // Family
                vocabulary.add("Abbaa - Father - ah-bah");
                vocabulary.add("Haadha - Mother - hah-dah");
                vocabulary.add("Ilma - Son - eel-mah");
                vocabulary.add("Intala - Daughter - in-tah-lah");
                vocabulary.add("Obboleessa - Brother - ob-bo-lees-sah");
                break;
            default:
                vocabulary.add("Sample word - Translation - pro-nun-ci-a-tion");
        }
        
        return vocabulary;
    }

    private void displayVocabulary(int index) {
        if (index >= 0 && index < vocabulary.size()) {
            String[] parts = vocabulary.get(index).split(" - ");
            if (parts.length >= 3) {
                tvWordOromo.setText(parts[0]);
                tvWordEnglish.setText(parts[1]);
                tvPronunciation.setText(parts[2]);
            }
        }
    }
}