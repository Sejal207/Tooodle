package com.example.mad_toodle;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tooodle.R;

public class ContinueActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);

        findViewById(R.id.btnContinue).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}