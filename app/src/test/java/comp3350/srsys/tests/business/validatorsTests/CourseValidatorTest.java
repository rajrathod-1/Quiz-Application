package comp3350.srsys.tests.business.validatorsTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import comp3350.srsys.business.validators.CourseValidator;
import comp3350.srsys.objects.Course;
import comp3350.srsys.tests.utils.TestUtils;


public class CourseValidatorTest {
    @Test
    public void validateCourseNum_valid() {
        assertTrue(CourseValidator.validateCourseNum(1000));
        assertTrue(CourseValidator.validateCourseNum(7999));
    }

    @Test
    public void validateCourseNum_invalid() {
        assertFalse(CourseValidator.validateCourseNum(8000));
        assertFalse(CourseValidator.validateCourseNum(1234.5));
        assertFalse(CourseValidator.validateCourseNum(-500));
    }

    @Test
    public void validateCourseSubject_valid() {
        assertTrue(CourseValidator.validateCourseSubject("COMP"));
        assertTrue(CourseValidator.validateCourseSubject("HIST"));
    }

    @Test
    public void validateCourseSubject_invalid() {
        assertFalse(CourseValidator.validateCourseSubject("A"));
        assertFalse(CourseValidator.validateCourseSubject("VeryLongSubjectName"));
    }

    @Test
    public void validateCourseName_valid() {
        assertTrue(CourseValidator.validateCourseName("Software Engineering 1"));
        assertTrue(CourseValidator.validateCourseName("Object Orientation"));
    }

    @Test
    public void validateCourseName_invalid() {
        assertFalse(CourseValidator.validateCourseName("A"));
        assertFalse(CourseValidator.validateCourseName("This is a very long course name that should fail the validation check"));
    }

    @Test
    public void validateDate_valid() {
        assertTrue(CourseValidator.validateDate("2020-12-31"));
        assertTrue(CourseValidator.validateDate("2024-02-29"));
    }

    @Test
    public void validateDate_invalid() {
        assertFalse(CourseValidator.validateDate("2020-13-31"));
        assertFalse(CourseValidator.validateDate("2020-02-30"));
        assertFalse(CourseValidator.validateDate("2020-04-31"));
        assertFalse(CourseValidator.validateDate("2019-02-29"));
        assertFalse(CourseValidator.validateDate("2019-02"));
    }

    @Test
    public void validateCreditHours_valid() {
        assertTrue(CourseValidator.validateCreditHours(3));
        assertTrue(CourseValidator.validateCreditHours(1.5));
    }

    @Test
    public void validateCreditHours_invalid() {
        assertFalse(CourseValidator.validateCreditHours(0));
        assertFalse(CourseValidator.validateCreditHours(7));
        assertFalse(CourseValidator.validateCreditHours(8));
    }

    @Test
    public void validateNewGPA_valid() {
        assertTrue(CourseValidator.validateNewGPA(4.5));
        assertTrue(CourseValidator.validateNewGPA(3.2));
        assertTrue(CourseValidator.validateNewGPA(0));
    }

    @Test
    public void validateNewGPA_invalid() {
        assertFalse(CourseValidator.validateNewGPA(4.6));
        assertFalse(CourseValidator.validateNewGPA(-1));
    }
}
