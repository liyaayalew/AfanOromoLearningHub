package com.example.afan_oromo_learning;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.example.afan_oromo_learning.database.AppDatabase;
import com.example.afan_oromo_learning.utils.SharedPrefManager;

public class AppController extends Application {
    private static AppController instance;
    
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // Enable MultiDex for apps with many methods
        MultiDex.install(this);
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        
        // Initialize database
        AppDatabase.getInstance(this);
        
        // Initialize SharedPrefManager with context
        SharedPrefManager.getInstance(this);
    }
    
    public static Context getAppContext() {
        if (instance == null) {
            // This can happen if called before onCreate()
            throw new IllegalStateException("AppController not initialized yet");
        }
        return instance.getApplicationContext();
    }
    
    public static AppController getInstance() {
        return instance;
    }
}