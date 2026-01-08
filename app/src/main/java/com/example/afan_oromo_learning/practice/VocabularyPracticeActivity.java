package com.example.afan_oromo_learning.practice;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.adapters.VocabularyAdapter;
import com.example.afan_oromo_learning.models.Vocabulary;

import java.util.ArrayList;
import java.util.List;

public class VocabularyPracticeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VocabularyAdapter adapter;
    private List<Vocabulary> vocabularyList;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_practice);

        initializeViews();
        setupRecyclerView();
    }

    private void initializeViews() {
        // Just initialize without looking for specific IDs
        recyclerView = findViewById(android.R.id.list);
        if (recyclerView == null) {
            // Create a RecyclerView programmatically if not in layout
            recyclerView = new RecyclerView(this);
            setContentView(recyclerView);
        }
    }

    private void setupRecyclerView() {
        vocabularyList = new ArrayList<>();
        // Initialize with dummy data
        vocabularyList.add(new Vocabulary("1", "Akkam", "Hello", "ah-kahm", false, 0));
        vocabularyList.add(new Vocabulary("2", "Nagaa", "Goodbye", "nah-gah", false, 0));
        vocabularyList.add(new Vocabulary("3", "Galatoomaa", "Thank you", "gah-lah-too-mah", false, 0));
        vocabularyList.add(new Vocabulary("4", "Maaloo", "Please", "mah-loo", false, 0));
        vocabularyList.add(new Vocabulary("5", "Eessaa", "Where", "eh-sah", false, 0));

        adapter = new VocabularyAdapter(this);
        adapter.updateVocabularyList(vocabularyList);
        
        adapter.setOnVocabularyClickListener(new VocabularyAdapter.OnVocabularyClickListener() {
            @Override
            public void onVocabularyClick(Vocabulary vocabulary, int position) {
                Toast.makeText(VocabularyPracticeActivity.this,
                        "Selected: " + vocabulary.getOromoWord(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFavoriteClick(Vocabulary vocabulary, int position) {
                vocabulary.setFavorite(!vocabulary.isFavorite());
                adapter.updateVocabularyFavorite(position, vocabulary.isFavorite());
                Toast.makeText(VocabularyPracticeActivity.this,
                        vocabulary.isFavorite() ? "Added to favorites" : "Removed from favorites",
                        Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void playAudio(int audioResourceId) {
        if (audioResourceId != 0) {
            try {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(this, audioResourceId);
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Audio playback failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Audio not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
