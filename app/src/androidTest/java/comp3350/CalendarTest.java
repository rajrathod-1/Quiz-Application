package comp3350;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

import android.content.Context;
import android.os.SystemClock;
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
import java.util.List;

import comp3350.srsys.R;
import comp3350.srsys.application.Main;
import comp3350.srsys.application.Services;
import comp3350.srsys.objects.Course;
import comp3350.srsys.presentation.HomePage;
import comp3350.utils.TestUtils;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CalendarTest {
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
    public void testCalendarView() {
        onView(withId(R.id.calendarButton)).perform(click());
        onView(withId(R.id.calendarRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddDeadlines() {
        onView(withId(R.id.calendarButton)).perform(click());
        onView(withId(R.id.calendarRecyclerView)).check(matches(isDisplayed()));
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.newEvent)).perform(click());
        onView(withId(R.id.floatingaddbutton)).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.reminder_title)).perform(typeText("Iteration Due"),closeSoftKeyboard());
        onView(withId(R.id.btn_done)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.list)).atPosition(0).perform(click());
    }

    //a feature that we missed for the calendar implementation is the notification portion of the
    //app where we would notify the user when the notification has occurred. This required changing
    //some stuff in the OS of the app so we left it out of the acceptance tests.
}
