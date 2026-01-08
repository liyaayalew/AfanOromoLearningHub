package com.example.afan_oromo_learning.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String email;
    private String password;
    private String level;
    private String goal;
    private int streak;
    private int wordsLearned;
    
    // Room requires a no-arg constructor
    public User() {
        this.id = "";
        this.streak = 0;
        this.wordsLearned = 0;
    }
    
    @Ignore
    public User(@NonNull String id, String name, String email, String password, String level, String goal) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.level = level;
        this.goal = goal;
        this.streak = 0;
        this.wordsLearned = 0;
    }
    
    @Ignore
    public User(@NonNull String id, String name, String email, String password, String level, String goal, int streak, int wordsLearned) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.level = level;
        this.goal = goal;
        this.streak = streak;
        this.wordsLearned = wordsLearned;
    }
    
    // Getters and Setters
    @NonNull
    public String getId() { return id; }
    public void setId(@NonNull String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    
    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }
    
    public int getStreak() { return streak; }
    public void setStreak(int streak) { this.streak = streak; }
    
    public int getWordsLearned() { return wordsLearned; }
    public void setWordsLearned(int wordsLearned) { this.wordsLearned = wordsLearned; }
}
