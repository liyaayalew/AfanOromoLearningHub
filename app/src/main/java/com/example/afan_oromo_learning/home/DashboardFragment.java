package com.example.afan_oromo_learning.home;

import android.os.Bundle;
import android.content.Intent; 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.adapters.LessonAdapter;
import com.example.afan_oromo_learning.lessons.LessonActivity;
import com.example.afan_oromo_learning.lessons.LessonManager;
import com.example.afan_oromo_learning.models.Lesson;
import com.example.afan_oromo_learning.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    private TextView tvWelcomeMessage, tvStreak, tvWords, tvDailyQuote, tvBackendStatus;
    private RecyclerView rvLessons;
    private LessonAdapter lessonAdapter;
    private SharedPrefManager prefs;
    private LessonManager lessonManager;
    
    // For backend integration
    private boolean isOnline = true;
    private List<Lesson> lessonList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        
        tvWelcomeMessage = view.findViewById(R.id.tvWelcomeMessage);
        tvStreak = view.findViewById(R.id.tvStreak);
        tvWords = view.findViewById(R.id.tvWordsLearned);
        tvDailyQuote = view.findViewById(R.id.tvDailyQuote);
        tvBackendStatus = view.findViewById(R.id.tvBackendStatus);
        rvLessons = view.findViewById(R.id.rvLessons);
        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        prefs = SharedPrefManager.getInstance(requireContext());
        lessonManager = LessonManager.getInstance(); // Initialize
        
        // Setup RecyclerView with 2-column grid
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        rvLessons.setLayoutManager(layoutManager);
        
        // Initialize adapter with empty list and click listener
        lessonAdapter = new LessonAdapter(requireContext(), new ArrayList<>(), lesson -> {
             if (getActivity() == null) return;

            if (!lesson.isUnlocked()) {
                Toast.makeText(getContext(), 
                    "Complete previous lessons to unlock this", 
                    Toast.LENGTH_SHORT).show();
                return;
            }

            // Launch LessonActivity
            Intent intent = new Intent(getActivity(), LessonActivity.class);
            intent.putExtra("lesson_id", lesson.getId());
            intent.putExtra("lesson_title", lesson.getTitle());
            intent.putExtra("lesson_description", lesson.getDescription());
            startActivity(intent);
            
        });
        
        view.post(new Runnable() {
            @Override
            public void run() {
                ScrollView scrollView = view.findViewById(R.id.scrollView); // Make sure ScrollView has an ID
                if (scrollView != null) {
                    scrollView.scrollTo(0, 0);
                    scrollView.fullScroll(View.FOCUS_UP);
                }
            }
        });

        rvLessons.setAdapter(lessonAdapter);
        
        updateUI();
        loadLessons();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData(); // This will update UI and check unlocked lessons
    }

    private void updateUI() {
        if (getActivity() != null) {
            // Personalized welcome message
            String userName = prefs.getUserName();
            if (!userName.isEmpty()) {
                tvWelcomeMessage.setText("Hello, " + userName + "!");
            } else {
                tvWelcomeMessage.setText("Hello, Learner!");
            }
            
            // Stats
            int streak = prefs.getLearningStreak();
            tvStreak.setText("🔥 " + streak + " Lessons");
            
            int words = prefs.getWordsLearned();
            tvWords.setText(words + " words");
            
            // Daily motivational quote (can be fetched from backend)
            tvDailyQuote.setText(getDailyQuote());
        }
    }

    private String getDailyQuote() {
        // Hardcoded for now, can be fetched from backend
        String[] quotes = {
            "Ready to learn Afan Oromo today?",
            "Every word brings you closer to fluency!",
            "Consistency is the key to language mastery.",
            "Today's effort is tomorrow's fluency."
        };
        return quotes[(int) (System.currentTimeMillis() % quotes.length)];
    }

    private void loadLessons() {
        // Use LessonManager to get all lessons
        lessonList.clear();
        lessonList.addAll(lessonManager.getAllLessons(prefs));
        lessonAdapter.updateLessons(lessonList);
        
        // Update status
        tvBackendStatus.setVisibility(View.VISIBLE);
        tvBackendStatus.setText("Showing " + lessonList.size() + " lessons");
    }

    // Methods for backend integration
    public void updateLessonsFromBackend(List<Lesson> lessons) {
        lessonList.clear();
        lessonList.addAll(lessons);
        lessonAdapter.updateLessons(lessonList);
        
        tvBackendStatus.setVisibility(View.VISIBLE);
        tvBackendStatus.setText("Updated from server: " + lessons.size() + " lessons");
    }
    
    public void setOnlineStatus(boolean online) {
        this.isOnline = online;
        if (!online) {
            tvBackendStatus.setVisibility(View.VISIBLE);
            tvBackendStatus.setText("Offline mode - using cached data");
        } else {
            tvBackendStatus.setVisibility(View.GONE);
        }
    }
    
    public void refreshData() {
        updateUI();
        loadLessons();
    }
}