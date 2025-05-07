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
public class PasswordResetTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void passwordReset_shouldSendEmail() {
        // Click on Forgot Password
        onView(withId(R.id.tvForgotPassword)).perform(click());

        // Enter email for reset
        onView(withId(R.id.etEmail))
                .perform(typeText("testuser@toodle.com"), closeSoftKeyboard());

        // Click reset button
        onView(withId(R.id.btnLogin)).perform(click());

        // Confirm reset message
        onView(withText("Reset link sent to your email"))
                .check(matches(isDisplayed()));
    }
}
