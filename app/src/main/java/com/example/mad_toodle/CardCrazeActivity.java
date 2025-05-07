package com.example.mad_toodle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class CardCrazeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardcraze);

        // Find all image views
        ImageView fishImage = findViewById(R.id.imageView6);
        ImageView otherImage1 = findViewById(R.id.imageView8);
        ImageView otherImage2 = findViewById(R.id.imageView7);
        ImageView otherImage3 = findViewById(R.id.imageView9);

        // Set click listeners
        fishImage.setOnClickListener(v -> {
            startActivity(new Intent(CardCrazeActivity.this, CardCraze1Activity.class));
            finish();
        });

        // For all other images, navigate to CardCraze2
        otherImage1.setOnClickListener(v -> navigateToCardCraze2());
        otherImage2.setOnClickListener(v -> navigateToCardCraze2());
        otherImage3.setOnClickListener(v -> navigateToCardCraze2());
    }

    private void navigateToCardCraze2() {
        startActivity(new Intent(CardCrazeActivity.this, CardCraze2Activity.class));
        finish();
    }
} 