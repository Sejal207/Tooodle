package com.example.mad_toodle;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LogoutTest {

    private ActivityScenario<HomeActivity> activityScenario;

    @Before
    public void setUp() throws InterruptedException {
        Intents.init();

        // Sign in synchronously
        CountDownLatch authLatch = new CountDownLatch(1);
        final boolean[] signInSuccess = {false};
        FirebaseAuth.getInstance().signInWithEmailAndPassword("koogoyal@gmail.com", "khushi12")
            .addOnCompleteListener(task -> {
                signInSuccess[0] = task.isSuccessful();
                authLatch.countDown();
            });

        // Wait for authentication to complete (with timeout)
        if (!authLatch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Authentication timeout");
        }
        if (!signInSuccess[0]) {
            throw new RuntimeException("Authentication failed");
        }

        // Now launch the activity on the main thread
        activityScenario = ActivityScenario.launch(HomeActivity.class);
    }

    @Test
    public void testLogoutFlow() throws InterruptedException {
        // Verify we're on the home screen
        onView(withId(R.id.btnLogout)).check(matches(isDisplayed()));

        // Click logout button
        onView(withId(R.id.btnLogout)).perform(click());

        // Wait for async logout to complete
        Thread.sleep(2000);

        // Now check for login screen
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()));
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.etPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.btnLogin)).check(matches(withText("Login")));
    }

    @After
    public void tearDown() {
        // Clean up
        if (activityScenario != null) {
            activityScenario.close();
        }
        Intents.release();
        
        // Sign out from Firebase
        FirebaseAuth.getInstance().signOut();
    }
}
