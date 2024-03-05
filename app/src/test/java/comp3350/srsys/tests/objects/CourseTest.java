package comp3350.srsys.tests.objects;

import static org.junit.Assert.*;

import comp3350.srsys.application.Services;
import comp3350.srsys.objects.Course;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;


public class CourseTest {

    @Before
    public void setup() {
        System.out.println("Starting test for Course");
    }

    @Test
    public void testCreateAFullCourse() {
        System.out.println("\nStarting testCreateACourse");

        //creates the course that will be tested
        Course ourCourse = new Course("COMP", 3350, "Software Engineering 1", 1, 7, 2024, 4, 10, 2024);

        assertNotNull(ourCourse);
        assertEquals("COMP", ourCourse.getTopic());
        assertEquals(3350, ourCourse.getCourseNum());
        assertEquals("Software Engineering 1", ourCourse.getCourseName());
        assertEquals("2024-1-7",ourCourse.getStartDateString());
        assertEquals("2024-4-10",ourCourse.getEndDateString());
        assertEquals(2024,ourCourse.getStartYear());
        assertEquals(1,ourCourse.getStartMonth());
        assertEquals(7,ourCourse.getStartDate());
        assertEquals(0,ourCourse.getNotesCreated());
        assertEquals(0, ourCourse.getQuizCreated());
        assertEquals("Course: COMP  3350: Software Engineering 1",ourCourse.toString());
        assertEquals(Objects.hash(ourCourse.getTopic(),ourCourse.getCourseNum()),ourCourse.hashCode());

    }

    @Test
    public void NullCourse(){
        Course ourCourse = new Course("COMP", 3350);

        assertEquals("COMP", ourCourse.getTopic());
        assertEquals(3350, ourCourse.getCourseNum());
        assertEquals("",ourCourse.getCourseName());
        assertEquals(0,ourCourse.getStartDate());
        assertEquals(0,ourCourse.getEndDate());
        assertEquals(0,ourCourse.getStartYear());
        assertEquals(0,ourCourse.getStartMonth());
        assertEquals(0,ourCourse.getStartDate());
        assertEquals(0,ourCourse.getNotesCreated());
        assertEquals(0, ourCourse.getQuizCreated());
        assertFalse(ourCourse.getmarked());
        ourCourse.favoriteCourse();
        assertTrue(ourCourse.getmarked());
        ourCourse.unfavoriteCourse();
        assertFalse(ourCourse.getmarked());
    }
}
