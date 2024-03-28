package comp3350.srsys.tests.business.mockTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.objects.Course;
import comp3350.srsys.persistence.ICoursePersistence;
import comp3350.srsys.persistence.stubs.CoursePersistenceStub;

public class AccessCoursesTestMock {

    private ICoursePersistence coursePersistence;
    private AccessCourses accessCourses;
    private Course course1,course2;

    @Before
    public void setup() {
        coursePersistence = mock(ICoursePersistence.class);
        accessCourses = new AccessCourses(coursePersistence);
        course1 = mock(Course.class);
        course2 = mock(Course.class);
        System.out.println("Starting mock test for AccessCourses");
    }

    @Test
    public void testGetTermGPAWithNullCourses() {
        when(accessCourses.getCourses()).thenReturn(null);
        assertEquals(0.0, accessCourses.getTermGPA(), 0.01);
    }

    @Test
    public void testGetTermGPAWithEmptyCourses() {
        when(accessCourses.getCourses()).thenReturn(Arrays.asList());
        assertEquals(0.0, accessCourses.getTermGPA(), 0.01);
    }

    @Test
    public void testGetTermGPAWithCourses() {
        when(accessCourses.getCourses()).thenReturn(Arrays.asList(course1, course2));

        when(course1.getGPA()).thenReturn(4.0);
        when(course1.getCreditHours()).thenReturn(3.0);
        when(course2.getGPA()).thenReturn(3.5);
        when(course2.getCreditHours()).thenReturn(4.0);

        double expectedGPA = ((4.0 * 3) + (3.5 * 4)) / (3 + 4);
        expectedGPA = Math.round(expectedGPA * 100.0) / 100.0;

        assertEquals(expectedGPA, accessCourses.getTermGPA(), 0.01);
    }

    @Test
    public void testSetGPAWithSpy() {
        Course course = new Course("History", 104, "World History", 8, 25, 2023, 12, 20, 2023, 3.0);

        Course spyCourse = spy(course);

        spyCourse.setGPA(3.75);

        verify(spyCourse).setGPA(3.75);
    }

    @Test
    public void testChangeGPA() {
        System.out.println("Starting test for testChangeGPA");

        Course mockCourse = mock(Course.class);

        double newGPA = 3.5;

        coursePersistence.changeGPA(mockCourse, newGPA);
    }
}
