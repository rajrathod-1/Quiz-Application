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

import static org.hamcrest.Matchers.anything;

import android.app.Service;
import android.content.Context;
import android.os.SystemClock;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.srsys.R;
import comp3350.srsys.application.Main;
import comp3350.srsys.application.Services;
import comp3350.srsys.objects.Note;
import comp3350.srsys.presentation.HomePage;
import comp3350.utils.TestUtils;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
public class NotesTest {
    private final int sleepTime = 500;
    @Rule
    public ActivityScenarioRule<HomePage> activityScenarioRule = new ActivityScenarioRule<>(HomePage.class);

    private TestUtils testUtils;
    @Before
    public void setupTestUtils(){
        testUtils = new TestUtils();
    }
    @Test
    public void testNoteViewing() {
        onView(withId(R.id.classesButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onView(withId(R.id.notesButton)).perform(click());
    }

    //this test was originally three separate tests. Due to database errors they had to be merged into 1.
    //each respective component that was tests has a comment identifying it.
    @Test
    public void testAddDeleteEditNote() {
        //edit note test
        onView(withId(R.id.classesButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onView(withId(R.id.notesButton)).perform(click());
        onView(withId(R.id.addNoteButton)).perform(click());
        onView(withId(R.id.noteTitle)).check(matches(withText("New Note")));
        onView(withId(R.id.noteTitle)).perform(click());
        onView(withId(R.id.noteTitle)).perform(typeText(": Test Note"), closeSoftKeyboard());
        onView(withId(R.id.noteContent)).perform(click());
        onView(withId(R.id.noteContent)).perform(typeText("This is content"), closeSoftKeyboard());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.saveNoteButton)).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.sortByLastEditedButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listNotes)).atPosition(0).perform(click());
        onView(withId(R.id.noteTitle)).check(matches(withText("New Note: Test Note")));
        onView(withId(R.id.noteContent)).perform(typeText(": This is saved content"), closeSoftKeyboard());
        onView(withId(R.id.saveNoteButton)).perform(click());
        onView(withId(R.id.saveNoteButton)).perform(click());
        onView(withId(R.id.backButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onView(withId(R.id.notesButton)).perform(click());
        onView(withId(R.id.sortByTitleButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listNotes)).atPosition(0).perform(click());
        onView(withId(R.id.noteContent)).check(matches(withText("This is content")));
        onView(withId(R.id.backButton)).perform(click());

        //add note test
        onView(withId(R.id.classesButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onView(withId(R.id.notesButton)).perform(click());
        onView(withId(R.id.addNoteButton)).perform(click());
        onView(withId(R.id.noteTitle)).check(matches(withText("New Note")));
        onView(withId(R.id.noteTitle)).perform(click());
        onView(withId(R.id.noteTitle)).perform(typeText(": Add Test Note"), closeSoftKeyboard());
        onView(withId(R.id.noteContent)).perform(click());
        onView(withId(R.id.noteContent)).perform(typeText("This is content"), closeSoftKeyboard());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.saveNoteButton)).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.backButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onView(withId(R.id.notesButton)).perform(click());
        onView(withId(R.id.sortByLastEditedButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listNotes)).atPosition(0).perform(click());
        onView(withId(R.id.noteTitle)).check(matches(withText("New Note: Add Test Note")));
        onView(withId(R.id.backButton)).perform(click());

        //delete note test
        onView(withId(R.id.classesButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onView(withId(R.id.notesButton)).perform(click());
        onView(withId(R.id.addNoteButton)).perform(click());
        onView(withId(R.id.noteTitle)).check(matches(withText("New Note")));
        onView(withId(R.id.noteTitle)).perform(click());
        onView(withId(R.id.noteTitle)).perform(typeText(": Check Note"), closeSoftKeyboard());
        onView(withId(R.id.noteContent)).perform(click());
        onView(withId(R.id.noteContent)).perform(typeText("This is to check deletion"), closeSoftKeyboard());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.saveNoteButton)).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.addNoteButton)).perform(click());
        onView(withId(R.id.noteTitle)).check(matches(withText("New Note")));
        onView(withId(R.id.noteTitle)).perform(click());
        onView(withId(R.id.noteTitle)).perform(typeText(": Test Note"), closeSoftKeyboard());
        onView(withId(R.id.noteContent)).perform(click());
        onView(withId(R.id.noteContent)).perform(typeText("This is content"), closeSoftKeyboard());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.saveNoteButton)).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.sortByLastEditedButton)).perform(click());
        onView(withId(R.id.noteTitle)).check(matches(withText("New Note: Test Note")));
        onView(withId(R.id.deleteNoteButton)).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.noButton)).perform(click());
        onView(withId(R.id.deleteNoteButton)).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.yesButton)).perform(click());
        onView(withId(R.id.backButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onView(withId(R.id.notesButton)).perform(click());
        onView(withId(R.id.sortByTitleButton)).perform(click());
        onView(withId(R.id.noteTitle)).check(matches(withText("New Note: Check Note")));
    }
}
