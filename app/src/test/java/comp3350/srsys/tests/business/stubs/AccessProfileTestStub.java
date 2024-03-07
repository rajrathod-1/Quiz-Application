package comp3350.srsys.tests.business.stubs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.AccessProfile;
import comp3350.srsys.business.AccessQuizzes;
import comp3350.srsys.objects.Profile;
import comp3350.srsys.objects.Quiz;
import comp3350.srsys.persistence.stubs.CoursePersistenceStub;
import comp3350.srsys.persistence.stubs.ProfilePersistenceStub;
import comp3350.srsys.persistence.stubs.QuizPersistenceStub;

public class AccessProfileTestStub {

    private AccessProfile accessProfile;

    @Before
    public void setUp() {
        this.accessProfile = new AccessProfile(new ProfilePersistenceStub());
        System.out.println("Starting test for AccessProfile");
    }

    @Test
    public void testGetProfile() {
        Profile profile = accessProfile.getProfile();
        assertEquals(new Profile(1,"Monkey","Monkey","monkey@monkey.com", "8808-07-8",3, "Moneky").getName(),profile.getName());
    }

    @Test
    public void testUpdateProfile() {
        accessProfile.updateProfile(new Profile(1,"Profile2","Monkey","monkey@monkey.com", "8808-07-8",3, "Moneky"));
        assertEquals("Profile2",accessProfile.getProfile().getName());

    }

    @Test
    public void testClearDatabase() {
        accessProfile.clearProfile();
        assertNull(accessProfile.getProfile());
    }

    @After
    public void tearDown() {
        Services.clean();
    }
}