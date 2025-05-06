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

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            // Initialize Firebase if not already initialized
            if (FirebaseApp.getApps(this).isEmpty()) {
                FirebaseApp.initializeApp(this);
            }
            mAuth = FirebaseAuth.getInstance();
            
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
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);
        TextView tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(v -> loginUser());

        tvForgotPassword.setOnClickListener(v -> resetPassword());

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

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

        // Show loading state
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setEnabled(false);
        btnLogin.setText("Logging in...");

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    // Reset button state
                    btnLogin.setEnabled(true);
                    btnLogin.setText("Login");

                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d(TAG, "Login successful for user: " + user.getUid());
                        Toast.makeText(LoginActivity.this, "Welcome back!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        String errorCode = "unknown";
                        if (task.getException() instanceof FirebaseAuthException) {
                            errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        }
                        Log.e(TAG, "Login failed with error code: " + errorCode, task.getException());
                        
                        String errorMessage;
                        switch (errorCode) {
                            case "ERROR_INVALID_EMAIL":
                                errorMessage = "Invalid email address format.";
                                break;
                            case "ERROR_USER_NOT_FOUND":
                                errorMessage = "No account found with this email. Please register first.";
                                break;
                            case "ERROR_WRONG_PASSWORD":
                                errorMessage = "Incorrect password. Please try again.";
                                break;
                            case "ERROR_USER_DISABLED":
                                errorMessage = "This account has been disabled. Please contact support.";
                                break;
                            case "ERROR_OPERATION_NOT_ALLOWED":
                                errorMessage = "Email/password accounts are not enabled. Please contact support.";
                                break;
                            default:
                                errorMessage = "Login failed: " + 
                                    (task.getException() != null ? task.getException().getMessage() : "Unknown error");
                        }
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void resetPassword() {
        String email = etEmail.getText().toString().trim();
        
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Please enter your email first");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email address");
            return;
        }

        // Show loading state
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvForgotPassword.setEnabled(false);
        tvForgotPassword.setText("Sending...");

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    // Reset button state
                    tvForgotPassword.setEnabled(true);
                    tvForgotPassword.setText("Forgot Password?");

                    if (task.isSuccessful()) {
                        Log.d(TAG, "Password reset email sent to: " + email);
                        Toast.makeText(LoginActivity.this, 
                                "Password reset email sent. Please check your email.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        String errorCode = "unknown";
                        if (task.getException() instanceof FirebaseAuthException) {
                            errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        }
                        Log.e(TAG, "Password reset failed with error code: " + errorCode, task.getException());
                        
                        String errorMessage;
                        switch (errorCode) {
                            case "ERROR_INVALID_EMAIL":
                                errorMessage = "Invalid email address format.";
                                break;
                            case "ERROR_USER_NOT_FOUND":
                                errorMessage = "No account found with this email.";
                                break;
                            case "ERROR_OPERATION_NOT_ALLOWED":
                                errorMessage = "Password reset is not enabled. Please contact support.";
                                break;
                            default:
                                errorMessage = "Failed to send reset email: " + 
                                    (task.getException() != null ? task.getException().getMessage() : "Unknown error");
                        }
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }
}

