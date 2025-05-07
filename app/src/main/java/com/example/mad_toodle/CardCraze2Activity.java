package com.example.mad_toodle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CardCraze2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardcraze2_wrong);

        // Find the continue button
        Button continueButton = findViewById(R.id.continue_button);
        
        // Set click listener
        continueButton.setOnClickListener(v -> {
            // Navigate to dino ending screen
            Intent intent = new Intent(this, DinoEndingActivity.class);
            startActivity(intent);
            finish(); // Close this activity
        });
    }
} 