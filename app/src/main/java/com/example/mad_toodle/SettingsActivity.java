package com.example.mad_toodle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        Button btnLogout = findViewById(R.id.btnLogout);
        
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(SettingsActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsActivity.this, GettingStartedActivity.class));
            finishAffinity(); // This will clear all activities in the stack
        });
    }
} 