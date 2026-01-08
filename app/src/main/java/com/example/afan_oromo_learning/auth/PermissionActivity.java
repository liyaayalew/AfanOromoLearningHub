package com.example.afan_oromo_learning.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.afan_oromo_learning.R;
import com.example.afan_oromo_learning.home.HomeActivity;
import com.example.afan_oromo_learning.utils.PermissionManager;
import java.util.List;

public class PermissionActivity extends AppCompatActivity 
        implements PermissionManager.PermissionCallback {
    
    private Button btnGrantPermissions;
    private Button btnSkip;
    private TextView txtPermissionExplanation;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        
        // Initialize views
        btnGrantPermissions = findViewById(R.id.btn_grant_permissions);
        btnSkip = findViewById(R.id.btn_skip);
        txtPermissionExplanation = findViewById(R.id.txt_permission_explanation);
        
        // Set permission explanations
        setPermissionExplanations();
        
        // Grant Permissions button click
        btnGrantPermissions.setOnClickListener(v -> {
            // Request all permissions
            PermissionManager.checkAndRequestAllPermissions(this, this);
        });
        
        // Skip button click (optional)
        btnSkip.setOnClickListener(v -> {
            // Navigate to HomeActivity even without permissions
            // Some features will be disabled
            navigateToHome();
        });
        
        // If permissions are already granted, skip to home
        if (PermissionManager.areAllPermissionsGranted(this)) {
            navigateToHome();
        }
    }
    
    private void setPermissionExplanations() {
        StringBuilder explanations = new StringBuilder();
        explanations.append("For the best experience, we need:\n\n");
        explanations.append("🎤 Microphone: For voice recording and pronunciation practice\n");
        explanations.append("💾 Storage: To save lessons and download content\n");
        explanations.append("📶 Network: To access online lessons and updates\n\n");
        explanations.append("You can change these later in Settings.");
        
        txtPermissionExplanation.setText(explanations.toString());
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, 
                                           String[] permissions, 
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionResult(requestCode, permissions, grantResults, this);
    }
    
    @Override
    public void onAllPermissionsGranted() {
        // All permissions granted - navigate to home
        navigateToHome();
    }
    
    @Override
    public void onPermissionsGranted(List<String> grantedPermissions) {
        // Some permissions granted
        // You could show a progress update here
    }
    
    @Override
    public void onPermissionsDenied(List<String> deniedPermissions) {
        // Show rationale for denied permissions
        for (String permission : deniedPermissions) {
            String explanation = PermissionManager.getPermissionExplanation(permission);
            
            PermissionManager.showPermissionRationaleDialog(
                this,
                "Permission Needed",
                explanation,
                (dialog, which) -> {
                    // Request again
                    PermissionManager.checkAndRequestAllPermissions(this, this);
                }
            );
        }
    }
    
    @Override
    public void onPermissionsPermanentlyDenied(List<String> permanentlyDeniedPermissions) {
        // User selected "Don't ask again"
        StringBuilder message = new StringBuilder();
        message.append("Some permissions are permanently denied:\n");
        
        for (String permission : permanentlyDeniedPermissions) {
            message.append("- ").append(PermissionManager.getPermissionName(permission)).append("\n");
        }
        
        message.append("\nPlease enable them in App Settings for full functionality.");
        
        PermissionManager.showPermanentlyDeniedDialog(
            this,
            "Permissions Required",
            message.toString()
        );
    }
    
    private void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish(); // Close PermissionActivity
    }
}