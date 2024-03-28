package comp3350;

import androidx.test.filters.LargeTest;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;

@LargeTest
@FixMethodOrder(MethodSorters.JVM)
@RunWith(Suite.class)
@Suite.SuiteClasses({
        NotesTest.class,
        QuizTest.class,
        CalendarTest.class,
        CourseTest.class,
        ProfileTest.class,
        ExampleInstrumentedTest.class
})

public class AllAcceptanceTests {
}
