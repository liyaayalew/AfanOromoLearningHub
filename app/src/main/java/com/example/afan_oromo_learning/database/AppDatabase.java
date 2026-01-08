package com.example.afan_oromo_learning.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.afan_oromo_learning.models.User;
import com.example.afan_oromo_learning.models.Lesson;
import com.example.afan_oromo_learning.models.Progress;

@Database(
    entities = {User.class, Lesson.class, Progress.class}, 
    version = 1,
    exportSchema = false  // Set to false to avoid schema export issues
)
@TypeConverters({}) // Add converters if you have any
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract LessonDao lessonDao();
    public abstract ProgressDao progressDao();
    
    private static volatile AppDatabase INSTANCE;
    
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "afan_oromo_database")
                            .fallbackToDestructiveMigration()  // This will destroy and recreate on version change
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
