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

import android.os.SystemClock;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import comp3350.srsys.R;
import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Quiz;
import comp3350.srsys.presentation.HomePage;
import comp3350.utils.TestUtils;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class QuizTest {
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
    public void testQuizCardNavigation() {
        Quiz currentQuiz = testUtils.getNextQuizSequential();

        //Tests to make sure that quiz card content is correct and that we can navigate the quiz cards proper
        onView(withId(R.id.classesButton)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listCourses)).atPosition(0).perform(click());
        onView(withId(R.id.quizButton)).perform(click());

        onView(withId(R.id.quizQuestion)).check(matches(withText(currentQuiz.getQuestion())));
        onView(withId(R.id.revealAnswerButton)).perform(click());
        onView(withId(R.id.quizAnswer)).check(matches(withText(currentQuiz.getAnswer())));
        onView(withId(R.id.nextCardButton)).perform(click());

        currentQuiz = testUtils.getNextQuizSequential();

        onView(withId(R.id.quizQuestion)).check(matches(withText(currentQuiz.getQuestion())));
        onView(withId(R.id.revealAnswerButton)).perform(click());
        onView(withId(R.id.quizAnswer)).check(matches(withText(currentQuiz.getAnswer())));
        onView(withId(R.id.nextCardButton)).perform(click());

        currentQuiz = testUtils.getNextQuizSequential();

        onView(withId(R.id.quizQuestion)).check(matches(withText(currentQuiz.getQuestion())));
        onView(withId(R.id.revealAnswerButton)).perform(click());
        onView(withId(R.id.quizAnswer)).check(matches(withText(currentQuiz.getAnswer())));
        onView(withId(R.id.nextCardButton)).perform(click());

        currentQuiz = testUtils.getNextQuizSequential();

        onView(withId(R.id.quizQuestion)).check(matches(withText(currentQuiz.getQuestion())));
        onView(withId(R.id.revealAnswerButton)).perform(click());
        onView(withId(R.id.quizAnswer)).check(matches(withText(currentQuiz.getAnswer())));
        onView(withId(R.id.nextCardButton)).perform(click());

        onView(withId(R.id.backButton)).perform(click());

    }
}
