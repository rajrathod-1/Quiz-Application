package comp3350.srsys.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.srsys.tests.business.mockTests.AccessCoursesTestMock;
import comp3350.srsys.tests.business.stubs.AccessCalendarTestStub;
import comp3350.srsys.tests.business.stubs.AccessCoursesTestStub;
import comp3350.srsys.tests.business.stubs.AccessEventsTestStub;
import comp3350.srsys.tests.business.stubs.AccessNotesTestStub;
import comp3350.srsys.tests.business.stubs.AccessProfileTestStub;
import comp3350.srsys.tests.business.stubs.AccessQuizzesTestStub;
import comp3350.srsys.tests.business.QuizMarkTest;
import comp3350.srsys.tests.business.validatorsTests.CourseValidatorTest;
import comp3350.srsys.tests.business.validatorsTests.EventValidatorTest;
import comp3350.srsys.tests.business.validatorsTests.NoteValidatorTest;
import comp3350.srsys.tests.business.validatorsTests.ProfileValidatorTest;
import comp3350.srsys.tests.business.validatorsTests.QuizValidatorTest;
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
        AccessCalendarTestStub.class,
        AccessNotesTestStub.class,
        AccessEventsTestStub.class,
        AccessQuizzesTestStub.class,
        AccessProfileTestStub.class,
        QuizMarkTest.class,
        CourseValidatorTest.class,
        EventValidatorTest.class,
        NoteValidatorTest.class,
        ProfileValidatorTest.class,
        QuizValidatorTest.class,
        AccessCoursesTestMock.class
})

public class AllUnitTests
{

}
