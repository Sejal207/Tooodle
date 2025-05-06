package com.example.mad_toodle;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class TooodleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
} 