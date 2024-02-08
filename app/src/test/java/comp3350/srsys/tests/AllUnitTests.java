package comp3350.srsys.tests;



import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.tests.business.stubs.AccessCoursesTestStub;
import comp3350.srsys.tests.business.stubs.AccessEventsTestStub;
import comp3350.srsys.tests.business.stubs.AccessQuizzesTestStub;
import comp3350.srsys.tests.business.stubs.AccessTagsTestStub;
import comp3350.srsys.tests.objects.CourseTest;
import comp3350.srsys.tests.objects.EventTest;
import comp3350.srsys.tests.objects.InitObjectTest;
import comp3350.srsys.tests.objects.NoteTest;
import comp3350.srsys.tests.objects.QuizTest;
import comp3350.srsys.tests.objects.TagTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CourseTest.class,
        EventTest.class,
        InitObjectTest.class,
        NoteTest.class,
        QuizTest.class,
        TagTest.class,
        AccessCoursesTestStub.class,
        AccessEventsTestStub.class,
        AccessQuizzesTestStub.class,
        AccessTagsTestStub.class
})

public class AllUnitTests
{

}
