package com.example.afan_oromo_learning.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionManager {
    // Request codes
    public static final int REQUEST_CODE_MULTIPLE_PERMISSIONS = 1001;
    public static final int REQUEST_CODE_AUDIO_PERMISSION = 1002;
    public static final int REQUEST_CODE_STORAGE_PERMISSION = 1003;
    
    // Permission groups
    public static final String[] AUDIO_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO
    };
    
    public static final String[] STORAGE_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    
    public static final String[] NETWORK_PERMISSIONS = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET
    };
    
    public static final String[] ALL_REQUIRED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET
    };
    
    // Callback interface
    public interface PermissionCallback {
        void onAllPermissionsGranted();
        void onPermissionsGranted(List<String> grantedPermissions);
        void onPermissionsDenied(List<String> deniedPermissions);
        void onPermissionsPermanentlyDenied(List<String> permanentlyDeniedPermissions);
    }
    
    /**
     * Check and request all required permissions with callback
     */
    public static void checkAndRequestAllPermissions(Activity activity, PermissionCallback callback) {
        checkAndRequestPermissions(activity, ALL_REQUIRED_PERMISSIONS, callback);
    }
    
    /**
     * Check and request specific permissions with callback
     */
    public static void checkAndRequestPermissions(Activity activity, String[] permissions, PermissionCallback callback) {
        List<String> permissionsToRequest = new ArrayList<>();
        
        for (String permission : permissions) {
            if (!hasPermission(activity, permission)) {
                permissionsToRequest.add(permission);
            }
        }
        
        if (permissionsToRequest.isEmpty()) {
            // All permissions already granted
            callback.onAllPermissionsGranted();
            callback.onPermissionsGranted(new ArrayList<>(List.of(permissions)));
        } else {
            // Store callback for later use
            PermissionCallbackHolder.getInstance().setCallback(callback);
            PermissionCallbackHolder.getInstance().setRequestedPermissions(permissions);
            
            // Request permissions
            ActivityCompat.requestPermissions(activity,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_CODE_MULTIPLE_PERMISSIONS);
        }
    }
    
    /**
     * Check and request permissions from Fragment
     */
    public static void checkAndRequestPermissions(Fragment fragment, String[] permissions, PermissionCallback callback) {
        List<String> permissionsToRequest = new ArrayList<>();
        
        for (String permission : permissions) {
            if (!hasPermission(fragment.requireContext(), permission)) {
                permissionsToRequest.add(permission);
            }
        }
        
        if (permissionsToRequest.isEmpty()) {
            // All permissions already granted
            callback.onAllPermissionsGranted();
            callback.onPermissionsGranted(new ArrayList<>(List.of(permissions)));
        } else {
            // Store callback for later use
            PermissionCallbackHolder.getInstance().setCallback(callback);
            PermissionCallbackHolder.getInstance().setRequestedPermissions(permissions);
            
            // Request permissions
            fragment.requestPermissions(
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_CODE_MULTIPLE_PERMISSIONS);
        }
    }
    
    /**
     * Handle permission result in Activity or Fragment
     */
    public static void handlePermissionResult(int requestCode, @NonNull String[] permissions, 
                                              @NonNull int[] grantResults, Activity activity) {
        if (requestCode != REQUEST_CODE_MULTIPLE_PERMISSIONS) {
            return;
        }
        
        PermissionCallback callback = PermissionCallbackHolder.getInstance().getCallback();
        if (callback == null) {
            return;
        }
        
        List<String> grantedPermissions = new ArrayList<>();
        List<String> deniedPermissions = new ArrayList<>();
        List<String> permanentlyDeniedPermissions = new ArrayList<>();
        
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                grantedPermissions.add(permission);
            } else {
                deniedPermissions.add(permission);
                
                // Check if permanently denied
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    permanentlyDeniedPermissions.add(permission);
                }
            }
        }
        
        // Call appropriate callback methods
        if (deniedPermissions.isEmpty()) {
            callback.onAllPermissionsGranted();
        }
        
        if (!grantedPermissions.isEmpty()) {
            callback.onPermissionsGranted(grantedPermissions);
        }
        
        if (!deniedPermissions.isEmpty()) {
            callback.onPermissionsDenied(deniedPermissions);
        }
        
        if (!permanentlyDeniedPermissions.isEmpty()) {
            callback.onPermissionsPermanentlyDenied(permanentlyDeniedPermissions);
        }
        
        // Clear stored callback
        PermissionCallbackHolder.getInstance().clear();
    }
    
    /**
     * Check if all required permissions are granted
     */
    public static boolean areAllPermissionsGranted(Context context) {
        for (String permission : ALL_REQUIRED_PERMISSIONS) {
            if (!hasPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Check if specific permission is granted
     */
    public static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
    
    /**
     * Check if audio permission is granted
     */
    public static boolean isAudioPermissionGranted(Context context) {
        return hasPermission(context, Manifest.permission.RECORD_AUDIO);
    }
    
    /**
     * Check if storage permissions are granted
     */
    public static boolean areStoragePermissionsGranted(Context context) {
        for (String permission : STORAGE_PERMISSIONS) {
            if (!hasPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Show rationale dialog for denied permissions
     */
    public static void showPermissionRationaleDialog(Activity activity, String title, 
                                                     String message, DialogInterface.OnClickListener positiveListener) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Grant Permission", positiveListener)
                .setNegativeButton("Cancel", null)
                .setCancelable(false)
                .show();
    }
    
    /**
     * Show dialog for permanently denied permissions (requires app settings)
     */
    public static void showPermanentlyDeniedDialog(Activity activity, String title, 
                                                   String message) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Go to Settings", (dialog, which) -> {
                    openAppSettings(activity);
                })
                .setNegativeButton("Cancel", null)
                .setCancelable(false)
                .show();
    }
    
    /**
     * Open app settings for manual permission granting
     */
    public static void openAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(android.net.Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }
    
    /**
     * Get permission explanation message
     */
    public static String getPermissionExplanation(String permission) {
        Map<String, String> explanations = new HashMap<>();
        explanations.put(Manifest.permission.RECORD_AUDIO, 
                "Audio permission is needed to record voice for pronunciation practice");
        explanations.put(Manifest.permission.READ_EXTERNAL_STORAGE, 
                "Storage permission is needed to access lesson content and audio files");
        explanations.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, 
                "Storage permission is needed to save downloaded lessons and recordings");
        
        return explanations.getOrDefault(permission, "This permission is required for app functionality");
    }
    
    /**
     * Get all required permissions
     */
    public static String[] getRequiredPermissions() {
        return ALL_REQUIRED_PERMISSIONS;
    }
    
    /**
     * Get permission name for display
     */
    public static String getPermissionName(String permission) {
        Map<String, String> permissionNames = new HashMap<>();
        permissionNames.put(Manifest.permission.RECORD_AUDIO, "Microphone");
        permissionNames.put(Manifest.permission.READ_EXTERNAL_STORAGE, "Read Storage");
        permissionNames.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "Write Storage");
        permissionNames.put(Manifest.permission.ACCESS_NETWORK_STATE, "Network State");
        permissionNames.put(Manifest.permission.INTERNET, "Internet");
        
        return permissionNames.getOrDefault(permission, "Unknown Permission");
    }
    
    // Holder class to store callback temporarily
    private static class PermissionCallbackHolder {
        private static PermissionCallbackHolder instance;
        private PermissionCallback callback;
        private String[] requestedPermissions;
        
        private PermissionCallbackHolder() {}
        
        public static PermissionCallbackHolder getInstance() {
            if (instance == null) {
                instance = new PermissionCallbackHolder();
            }
            return instance;
        }
        
        public void setCallback(PermissionCallback callback) {
            this.callback = callback;
        }
        
        public PermissionCallback getCallback() {
            return callback;
        }
        
        public void setRequestedPermissions(String[] permissions) {
            this.requestedPermissions = permissions;
        }
        
        public String[] getRequestedPermissions() {
            return requestedPermissions;
        }
        
        public void clear() {
            this.callback = null;
            this.requestedPermissions = null;
        }
    }
}