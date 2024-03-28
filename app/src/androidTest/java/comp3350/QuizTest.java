package comp3350;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import android.content.Context;
import android.os.SystemClock;

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
import comp3350.srsys.objects.Quiz;
import comp3350.srsys.presentation.HomePage;
import comp3350.utils.TestUtils;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class QuizTest {

    private final int sleepTime = 500;
    @Rule
    public ActivityScenarioRule<HomePage> activityScenarioRule = new ActivityScenarioRule<>(HomePage.class);
    private TestUtils testUtils;

    @Before
    public void setupTestUtils(){
        testUtils = new TestUtils();
    }

    //similarly to the Notes test the SQL database had problems when multiple tests
    //were run. The individual Quiz tests have comments identifying them.
    @Test
    public void createEditQuizCardOfDifferentTypes() {
        //create independent course to put quizzes into (Should have no quizzes associated with it)
        onView(withId(R.id.classesButton)).perform(click());
        onView(withId(R.id.addCourse)).perform(click());
        onView(withId(R.id.codeOutput)).perform(typeText("4530"), closeSoftKeyboard());
        onView(withId(R.id.nameOutput)).perform(typeText("Parallel Processing"), closeSoftKeyboard());
        onView(withId(R.id.startOutput)).perform(typeText("2024-01-07"), closeSoftKeyboard());
        onView(withId(R.id.endOutput)).perform(typeText("2024-04-07"), closeSoftKeyboard());
        onView(withId(R.id.subjectOutput)).perform(typeText("ECE"), closeSoftKeyboard());
        onView(withId(R.id.creditHoursInput)).perform(typeText("3.0"), closeSoftKeyboard());
        onView(withId(R.id.newFavoriteButton)).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.createButton)).perform(click());
        onView(withId(R.id.favouriteSort)).perform(click());

        //create different types of quiz cards
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.quizButton)).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.quizButton)).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.addButton)).perform(click());
        onView(withId(R.id.cancelButton)).perform(click());
        onView(withId(R.id.addButton)).perform(click());
        onView(withId(R.id.inputQuestion)).perform(typeText("What is the population of Canada: QuizCard"), closeSoftKeyboard());
        onView(withId(R.id.inputChoice1)).perform(typeText("40 Million"), closeSoftKeyboard());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.createButton)).perform(click());
        onView(withId(R.id.addButton)).perform(click());
        onView(withId(R.id.listQuizType)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Multiple Choice"))).perform(click());
        onView(withId(R.id.inputQuestion)).perform(typeText("What is the population of Canada: Multiple Choice"), closeSoftKeyboard());
        onView(withId(R.id.inputChoice1)).perform(typeText("40 Million"), closeSoftKeyboard());
        onView(withId(R.id.inputChoice2)).perform(typeText("50 Million"), closeSoftKeyboard());
        onView(withId(R.id.inputChoice3)).perform(typeText("30 Million"), closeSoftKeyboard());
        onView(withId(R.id.inputChoice4)).perform(typeText("20 Million"), closeSoftKeyboard());
        onView(withId(R.id.createButton)).perform(click());
        onView(withId(R.id.addButton)).perform(click());
        onView(withId(R.id.listQuizType)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("True or False"))).perform(click());
        onView(withId(R.id.inputQuestion)).perform(typeText("Canada has 40 Million People"), closeSoftKeyboard());
        onView(withId(R.id.createButton)).perform(click());

        //edit different types quiz cards
        onData(anything()).inAdapterView(withId(R.id.listQuizzes)).atPosition(2).perform(click());
        onView(withId(R.id.editButton)).perform(click());
        onView(withId(R.id.inputQuestion)).perform(typeText(": Edited"), closeSoftKeyboard());
        onView(withId(R.id.radioButton2)).perform(click());
        onView(withId(R.id.createButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listQuizzes)).atPosition(1).perform(click());
        onView(withId(R.id.editButton)).perform(click());
        onView(withId(R.id.inputQuestion)).perform(typeText(": Edited"), closeSoftKeyboard());
        onView(withId(R.id.radioButton3)).perform(click());
        onView(withId(R.id.createButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listQuizzes)).atPosition(0).perform(click());
        onView(withId(R.id.editButton)).perform(click());
        onView(withId(R.id.inputQuestion)).perform(typeText(": Edited"), closeSoftKeyboard());
        onView(withId(R.id.inputChoice1)).perform(typeText(": Edited"), closeSoftKeyboard());
        onView(withId(R.id.createButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listQuizzes)).atPosition(2).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.editButton)).perform(click());
        onView(withId(R.id.inputQuestion)).check(matches(withText("Canada has 40 Million People: Edited")));
        onView(withId(R.id.cancelButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listQuizzes)).atPosition(1).perform(click());
        onView(withId(R.id.editButton)).perform(click());
        onView(withId(R.id.inputQuestion)).check(matches(withText("What is the population of Canada: M: Editedultiple Choice")));
        onView(withId(R.id.cancelButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listQuizzes)).atPosition(0).perform(click());
        onView(withId(R.id.editButton)).perform(click());
        onView(withId(R.id.inputQuestion)).check(matches(withText("What is the population of Canada: Qu: EditedizCard")));
        onView(withId(R.id.cancelButton)).perform(click());

        //delete quiz
        onData(anything()).inAdapterView(withId(R.id.listQuizzes)).atPosition(0).perform(click());
        onView(withId(R.id.deleteButton)).perform(click());
        onView(withId(R.id.yesButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listQuizzes)).atPosition(0).perform(click());
        onView(withId(R.id.editButton)).perform(click());
        onView(withId(R.id.inputQuestion)).check(matches(withText("What is the population of Canada: M: Editedultiple Choice")));
        onView(withId(R.id.cancelButton)).perform(click());
    }
}
