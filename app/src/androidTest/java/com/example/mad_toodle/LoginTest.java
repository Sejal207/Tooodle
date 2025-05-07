package com.example.mad_toodle;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void userCanLoginSuccessfully() {
        // Fill in email
        onView(withId(R.id.etEmail))
                .perform(typeText("newuser@toodle.com"), ViewActions.closeSoftKeyboard());

        // Fill in password
        onView(withId(R.id.etPassword))
                .perform(typeText("Secure@1234"), ViewActions.closeSoftKeyboard());

        // Click login
        onView(withId(R.id.btnLogin)).perform(click());
    }
}

