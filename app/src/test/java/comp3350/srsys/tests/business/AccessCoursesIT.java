package comp3350.srsys.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.objects.Course;
import comp3350.srsys.tests.utils.TestUtils;


public class AccessCoursesIT {
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
    public void testGetCourses() {
        final List<Course> courses = accessCourses.getCourses();
        assertEquals(3,courses.size());

        System.out.println("Finished testGetCourses");
    }

    @Test
    public void testGetSequentialCourse() {
        final Course course = accessCourses.getSequential();
        assertNotNull("first sequential course should not be null", course);
        assertEquals("2160", Integer.toString(course.getCourseNum()));
        assertEquals("COMP", course.getTopic());

        System.out.println("Finished testGetSequentialCourses");
    }

    @Test
    public void testInsertCourse() {
        Course newCourse = new Course("ECE",4530);
        accessCourses.insertCourse(newCourse);
        final List<Course> courses = accessCourses.getCourses();
        assertEquals(4,courses.size());

        System.out.println("Finished testInsertCourses");
    }

    @Test
    public void testDeleteCourse() {
        accessCourses.deleteCourse(accessCourses.getSequential());
        final Course firstCourse = accessCourses.getSequential();
        assertEquals("3350", Integer.toString(firstCourse.getCourseNum()));
        assertEquals("COMP", firstCourse.getTopic());
        final List<Course> courses = accessCourses.getCourses();
        assertEquals(2,courses.size());

        System.out.println("Finished testDeleteCourses");
    }

    @Test
    public void testSortCoursesByFavorite() {
        Course newCourse = new Course("COMP", 3190, "Intro to AI", 1, 0, 2024, 4, 5, 2025,3,4.5);
        newCourse.favoriteCourse();
        accessCourses.insertCourse(newCourse);
        accessCourses.sortCoursesByFavorite();
        final Course favoriteCourse = accessCourses.getSequential();
        assertEquals(newCourse,favoriteCourse);

        System.out.println("Finished testSortCoursesByFavorite");
    }

    @Test
    public void testSortCoursesByID() {
        Course newCourse = new Course("COMP", 1010, "Intro to Computer Science", 1, 0, 2024, 4, 5, 2025,3,4.5);
        accessCourses.insertCourse(newCourse);
        accessCourses.sortCoursesByID();
        final Course favoriteCourse = accessCourses.getSequential();
        assertEquals(newCourse,favoriteCourse);

        System.out.println("Finished testSortCoursesByID");
    }

    @Test
    public void testSortCoursesByName() {
        Course newCourse = new Course("COMP", 4190, "Artificial Intelligence 2", 1, 0, 2024, 4, 5, 2025,3,4.5);
        accessCourses.insertCourse(newCourse);
        accessCourses.sortCoursesByName();
        final Course favoriteCourse = accessCourses.getSequential();
        assertEquals(newCourse,favoriteCourse);

        System.out.println("Finished testSortCoursesByName");
    }

    @Test
    public void testSortCoursesBySubject() {
        Course newCourse = new Course("AGRI", 1010, "Agriculture", 1, 0, 2024, 4, 5, 2025,3,4.5);
        accessCourses.insertCourse(newCourse);
        accessCourses.sortCoursesBySubject();
        final Course favoriteCourse = accessCourses.getSequential();
        assertEquals(newCourse,favoriteCourse);

        System.out.println("Finished testSortCoursesBySubject");
    }

    @Test
    public void testSortObjectsByDate(){
        Course newCourse = new Course("COMP", 3190, "Intro to AI", 1, 0, 2000, 4, 5, 2000,3,4.5);
        accessCourses.insertCourse(newCourse);
        accessCourses.sortCoursesByDate();
        final Course favoriteCourse = accessCourses.getSequential();
        assertEquals(newCourse,favoriteCourse);

        System.out.println("Finished testSortCoursesByDate");

    }

    @Test
    public void testDeleteNonExistingCourse() {
        Course course = new Course("COMP", 3190, "Intro to AI", 1, 0, 2024, 4, 5, 2025,3,4.5);

        accessCourses.deleteCourse(course);

        //expected size should remain the same
        assertEquals(3, accessCourses.getCourses().size());

        System.out.println("Finished testDeleteNonExistingCourse");
    }

    @Test
    public void testChangeGPA() {
        List<Course> courseList = accessCourses.getCourses();
        assertEquals(courseList.get(0).getGPA(),3.5,0.1);

        accessCourses.changeGPA(courseList.get(0),4.5);

        assertEquals(courseList.get(0).getGPA(),4.5,0.1);

    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();

        Services.clean();
    }
}
