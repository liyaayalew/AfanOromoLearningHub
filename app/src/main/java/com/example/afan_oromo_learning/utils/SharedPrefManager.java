package com.example.afan_oromo_learning.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class SharedPrefManager {
    private static final String PREF_NAME = "AfanOromoPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_SELECTED_LANGUAGE = "selectedLanguage";
    private static final String KEY_LEARNER_TYPE = "learnerType";
    private static final String KEY_USER_LEVEL = "userLevel";
    private static final String KEY_LEARNING_GOAL = "learningGoal";
    private static final String KEY_FIRST_LAUNCH = "firstLaunch";
    private static final String KEY_LEARNING_LANGUAGE = "learningLanguage";
    private static final String KEY_ONBOARDING_COMPLETED = "onboardingCompleted";
    private static final String KEY_LEARNING_STREAK = "learningStreak";
    private static final String KEY_WORDS_LEARNED = "wordsLearned";
    private static final String KEY_NOTIFICATIONS_ENABLED = "notificationsEnabled";
    private static final String KEY_SOUND_ENABLED = "soundEnabled";
    
    // Streak tracking keys
    private static final String KEY_LAST_LEARNING_DATE = "last_learning_date";
    private static final String KEY_HAS_COMPLETED_LESSON_TODAY = "has_completed_lesson_today";
    private static final String KEY_CURRENT_STREAK = "current_streak";
    private static final String KEY_MAX_STREAK = "max_streak"; // Track best streak
    
    // Lesson completion tracking
    private static final String KEY_COMPLETED_LESSONS = "completed_lessons";
    private static final String KEY_LESSON_WORDS_COUNTED = "lesson_words_counted";

    private static SharedPrefManager instance;
    private final SharedPreferences sharedPreferences;

    private SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    // ========== EXISTING METHODS ==========
    
    // First launch
    public void setFirstLaunch(boolean isFirstLaunch) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_FIRST_LAUNCH, isFirstLaunch);
        editor.apply();
    }

    public boolean isFirstLaunch() {
        return sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true);
    }

    // Login state
    public void setUserLoggedIn(boolean isLoggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // User data
    public void setUserData(String userId, String userName, String userEmail) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_EMAIL, userEmail);
        editor.apply();
    }

    public void setUserName(String userName) {
        sharedPreferences.edit().putString(KEY_USER_NAME, userName).apply();
    }

    public void setUserEmail(String userEmail) {
        sharedPreferences.edit().putString(KEY_USER_EMAIL, userEmail).apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, "");
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, "");
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, "");
    }

    // Language selection
    public void setLearningLanguage(String language) {
        sharedPreferences.edit().putString(KEY_LEARNING_LANGUAGE, language).apply();
    }

    public String getLearningLanguage() {
        return sharedPreferences.getString(KEY_LEARNING_LANGUAGE, "oromo");
    }

    // Learner type
    public void setLearnerType(String learnerType) {
        sharedPreferences.edit().putString(KEY_LEARNER_TYPE, learnerType).apply();
    }

    public String getLearnerType() {
        return sharedPreferences.getString(KEY_LEARNER_TYPE, "");
    }

    // User level
    public void setUserLevel(String level) {
        sharedPreferences.edit().putString(KEY_USER_LEVEL, level).apply();
    }

    public String getUserLevel() {
        return sharedPreferences.getString(KEY_USER_LEVEL, "Beginner");
    }

    // Learning goal
    public void setUserGoal(String goal) {
        sharedPreferences.edit().putString(KEY_LEARNING_GOAL, goal).apply();
    }

    public String getUserGoal() {
        return sharedPreferences.getString(KEY_LEARNING_GOAL, "");
    }

    // Onboarding
    public void setOnboardingCompleted(boolean completed) {
        sharedPreferences.edit().putBoolean(KEY_ONBOARDING_COMPLETED, completed).apply();
    }

    public boolean isOnboardingCompleted() {
        return sharedPreferences.getBoolean(KEY_ONBOARDING_COMPLETED, false);
    }

    // Settings
    public void setNotificationsEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled).apply();
    }

    public boolean getNotificationsEnabled() {
        return sharedPreferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, true);
    }

    public void setSoundEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_SOUND_ENABLED, enabled).apply();
    }

    public boolean getSoundEnabled() {
        return sharedPreferences.getBoolean(KEY_SOUND_ENABLED, true);
    }

    // ========== UPDATED PROGRESS TRACKING ==========

    /**
     * Checks and updates streak based on daily activity
     * Should be called whenever a lesson is completed
     */
    public void checkAndUpdateStreak() {
        String today = getTodayDate();
        String lastDate = getLastLearningDate();
        
        // Reset daily flag if it's a new day
        if (!today.equals(lastDate)) {
            resetDailyCompletionFlag();
        }
        
        // If user already completed a lesson today, don't update streak again
        if (hasCompletedLessonToday()) {
            return;
        }
        
        // User is completing their FIRST lesson today
        markDailyLessonCompleted();
        
        int currentStreak = getCurrentStreak();
        
        if (lastDate.isEmpty()) {
            // First time learning - start streak at 1
            currentStreak = 1;
        } else if (isYesterday(lastDate, today)) {
            // Consecutive day - increment streak
            currentStreak++;
        } else if (isToday(lastDate, today)) {
            // Already handled above, but just in case
            return;
        } else {
            // Streak broken (gap of more than 1 day) - reset to 1
            currentStreak = 1;
        }
        
        // Update max streak if current is higher
        int maxStreak = getMaxStreak();
        if (currentStreak > maxStreak) {
            setMaxStreak(currentStreak);
        }
        
        // Save updates
        setCurrentStreak(currentStreak);
        setLearningStreak(currentStreak); // For compatibility
        setLastLearningDate(today);
    }
    
    /**
     * Complete a lesson and update progress
     * @param lessonId Unique identifier for the lesson
     * @param newWordsCount Number of new words in this lesson
     */
    public void completeLesson(String lessonId, int newWordsCount) {
        // Mark lesson as completed
        markLessonCompleted(lessonId);
        
        // Add words (only if not already counted)
        addWordsFromLesson(lessonId, newWordsCount);
        
    }
    
    /**
     * Words learned - only count NEW words from lessons
     */
    public void addWordsFromLesson(String lessonId, int newWordsCount) {
        Set<String> countedLessons = getLessonsWordsCounted();
        
        if (!countedLessons.contains(lessonId)) {
            int currentWords = getWordsLearned();
            setWordsLearned(currentWords + newWordsCount);
            
            countedLessons.add(lessonId);
            saveLessonsWordsCounted(countedLessons);
        }
    }
    
    /**
     * Force reset streak (for testing or if user skips many days)
     */
    public void resetStreak() {
        setCurrentStreak(0);
        setLearningStreak(0);
        setLastLearningDate("");
        resetDailyCompletionFlag();
    }
    
    /**
     * Get streak information for display
     */
    public String getStreakInfo() {
        int currentStreak = getCurrentStreak();
        int maxStreak = getMaxStreak();
        return "Current Streak: " + currentStreak + " days | Best: " + maxStreak + " days";
    }
    
    // ========== PRIVATE STREAK MANAGEMENT METHODS ==========
    
    private int getCurrentStreak() {
        return sharedPreferences.getInt(KEY_CURRENT_STREAK, 0);
    }
    
    private void setCurrentStreak(int streak) {
        sharedPreferences.edit().putInt(KEY_CURRENT_STREAK, streak).apply();
    }
    
    private int getMaxStreak() {
        return sharedPreferences.getInt(KEY_MAX_STREAK, 0);
    }
    
    private void setMaxStreak(int streak) {
        sharedPreferences.edit().putInt(KEY_MAX_STREAK, streak).apply();
    }
    
    private boolean hasCompletedLessonToday() {
        return sharedPreferences.getBoolean(KEY_HAS_COMPLETED_LESSON_TODAY, false);
    }
    
    private void markDailyLessonCompleted() {
        sharedPreferences.edit().putBoolean(KEY_HAS_COMPLETED_LESSON_TODAY, true).apply();
    }
    
    private void resetDailyCompletionFlag() {
        sharedPreferences.edit().putBoolean(KEY_HAS_COMPLETED_LESSON_TODAY, false).apply();
    }
    
    private String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
    
    private String getLastLearningDate() {
        return sharedPreferences.getString(KEY_LAST_LEARNING_DATE, "");
    }
    
    private void setLastLearningDate(String date) {
        sharedPreferences.edit().putString(KEY_LAST_LEARNING_DATE, date).apply();
    }
    
    private boolean isYesterday(String date1, String date2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(d1);
            cal2.setTime(d2);
            
            cal1.add(Calendar.DAY_OF_YEAR, 1);
            
            return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                   cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean isToday(String date1, String date2) {
        return date1.equals(date2);
    }
    
    // ========== EXISTING COMPATIBILITY METHODS ==========
    public void setLearningStreak(int streak) {
        sharedPreferences.edit().putInt(KEY_LEARNING_STREAK, streak).apply();
    }

    public int getLearningStreak() {
        return sharedPreferences.getInt(KEY_LEARNING_STREAK, 0);
    }

    public void setWordsLearned(int words) {
        sharedPreferences.edit().putInt(KEY_WORDS_LEARNED, words).apply();
    }

    public int getWordsLearned() {
        return sharedPreferences.getInt(KEY_WORDS_LEARNED, 0);
    }
    
    // ========== LESSON COMPLETION TRACKING ==========
    public void markLessonCompleted(String lessonId) {
        Set<String> completedLessons = getCompletedLessons();
        completedLessons.add(lessonId);
        
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(KEY_COMPLETED_LESSONS, completedLessons).apply();
    }

    public Set<String> getCompletedLessons() {
        return sharedPreferences.getStringSet(KEY_COMPLETED_LESSONS, new HashSet<>());
    }

    public boolean isLessonCompleted(String lessonId) {
        return getCompletedLessons().contains(lessonId);
    }
    
    private Set<String> getLessonsWordsCounted() {
        return sharedPreferences.getStringSet(KEY_LESSON_WORDS_COUNTED, new HashSet<>());
    }
    
    private void saveLessonsWordsCounted(Set<String> lessons) {
        sharedPreferences.edit().putStringSet(KEY_LESSON_WORDS_COUNTED, lessons).apply();
    }

    // ========== CLEAR METHODS ==========
    public void clearUserData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_IS_LOGGED_IN);
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_USER_NAME);
        editor.remove(KEY_USER_EMAIL);
        editor.apply();
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    public void resetProgress() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        
        // Reset progress-related data
        editor.remove(KEY_LEARNING_STREAK);
        editor.remove(KEY_WORDS_LEARNED);
        editor.remove(KEY_COMPLETED_LESSONS);
        editor.remove(KEY_LESSON_WORDS_COUNTED);
        editor.remove(KEY_LAST_LEARNING_DATE);
        editor.remove(KEY_CURRENT_STREAK);
        editor.remove(KEY_MAX_STREAK);
        editor.remove(KEY_HAS_COMPLETED_LESSON_TODAY);
        
        editor.apply();
    }
}