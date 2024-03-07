package comp3350.srsys.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.business.AccessProfile;
import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Profile;
import comp3350.srsys.tests.utils.TestUtils;


public class AccessProfileIT {
    private AccessProfile accessProfile;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        Services.clean();
        System.out.println("Starting integration test for AccessProfile");
        this.tempDB = TestUtils.copyDB();
        this.accessProfile = new AccessProfile();

        assertNotNull(this.accessProfile);
    }

    @Test
    public void testGetProfile() {
        Profile ourProfile = accessProfile.getProfile();
        assertEquals(ourProfile.getName(),"I am Gorillaaaa");
    }

    @Test
    public void testUpdateProfile() {
        Profile ourProfile = new Profile(1,"Monkey","Monkey","monkey@monkey.com", "8808-07-8",3, "Moneky");
        accessProfile.updateProfile(ourProfile);
        assertEquals(accessProfile.getProfile().getName(),"Monkey");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();

        Services.clean();
    }
}
