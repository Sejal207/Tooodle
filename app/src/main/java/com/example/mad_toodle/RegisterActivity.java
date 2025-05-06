package com.example.mad_toodle;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mad_toodle.models.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText etEmail, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            // Initialize Firebase if not already initialized
            if (FirebaseApp.getApps(this).isEmpty()) {
                FirebaseApp.initializeApp(this);
            }
            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            
            // Check if Firebase is properly initialized
            if (mAuth == null) {
                Log.e(TAG, "Firebase Auth is null after initialization");
                Toast.makeText(this, "Authentication service not available. Please try again later.", 
                    Toast.LENGTH_LONG).show();
                finish();
                return;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Firebase", e);
            Toast.makeText(this, "Error initializing Firebase: " + e.getMessage(), 
                Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> registerUser());

        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email address");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            return;
        }

        // Show loading state
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setEnabled(false);
        btnRegister.setText("Registering...");

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String userId = firebaseUser.getUid();
                        
                        // Create user object
                        User user = new User(userId, email, email.split("@")[0]); // Using email prefix as name
                        
                        // Save user data to database
                        mDatabase.child("users").child(userId).setValue(user)
                            .addOnCompleteListener(dbTask -> {
                                // Reset button state
                                btnRegister.setEnabled(true);
                                btnRegister.setText("Register");

                                if (dbTask.isSuccessful()) {
                                    Log.d(TAG, "User data saved successfully");
                                    Toast.makeText(RegisterActivity.this, "Registration successful!",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                } else {
                                    Log.e(TAG, "Failed to save user data", dbTask.getException());
                                    Toast.makeText(RegisterActivity.this, 
                                            "Registration successful but failed to save user data. Please try logging in.",
                                            Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }
                            });
                    } else {
                        // Reset button state
                        btnRegister.setEnabled(true);
                        btnRegister.setText("Register");

                        String errorCode = "unknown";
                        if (task.getException() instanceof FirebaseAuthException) {
                            errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        }
                        Log.e(TAG, "Registration failed with error code: " + errorCode, task.getException());
                        
                        String errorMessage;
                        switch (errorCode) {
                            case "ERROR_EMAIL_ALREADY_IN_USE":
                                errorMessage = "This email is already registered. Please login instead.";
                                break;
                            case "ERROR_INVALID_EMAIL":
                                errorMessage = "Invalid email address format.";
                                break;
                            case "ERROR_WEAK_PASSWORD":
                                errorMessage = "Password is too weak. Please use a stronger password.";
                                break;
                            case "ERROR_OPERATION_NOT_ALLOWED":
                                errorMessage = "Email/password accounts are not enabled. Please contact support.";
                                break;
                            default:
                                errorMessage = "Registration failed: " + 
                                    (task.getException() != null ? task.getException().getMessage() : "Unknown error");
                        }
                        Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }
} 