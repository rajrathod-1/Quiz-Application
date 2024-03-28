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
import comp3350.srsys.business.validators.ProfileValidator;
import comp3350.srsys.objects.Course;
import comp3350.srsys.tests.utils.TestUtils;


public class ProfileValidatorTest {
    @Test
    public void validateName_valid() {
        assertTrue(ProfileValidator.validateName("John Doe"));
        assertTrue(ProfileValidator.validateName("aaaaaaaaaaaaaaaaaaa"));
    }

    @Test
    public void validateName_invalid() {
        assertFalse(ProfileValidator.validateName("aaaaaaaaaaaaaaaaaaaaa"));
    }

    @Test
    public void validateUserName_valid() {
        assertTrue(ProfileValidator.validateUserName("user1234"));
        assertTrue(ProfileValidator.validateUserName("aaaaaaaaaaaaaaaaaaa"));
    }

    @Test
    public void validateUserName_invalid() {
        assertFalse(ProfileValidator.validateUserName("aaaaaaaaaaaaaaaaaaaaa"));
    }

    @Test
    public void validateEmail_valid() {
        assertTrue(ProfileValidator.validateEmail("email@example.com"));
        assertTrue(ProfileValidator.validateEmail("firstname.lastname@example.com"));
    }

    @Test
    public void validateEmail_invalid() {
        assertFalse(ProfileValidator.validateEmail("email@123.123"));
        assertFalse(ProfileValidator.validateEmail("email@example.com@"));
        assertFalse(ProfileValidator.validateEmail("email@.example.com"));
    }

    @Test
    public void validatePhone_valid() {
        assertTrue(ProfileValidator.validatePhone("123-456-7890"));
    }

    @Test
    public void validatePhone_invalid() {
        assertFalse(ProfileValidator.validatePhone("1234567890"));
        assertFalse(ProfileValidator.validatePhone("123-456-789"));
        assertFalse(ProfileValidator.validatePhone("1-234-567-8901"));
    }
}
