package comp3350.srsys.tests;



import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.srsys.tests.business.stubs.AccessCoursesTestStub;
import comp3350.srsys.tests.business.stubs.AccessEventsTestStub;
import comp3350.srsys.tests.business.stubs.AccessQuizzesTestStub;
import comp3350.srsys.tests.objects.CourseTest;
import comp3350.srsys.tests.objects.EventTest;
import comp3350.srsys.tests.objects.InitObjectTest;
import comp3350.srsys.tests.objects.NoteTest;
import comp3350.srsys.tests.objects.QuizTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CourseTest.class,
        EventTest.class,
        InitObjectTest.class,
        NoteTest.class,
        QuizTest.class,
        AccessCoursesTestStub.class,
        AccessEventsTestStub.class,
        AccessQuizzesTestStub.class
})

public class AllUnitTests
{

}
