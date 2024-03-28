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
import comp3350.srsys.business.validators.EventValidator;
import comp3350.srsys.objects.Course;
import comp3350.srsys.tests.utils.TestUtils;


public class EventValidatorTest {
    @Test
    public void validateTitle_valid() {
        assertTrue(EventValidator.validateTitle("Meeting"));
        assertTrue(EventValidator.validateTitle("This is a somewhat longer event title"));
    }

    @Test
    public void validateTitle_invalid() {
        assertFalse(EventValidator.validateTitle("This title is exactly one hundred and one characters. AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
    }

    @Test
    public void validateDate_valid() {
        assertTrue(EventValidator.validateDate("25/12/2023"));
        assertTrue(EventValidator.validateDate("29/02/2024"));
    }

    @Test
    public void validateDate_invalid() {
        assertFalse(EventValidator.validateDate("29/02/2023"));
        assertFalse(EventValidator.validateDate("10/13/2023"));
        assertFalse(EventValidator.validateDate("10/00/2023"));
        assertFalse(EventValidator.validateDate("32/12/2023"));
        assertFalse(EventValidator.validateDate("2023/12"));
    }

    @Test
    public void validateTime_valid() {
        assertTrue(EventValidator.validateTime("23:59"));
        assertTrue(EventValidator.validateTime("00:00"));
        assertTrue(EventValidator.validateTime("12:34"));
    }

    @Test
    public void validateTime_invalid() {
        assertFalse(EventValidator.validateTime("24:00"));
        assertFalse(EventValidator.validateTime("00:60"));
        assertFalse(EventValidator.validateTime("23:59:01"));
        assertFalse(EventValidator.validateTime("noon"));
    }
}
