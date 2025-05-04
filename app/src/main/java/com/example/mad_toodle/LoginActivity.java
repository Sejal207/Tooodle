package com.example.mad_toodle;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tooodle.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // you'll create this layout next

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLoginContinue = findViewById(R.id.btnLoginContinue);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);
        TextView tvSignUp = findViewById(R.id.tvSignUp);
        // Button btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn); // If you want Google sign-in on this screen

        btnLoginContinue.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Implement Firebase Auth login here
                Toast.makeText(this, "Login logic goes here", Toast.LENGTH_SHORT).show();
                // On success: startActivity(new Intent(this, HomeActivity.class));
            }
        });

        tvForgotPassword.setOnClickListener(v -> {
            // TODO: Implement forgot password logic
            Toast.makeText(this, "Forgot password clicked", Toast.LENGTH_SHORT).show();
        });

        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, GoogleSignInActivity.class));
        });

        // If you want Google sign-in on this screen, uncomment below
        // btnGoogleSignIn.setOnClickListener(v -> {
        //     startActivity(new Intent(this, GoogleSignInActivity.class));
        // });
    }
}

