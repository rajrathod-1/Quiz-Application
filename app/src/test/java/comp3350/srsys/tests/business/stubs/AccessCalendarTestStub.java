package comp3350.srsys.tests.business.stubs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static comp3350.srsys.business.AccessCalendarUtils.daysInMonthArray;
import static comp3350.srsys.business.AccessCalendarUtils.monthYearFromDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.objects.Course;
import comp3350.srsys.tests.utils.TestUtils;


public class AccessCalendarTestStub {
    private AccessCourses accessCourses;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        Services.clean();
        System.out.println("Starting integration test for AccessCourses");
        this.tempDB = TestUtils.copyDB();
        this.accessCourses = new AccessCourses();

        assertNotNull(this.accessCourses);
    }

    @Test
    public void testMonthYearFromDate(){
         LocalDate ourDate = LocalDate.now();
         int year = ourDate.getYear();
         String month = ourDate.getMonth().toString();

         String output = monthYearFromDate(ourDate);

         assertEquals(output.toUpperCase(),  month.toUpperCase() + " " + year);
    }

    @Test
    public void testDaysInMonthArray(){
        LocalDate ourDate = LocalDate.now();


        ArrayList<LocalDate> ourArray = daysInMonthArray(ourDate);

        assertEquals(42, ourArray.size());
    }

    @After
    public void tearDown() {
        Services.clean();
    }
}
