package comp3350;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.srsys.R;
import comp3350.srsys.objects.Note;
import comp3350.srsys.presentation.HomePage;
import comp3350.utils.TestUtils;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NotesTest {
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
    public void testNotesNavigation() {
        Note currentNote = testUtils.getNotesSequential();

        //Tests to check whether navigation to the notes tab
        onView(withId(R.id.classesButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onView(withId(R.id.notesButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listNotes)).atPosition(0).perform(click());


        onView(withId(R.id.noteTitle)).check(matches(withText(currentNote.getTitle())));
        onView(withId(R.id.noteContent)).check(matches(withText(currentNote.getContent())));
        onData(anything()).inAdapterView(withId(R.id.listNotes)).atPosition(1).perform(click());

        currentNote = testUtils.getNotesSequential();

        onView(withId(R.id.noteTitle)).check(matches(withText(currentNote.getTitle())));
        onView(withId(R.id.noteContent)).check(matches(withText(currentNote.getContent())));
        onData(anything()).inAdapterView(withId(R.id.listNotes)).atPosition(2).perform(click());

        currentNote = testUtils.getNotesSequential();

        onView(withId(R.id.noteTitle)).check(matches(withText(currentNote.getTitle())));
        onView(withId(R.id.noteContent)).check(matches(withText(currentNote.getContent())));

        onView(withId(R.id.backButton)).perform(click());
    }
}
