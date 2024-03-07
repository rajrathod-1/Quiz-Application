package comp3350.srsys.tests;

import comp3350.srsys.tests.business.AccessCoursesIT;
import comp3350.srsys.tests.business.AccessEventsIT;
import comp3350.srsys.tests.business.AccessNotesIT;
import comp3350.srsys.tests.business.AccessProfileIT;
import comp3350.srsys.tests.business.AccessQuizzesIT;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessCoursesIT.class,
        AccessEventsIT.class,
        AccessNotesIT.class,
        AccessQuizzesIT.class,
        AccessProfileIT.class
})

public class AllIntegrationTests {
}
