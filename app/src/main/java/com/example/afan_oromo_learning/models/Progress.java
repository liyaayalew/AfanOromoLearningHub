package com.example.afan_oromo_learning.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "progress",
    foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {@Index("userId")}
)
public class Progress {
    @PrimaryKey
    @NonNull
    private String id;
    @NonNull
    private String userId;
    @NonNull
    private String lessonId;
    private int score;
    private long timestamp;
    private boolean completed;
    
    // Room requires a no-arg constructor
    public Progress() {
        this.id = "";
        this.userId = "";
        this.lessonId = "";
        this.completed = false;
    }
    
    @Ignore
    public Progress(@NonNull String id, @NonNull String userId, @NonNull String lessonId, int score, long timestamp) {
        this.id = id;
        this.userId = userId;
        this.lessonId = lessonId;
        this.score = score;
        this.timestamp = timestamp;
        this.completed = (score >= 70); // Auto-complete if score is good
    }
    
    @Ignore
    public Progress(@NonNull String id, @NonNull String userId, @NonNull String lessonId, int score, long timestamp, boolean completed) {
        this.id = id;
        this.userId = userId;
        this.lessonId = lessonId;
        this.score = score;
        this.timestamp = timestamp;
        this.completed = completed;
    }
    
    // Getters and Setters
    @NonNull
    public String getId() { return id; }
    public void setId(@NonNull String id) { this.id = id; }
    
    @NonNull
    public String getUserId() { return userId; }
    public void setUserId(@NonNull String userId) { this.userId = userId; }
    
    @NonNull
    public String getLessonId() { return lessonId; }
    public void setLessonId(@NonNull String lessonId) { this.lessonId = lessonId; }
    
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
