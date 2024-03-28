package comp3350;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Service;
import android.content.Context;
import android.view.KeyEvent;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.srsys.R;
import comp3350.srsys.application.Main;
import comp3350.srsys.application.Services;
import comp3350.srsys.presentation.HomePage;
import comp3350.utils.TestUtils;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileTest {
    // Sleep time used as a delay so that the app can catch up to the test
    private final int sleepTime = 500;
    @Rule
    public ActivityScenarioRule<HomePage> activityScenarioRule = new ActivityScenarioRule<>(HomePage.class);
    private TestUtils testUtils;

    @Before
    public void setupTestUtils(){
        testUtils = new TestUtils();
    }

    @Test
    public void testViewProfile() {
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.profile_image)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditProfile() {
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.profile_image)).check(matches(isDisplayed()));
        onView(withId(R.id.input_full_name)).perform(click());
        onView(withId(R.id.input_full_name)).perform(replaceText(""), closeSoftKeyboard());
        onView(withId(R.id.input_username)).perform(click());
        onView(withId(R.id.input_username)).perform(replaceText(""), closeSoftKeyboard());
        onView(withId(R.id.email)).perform(click());
        onView(withId(R.id.email)).perform(replaceText(""), closeSoftKeyboard());
        onView(withId(R.id.phone)).perform(click());
        onView(withId(R.id.phone)).perform(replaceText(""), closeSoftKeyboard());
        onView(withId(R.id.input_full_name)).perform(typeText("John Doe"), closeSoftKeyboard());
        onView(withId(R.id.input_username)).perform(typeText("jdoe"), closeSoftKeyboard());
        onView(withId(R.id.email)).perform(typeText("jdoe@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.phone)).perform(typeText("204-999-9999"), closeSoftKeyboard());
        onView(withId(R.id.update_button)).perform(click());
        onView(withId(R.id.input_full_name)).check(matches(withText("John Doe")));
        onView(withId(R.id.input_username)).check(matches(withText("jdoe")));
        onView(withId(R.id.email)).check(matches(withText("jdoe@gmail.com")));
        onView(withId(R.id.phone)).check(matches(withText("204-999-9999")));
        onView(withId(R.id.classesButton)).perform(click());
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.input_full_name)).check(matches(withText("John Doe")));
        onView(withId(R.id.input_username)).check(matches(withText("jdoe")));
        onView(withId(R.id.email)).check(matches(withText("jdoe@gmail.com")));
        onView(withId(R.id.phone)).check(matches(withText("204-999-9999")));
    }

}
