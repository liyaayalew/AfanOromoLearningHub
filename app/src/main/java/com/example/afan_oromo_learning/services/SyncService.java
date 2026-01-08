package com.example.afan_oromo_learning.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.afan_oromo_learning.database.AppDatabase;
import com.example.afan_oromo_learning.utils.NetworkUtils;

public class SyncService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (NetworkUtils.isNetworkAvailable(this)) {
            syncData();
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
        return START_STICKY;
    }

    private void syncData() {
        // Sync progress with server (simulated)
        Toast.makeText(this, "Syncing your progress...", Toast.LENGTH_SHORT).show();

        // In a real app, you would sync with Firebase or your backend
        // For now, just show a toast
        new android.os.Handler().postDelayed(() -> {
            Toast.makeText(this, "Progress synced successfully", Toast.LENGTH_SHORT).show();
            stopSelf();
        }, 2000);
    }
}