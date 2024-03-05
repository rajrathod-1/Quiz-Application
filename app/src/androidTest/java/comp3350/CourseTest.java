package comp3350;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import comp3350.srsys.objects.Course;
import comp3350.srsys.presentation.HomePage;
import comp3350.utils.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import comp3350.srsys.R;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

import java.util.List;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CourseTest {
    // Sleep time used as a delay so that the app can catch up to the test
    private final int sleepTime = 500;
    @Rule
    public ActivityScenarioRule<HomePage> activityScenarioRule = new ActivityScenarioRule<>(HomePage.class);
    private TestUtils testUtils;

    @Before
    public void setupTestUtils() {
        testUtils = new TestUtils();
    }

    @Test
    public void testFeatureNavigation() {
        //Tests to check whether navigation across the app works correctly
        onView(withId(R.id.startImage)).check(matches(isDisplayed()));
        onView(withId(R.id.classesButton)).perform(click());
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.calendarButton)).perform(click());
        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.classesButton)).perform(click());
        onView(withId(R.id.homeButton)).perform(click());
    }

    @Test
    public void testCourseView() {
        //Tests to check if the course view works correctly
        onView(withId(R.id.classesButton)).perform(click());
        onView(withId(R.id.listCourses)).check(matches(isDisplayed()));
    }

    @Test
    public void testCalendarView() {
        //Tests to check if the calendar view works correctly
        onView(withId(R.id.calendarButton)).perform(click());
        onView(withId(R.id.calendarRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testCourseSorting() {
        onView(withId(R.id.classesButton)).perform(click());
        List<Course> course = testUtils.getCourses();

        //double click on the top entry needed to unselect the previously selected course first
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onView(withId(R.id.codeOutput)).check(matches(withText(course.get(0).getTopic() + " " +  course.get(0).getCourseNum())));
        onView(withId(R.id.dateButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        course = testUtils.sortCoursesByDate();
        onView(withId(R.id.codeOutput)).check(matches(withText(course.get(0).getTopic() + " " +  course.get(0).getCourseNum())));
        onView(withId(R.id.subjectButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        course = testUtils.sortCoursesBySubject();
        onView(withId(R.id.codeOutput)).check(matches(withText(course.get(0).getTopic() + " " +  course.get(0).getCourseNum())));
        onView(withId(R.id.nameSort)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        course = testUtils.sortCoursesByName();
        onView(withId(R.id.codeOutput)).check(matches(withText(course.get(0).getTopic() + " " +  course.get(0).getCourseNum())));
        onView(withId(R.id.codeSort)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        course = testUtils.sortCoursesByID();
        onView(withId(R.id.codeOutput)).check(matches(withText(course.get(0).getTopic() + " " +  course.get(0).getCourseNum())));
    }

    @Test
    public void testCourseCreation() {
        onView(withId(R.id.classesButton)).perform(click());

        SystemClock.sleep(sleepTime);
        onView(withId(R.id.addCourse)).perform(click());
        onView(withId(R.id.cancelButton)).perform(click());

        onView(withId(R.id.addCourse)).perform(click());

        onView(withId(R.id.codeOutput)).perform(typeText("4530"), closeSoftKeyboard());
        onView(withId(R.id.nameOutput)).perform(typeText("Parallel Processing"), closeSoftKeyboard());
        onView(withId(R.id.startOutput)).perform(typeText("2024-01-07"), closeSoftKeyboard());
        onView(withId(R.id.endOutput)).perform(typeText("2024-04-07"), closeSoftKeyboard());
        onView(withId(R.id.subjectOutput)).perform(typeText("ECE"), closeSoftKeyboard());

        onView(withId(R.id.newFavoriteButton)).perform(click());

        onView(withId(R.id.createButton)).perform(click());

        onView(withId(R.id.favouriteSort)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onView(withId(R.id.codeOutput)).check(matches(withText("ECE 4530")));
    }

    @Test
    public void testCourseDeletion(){
        onView(withId(R.id.classesButton)).perform(click());
        List<Course> course = testUtils.getCourses();

        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onView(withId(R.id.codeOutput)).check(matches(withText(course.get(0).getTopic() + " " +  course.get(0).getCourseNum())));
        onView(withId(R.id.deleteEntry)).perform(click());
        onView(withId(R.id.noButton)).perform(click());
        onView(withId(R.id.deleteEntry)).perform(click());
        onView(withId(R.id.yesButton)).perform(click());

        testUtils.deleteCourse(course.get(0));
        course = testUtils.getCourses();
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.classesButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onView(withId(R.id.codeOutput)).check(matches(withText(course.get(0).getTopic() + " " +  course.get(0).getCourseNum())));

    }



}
