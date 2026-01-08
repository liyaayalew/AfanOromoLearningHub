package com.example.afan_oromo_learning.lessons;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.adapters.LessonAdapter;
import com.example.afan_oromo_learning.lessons.LessonManager;
import com.example.afan_oromo_learning.models.Lesson;
import com.example.afan_oromo_learning.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class LessonListActivity extends AppCompatActivity implements LessonAdapter.OnLessonClickListener {
    private RecyclerView recyclerView;
    private LessonAdapter lessonAdapter;
    private List<Lesson> lessonList;
    private ProgressBar progressBar;
    private TextView tvProgress;
    private LessonManager lessonManager;
    private SharedPrefManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);

        prefs = SharedPrefManager.getInstance(this);
        lessonManager = LessonManager.getInstance();

        initializeViews();
        setupRecyclerView();
        loadLessons();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        loadLessons(); // Just reload lessons
    }
    
    private void initializeViews() {
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tvProgress);
        
        // Create RecyclerView programmatically if not in layout
        recyclerView = findViewById(android.R.id.list);
        if (recyclerView == null) {
            recyclerView = new RecyclerView(this);
            setContentView(recyclerView);
        }
    }

    private void setupRecyclerView() {
        lessonList = new ArrayList<>();
        lessonAdapter = new LessonAdapter(this, lessonList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(lessonAdapter);
    }

    private void loadLessons() {
        lessonList.clear();
        lessonList.addAll(lessonManager.getAllLessons(prefs));
        lessonAdapter.updateLessons(lessonList);
        updateProgress();
    }

    private void updateProgress() {
        int completed = 0;
        for (Lesson lesson : lessonList) {
            if (lesson.isCompleted()) {
                completed++;
            }
        }
        int progress = (int) ((completed / (float) lessonList.size()) * 100);
        if (progressBar != null) {
            progressBar.setProgress(progress);
        }
        if (tvProgress != null) {
            tvProgress.setText(String.format("%d%% Complete", progress));
        }
    }

    @Override
    public void onLessonClick(Lesson lesson) {
        if (!lesson.isUnlocked()) {
            onLockedLessonClick(lesson);
        } else {
            Intent intent = new Intent(this, LessonActivity.class);
            intent.putExtra("lesson_id", lesson.getId());
            intent.putExtra("lesson_title", lesson.getTitle());
            startActivity(intent);
        }
    }

    public void onLockedLessonClick(Lesson lesson) {
        // Show dialog or message that lesson is locked
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Lesson Locked")
                .setMessage("Complete previous lessons to unlock this lesson.")
                .setPositiveButton("OK", null)
                .show();
    }
}
