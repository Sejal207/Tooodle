package com.example.mad_toodle;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class RegistrationTest {

    @Rule
    public ActivityScenarioRule<RegisterActivity> activityRule =
            new ActivityScenarioRule<>(RegisterActivity.class);

    @Test
    public void registerUser_shouldShowConfirmationAndNavigateToLogin() {
        // Type email
        onView(withId(R.id.etEmail))
                .perform(typeText("newuser_test@toodle.com"), closeSoftKeyboard());

        // Type password
        onView(withId(R.id.etPassword))
                .perform(typeText("TestPassword123!"), closeSoftKeyboard());

        // Type confirm password
        onView(withId(R.id.etConfirmPassword))
                .perform(typeText("TestPassword123!"), closeSoftKeyboard());

        // Click register button
        onView(withId(R.id.btnRegister)).perform(click());

        // Wait for registration to complete and verify navigation to login screen
        // by checking for the login text view
        onView(withId(R.id.tvLogin))
                .check(matches(isDisplayed()));
    }
}

