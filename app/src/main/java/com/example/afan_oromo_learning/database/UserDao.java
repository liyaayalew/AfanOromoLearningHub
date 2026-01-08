package com.example.afan_oromo_learning.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.afan_oromo_learning.models.User;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(String userId);

    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByEmail(String email);

    @Query("DELETE FROM users")
    void deleteAll();

    @Query("UPDATE users SET streak = :streak WHERE id = :userId")
    void updateStreak(String userId, int streak);

    @Query("UPDATE users SET wordsLearned = :words WHERE id = :userId")
    void addWordsLearned(String userId, int words);

    @Query("SELECT streak FROM users WHERE id = :userId")
    int getStreak(String userId);

    @Query("SELECT wordsLearned FROM users WHERE id = :userId")
    int getWordsLearned(String userId);
}
