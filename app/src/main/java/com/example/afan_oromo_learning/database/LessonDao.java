package com.example.afan_oromo_learning.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.afan_oromo_learning.models.Lesson;

import java.util.List;

@Dao
public interface LessonDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Lesson lesson);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Lesson> lessons);
    
    @Update
    void update(Lesson lesson);
    
    @Query("DELETE FROM lessons")
    void deleteAll();
    
    @Query("SELECT * FROM lessons ORDER BY id")
    LiveData<List<Lesson>> getAllLessons();
    
    @Query("SELECT * FROM lessons WHERE difficulty = :difficulty ORDER BY id")
    List<Lesson> getLessonsByDifficulty(String difficulty);
    
    @Query("SELECT * FROM lessons WHERE completed = 1 ORDER BY id")
    List<Lesson> getCompletedLessons();
    
    @Query("SELECT * FROM lessons WHERE progress > 0 AND progress < 100 ORDER BY id")
    List<Lesson> getInProgressLessons();
    
    @Query("SELECT * FROM lessons WHERE isUnlocked = 1 ORDER BY id")
    List<Lesson> getUnlockedLessons();
    
    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    Lesson getLessonById(String lessonId);
    
    @Query("UPDATE lessons SET completed = :completed WHERE id = :lessonId")
    void updateCompleted(String lessonId, boolean completed);
    
    @Query("UPDATE lessons SET progress = :progress WHERE id = :lessonId")
    void updateProgress(String lessonId, int progress);
    
    @Query("UPDATE lessons SET isUnlocked = :unlocked WHERE id = :lessonId")
    void updateUnlocked(String lessonId, boolean unlocked);
}