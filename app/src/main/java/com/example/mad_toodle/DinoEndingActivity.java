package com.example.mad_toodle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DinoEndingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dino_ending);

        // Find the main menu button
        Button mainMenuButton = findViewById(R.id.main_menu);
        
        // Set click listener
        mainMenuButton.setOnClickListener(v -> {
            // Navigate back to homepage
            Intent intent = new Intent(this, HomepageActivity.class);
            startActivity(intent);
            finish(); // Close this activity
        });
    }
} 