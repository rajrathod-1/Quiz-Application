package comp3350.srsys.tests.business.stubs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.objects.Course;
import comp3350.srsys.persistence.stubs.CoursePersistenceStub;
import kotlin.coroutines.RestrictsSuspension;

public class AccessCoursesTestStub {

    private AccessCourses accessCourses;
    private Course course;
    private int expectedSize;

    @Before
    public void setup() {
        accessCourses = new AccessCourses(new CoursePersistenceStub());
        expectedSize = accessCourses.getCourses().size();
        System.out.println("Starting test for AccessCourses");
    }

    @Test
    public void testInsertCourse() {
        System.out.println("Starting test for testInsertCourse");

        assertEquals(expectedSize, accessCourses.getCourses().size());

        course = new Course("COMP", 3190, "Intro to AI", 1, 0, 2024, 4, 5, 2025);
        accessCourses.insertCourse(course);
        expectedSize++;

        assertEquals(expectedSize, accessCourses.getCourses().size());
    }

    @Test
    public void testDeleteExistingCourse() {
        course = accessCourses.getCourses().get(0);

        accessCourses.deleteCourse(course);
        expectedSize--;

        assertEquals(expectedSize, accessCourses.getCourses().size());
    }

    @Test
    public void testGetSequential() {
        accessCourses.getSequential();
        accessCourses.getSequential();
        accessCourses.getSequential();
        accessCourses.getSequential();
        accessCourses.getSequential();
        accessCourses.getSequential();
        Course ourCourse = accessCourses.getSequential();
        assertNull(ourCourse);
    }

    @Test
    public void testSortCoursesByFavorite() {

        Course newCourse = new Course("COMP", 3190, "Intro to AI", 1, 0, 2024, 4, 5, 2025);
        newCourse.favoriteCourse();

        accessCourses.insertCourse(newCourse);

        //expecting course to be the first in the list
        accessCourses.sortCoursesByFavorite();

        course = accessCourses.getCourses().get(0);

        assertEquals("COMP", course.getTopic());
        assertEquals(3190, course.getCourseNum());
        assertEquals("Intro to AI", course.getCourseName());

    }

    @Test
    public void testSortCoursesByID() {

        accessCourses.sortCoursesByID();
        course = accessCourses.getCourses().get(0);
        assertEquals(2160, course.getCourseNum());

        //test second course
        course = accessCourses.getCourses().get(1);
        assertEquals(3020, course.getCourseNum());

    }

    @Test
    public void testSortCoursesByName() {
        accessCourses.sortCoursesByName();

        course = accessCourses.getCourses().get(0);
        assertEquals("Biology 1", course.getCourseName());

        //test last course
        course = accessCourses.getCourses().get(expectedSize-1);
        assertEquals("Software Engineering I", course.getCourseName());

    }

    @Test
    public void testSortCoursesBySubject() {
        accessCourses.sortCoursesBySubject();

        //test first course
        course = accessCourses.getCourses().get(0);
        assertEquals("AGRI", course.getTopic());

        //test last course
        course = accessCourses.getCourses().get(expectedSize-1);
        assertEquals("ECE", course.getTopic());


    }

    @Test
    public void testSortObjectsByDate(){
        Course newCourse = new Course("COMP", 3190, "Intro to AI", 1, 0, 2000, 4, 5, 2000);
        newCourse.favoriteCourse();

        accessCourses.insertCourse(newCourse);

        //expecting course to be the first in the list
        accessCourses.sortCoursesByDate();

        course = accessCourses.getCourses().get(0);

        assertEquals("COMP", course.getTopic());
        assertEquals(3190, course.getCourseNum());
        assertEquals("Intro to AI", course.getCourseName());

    }

    @Test
    public void testDeleteNonExistingCourse() {
        course = new Course("COMP", 3190, "Intro to AI", 1, 0, 2024, 4, 5, 2025);

        accessCourses.deleteCourse(course);

        //expected size should remain the same
        assertEquals(expectedSize, accessCourses.getCourses().size());
    }

    @After
    public void tearDown() {
        Services.clean();
    }
}
