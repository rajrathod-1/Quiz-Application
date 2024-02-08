package comp3350;

import androidx.test.filters.LargeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@LargeTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CourseTest.class,
        ExampleInstrumentedTest.class,
        NotesTest.class,
        QuizTest.class
})

public class AllAcceptanceTests {
}
