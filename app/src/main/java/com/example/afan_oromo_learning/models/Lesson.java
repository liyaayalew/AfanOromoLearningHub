package com.example.afan_oromo_learning.models;

import java.util.HashSet;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "lessons")
public class Lesson {
    @PrimaryKey
    @NonNull
    private String id;
    
    private String title;
    private String description;
    private String difficulty; // "beginner", "intermediate", "advanced"
    private int duration; // in minutes
    private boolean isUnlocked;
    private int progress; // 0-100
    private String icon; // emoji or icon code
    private String prerequisites; // JSON array of required lesson IDs
    private String category;
    private long lastUpdated; // timestamp for sync
    private boolean completed; // For Room database queries
    private boolean comingSoon = false;

    // Default constructor for Room
    public Lesson() {
    }

    // Constructor for manual creation - Add @Ignore
    @Ignore
    public Lesson(String id, String title, String description, String difficulty, 
                  int duration, boolean isUnlocked, int progress, String icon) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.duration = duration;
        this.isUnlocked = isUnlocked;
        this.progress = progress;
        this.icon = icon;
        this.completed = (progress == 100);
    }

    // Add this constructor for backward compatibility
    @Ignore
    public Lesson(String id, String title, String description, int level, boolean isUnlocked, int progress) {
        this.id = id;
        this.title = title;
        this.description = description;
        // Convert level to difficulty
        if (level == 1) this.difficulty = "beginner";
        else if (level == 2) this.difficulty = "intermediate";
        else this.difficulty = "advanced";
        this.duration = 15; // default
        this.isUnlocked = isUnlocked;
        this.progress = progress;
        this.icon = "📚"; // default icon
        this.completed = (progress == 100);
    }

    // Add isLocked() method for compatibility
    public boolean isLocked() {
        return !isUnlocked;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        this.completed = (progress == 100);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        if (completed) {
            this.progress = 100;
        }
    }

    // Helper methods for display
    public String getDifficultyStars() {
        if (difficulty == null) return "⭐☆☆";
        switch (difficulty.toLowerCase()) {
            case "beginner": return "⭐☆☆";
            case "intermediate": return "⭐⭐☆";
            case "advanced": return "⭐⭐⭐";
            default: return "⭐☆☆";
        }
    }

    public String getTimeEstimate() {
        return duration + " min";
    }

    // For backward compatibility - get level (mapped from difficulty)
    public int getLevel() {
        if (difficulty == null) return 1;
        switch (difficulty.toLowerCase()) {
            case "beginner": return 1;
            case "intermediate": return 2;
            case "advanced": return 3;
            default: return 1;
        }
    }

    // Method to to track prerequisites
    public boolean canUnlock(Set<String> completedLessonIds) {
        if (prerequisites == null || prerequisites.isEmpty()) {
            return true;
        }
        // Simple check - for now assume lesson 2 requires lesson 1, etc.
        if (id.equals("lesson_2") && completedLessonIds.contains("lesson_1")) {
            return true;
        }
        if (id.equals("lesson_3") && completedLessonIds.contains("lesson_1")) {
            return true;
        }
        if (id.equals("lesson_4") && completedLessonIds.contains("lesson_2")) {
            return true;
        }
        if (id.equals("lesson_5") && completedLessonIds.contains("lesson_4")) {
            return true;
        }
        return false;
    }

    public boolean isComingSoon() {
        return comingSoon;
    }

    public void setComingSoon(boolean comingSoon) {
        this.comingSoon = comingSoon;
        if (comingSoon) {
            this.isUnlocked = false; // Coming soon lessons are always locked
            this.progress = 0;
        }
    }
}