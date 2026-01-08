package com.example.afan_oromo_learning.lessons;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.adapters.VocabularyAdapter;
import com.example.afan_oromo_learning.models.Vocabulary;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;
import java.util.List;

public class VocabularyActivity extends AppCompatActivity {
    private RecyclerView recyclerViewVocabulary;
    private VocabularyAdapter vocabularyAdapter;
    private List<Vocabulary> vocabularyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        initViews();
        setupToolbar();
        loadVocabulary();
        setupRecyclerView();
    }

    private void initViews() {
        recyclerViewVocabulary = findViewById(R.id.recyclerViewVocabulary);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setTitle("Vocabulary");
    }

    private void loadVocabulary() {
        vocabularyList.clear();

        vocabularyList.add(new Vocabulary(
                "1",                // id
                "Akkam",           // oromoWord
                "Hello",           // englishTranslation
                "ah-kahm",         // pronunciation
                false,             // favorite
                0  // audioResourceId
        ));

        vocabularyList.add(new Vocabulary(
                "2",
                "Nagaa",
                "Goodbye",
                "nah-gah",
                false,
                0
        ));

        vocabularyList.add(new Vocabulary(
                "3",
                "Galatoomaa",
                "Thank you",
                "gah-lah-too-mah",
                false,
                0
        ));

        vocabularyList.add(new Vocabulary(
                "4",
                "Maaloo",
                "Please",
                "mah-loo",
                false,
                0
        ));

        vocabularyList.add(new Vocabulary(
                "5",
                "Dhiifama",
                "Excuse me",
                "dee-fah-mah",
                false,
                0
        ));

        vocabularyList.add(new Vocabulary(
                "6",
                "Eessaa?",
                "Where?",
                "eh-sah",
                false,
                0
        ));

        vocabularyList.add(new Vocabulary(
                "7",
                "Maaliif?",
                "Why?",
                "mah-leef",
                false,
                0
        ));

        vocabularyList.add(new Vocabulary(
                "8",
                "Yoom?",
                "When?",
                "yohm",
                false,
                0
        ));
    }

    private void setupRecyclerView() {
        vocabularyAdapter = new VocabularyAdapter(this, vocabularyList);
        recyclerViewVocabulary.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewVocabulary.setAdapter(vocabularyAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}