package com.example.mad_toodle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileAndProgressActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileandprogress);

        // Initialize BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        // Set listener for navigation item selection
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navHome) {
                Toast.makeText(ProfileAndProgressActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProfileAndProgressActivity.this, HomepageActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.navProgress) {
                Toast.makeText(ProfileAndProgressActivity.this, "Progress Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProfileAndProgressActivity.this, LeaderboardActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.navProfile) {
                Toast.makeText(ProfileAndProgressActivity.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                // Already on profile screen
                return true;
            } else if (itemId == R.id.navSettings) {
                Toast.makeText(ProfileAndProgressActivity.this, "Settings Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProfileAndProgressActivity.this, SettingsActivity.class));
                finish();
                return true;
            }
            return false;
        });

        // Set the selected item to Profile
        bottomNav.setSelectedItemId(R.id.navProfile);
    }
}
