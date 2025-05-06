package com.example.mad_toodle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> logout());
    }

    private void logout() {
        if (currentUser != null) {
            // Delete user data from database
            mDatabase.child("users").child(currentUser.getUid()).removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User data deleted successfully");
                    } else {
                        Log.e(TAG, "Failed to delete user data", task.getException());
                    }
                    
                    // Sign out from Firebase Auth
                    mAuth.signOut();
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                });
        } else {
            // If no user is logged in, just sign out
            mAuth.signOut();
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}