package com.example.afan_oromo_learning.lessons;

import com.example.afan_oromo_learning.models.Lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.example.afan_oromo_learning.utils.SharedPrefManager;

public class LessonManager {
    private static LessonManager instance;
    
    private LessonManager() {}
    
    public static LessonManager getInstance() {
        if (instance == null) {
            instance = new LessonManager();
        }
        return instance;
    }
    
    public List<Lesson> getAllLessons(SharedPrefManager prefs) {
        List<Lesson> lessons = new ArrayList<>();
        Set<String> completed = prefs.getCompletedLessons();
        
        // Calculate unlocked status based on prerequisites
        boolean lesson1Unlocked = true; // First lesson always unlocked
        boolean lesson2Unlocked = completed.contains("lesson_1");
        boolean lesson3Unlocked = completed.contains("lesson_1");
        boolean lesson4Unlocked = completed.contains("lesson_2");
        boolean lesson5Unlocked = completed.contains("lesson_4");
        boolean lesson6Unlocked = false; // Coming soon - always locked
        
        // Add lessons with consistent data
        lessons.add(createLesson("lesson_1", "The Alphabet", 
            "Learn Afan Oromo letters and pronunciation", 
            "beginner", 15, lesson1Unlocked, 
            completed.contains("lesson_1") ? 100 : 0, "🔤"));
        
        lessons.add(createLesson("lesson_2", "Basic Greetings", 
            "Hello, goodbye, and introductions", 
            "beginner", 20, lesson2Unlocked, 
            completed.contains("lesson_2") ? 100 : 0, "👋"));
        
        lessons.add(createLesson("lesson_3", "Numbers 1-10", 
            "Counting, prices, and basic math", 
            "beginner", 15, lesson3Unlocked, 
            completed.contains("lesson_3") ? 100 : 0, "🔢"));
        
        lessons.add(createLesson("lesson_4", "Common Phrases", 
            "Thank you, please, excuse me", 
            "intermediate", 25, lesson4Unlocked, 
            completed.contains("lesson_4") ? 100 : 0, "💬"));
        
        lessons.add(createLesson("lesson_5", "Family Members", 
            "Relationships and family titles", 
            "intermediate", 30, lesson5Unlocked, 
            completed.contains("lesson_5") ? 100 : 0, "👨‍👩‍👧‍👦"));
        
        // Lesson 6 - Coming soon (special handling)
        Lesson comingSoon = createLesson("lesson_6", "Food & Drinks", 
            "Coming in the next update!", 
            "advanced", 0, lesson6Unlocked, 0, "🍲");
        comingSoon.setComingSoon(true); // You need to add this method to Lesson.java
        lessons.add(comingSoon);
        
        return lessons;
    }
    
    private Lesson createLesson(String id, String title, String description, 
                               String difficulty, int duration, boolean unlocked, 
                               int progress, String icon) {
        Lesson lesson = new Lesson(id, title, description, difficulty, duration, unlocked, progress, icon);
        return lesson;
    }
    
    // Get lesson by ID
    public Lesson getLessonById(String lessonId, SharedPrefManager prefs) {
        List<Lesson> lessons = getAllLessons(prefs);
        for (Lesson lesson : lessons) {
            if (lesson.getId().equals(lessonId)) {
                return lesson;
            }
        }
        return null;
    }
    
    // Get next lesson ID for navigation
    public String getNextLessonId(String currentLessonId) {
        switch (currentLessonId) {
            case "lesson_1": return "lesson_2";
            case "lesson_2": return "lesson_3";
            case "lesson_3": return "lesson_4";
            case "lesson_4": return "lesson_5";
            case "lesson_5": return "lesson_6";
            default: return null;
        }
    }
    
    // Get previous lesson ID
    public String getPreviousLessonId(String currentLessonId) {
        switch (currentLessonId) {
            case "lesson_2": return "lesson_1";
            case "lesson_3": return "lesson_2";
            case "lesson_4": return "lesson_3";
            case "lesson_5": return "lesson_4";
            case "lesson_6": return "lesson_5";
            default: return null;
        }
    }
}