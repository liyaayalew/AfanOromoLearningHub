package com.example.afan_oromo_learning.database;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.afan_oromo_learning.models.Progress;

import java.util.List;

@Dao
public interface ProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Progress progress);

    @Update
    void update(Progress progress);

    @Query("SELECT * FROM progress WHERE userId = :userId ORDER BY timestamp DESC")
    List<Progress> getUserProgress(@NonNull String userId);

    @Query("SELECT * FROM progress WHERE userId = :userId AND lessonId = :lessonId")
    Progress getProgressForLesson(@NonNull String userId, @NonNull String lessonId);

    @Query("SELECT * FROM progress WHERE userId = :userId AND completed = 1 ORDER BY timestamp DESC")
    List<Progress> getCompletedLessons(@NonNull String userId);

    @Query("UPDATE progress SET score = :score, completed = :completed WHERE id = :progressId")
    void updateScoreAndCompletion(int progressId, int score, boolean completed);

    @Query("SELECT COUNT(*) FROM progress WHERE userId = :userId AND completed = 1")
    int getCompletedLessonCount(@NonNull String userId);

    @Query("SELECT AVG(score) FROM progress WHERE userId = :userId AND completed = 1")
    double getAverageScore(@NonNull String userId);

    @Query("DELETE FROM progress WHERE userId = :userId")
    void deleteUserProgress(@NonNull String userId);

    @Query("DELETE FROM progress")
    void deleteAll();
}
