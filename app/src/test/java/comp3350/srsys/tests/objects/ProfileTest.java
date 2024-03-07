package comp3350.srsys.tests.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Profile;


public class ProfileTest {

    @Before
    public void setup() {
        System.out.println("Starting test for Profile");
    }

    @Test
    public void testCreateProfile() {
        Profile ourProfile = new Profile(1,"Monkey","Monkey","monkey@monkey.com", "8808-07-8",3, "Moneky");
        assertNotNull(ourProfile);
    }

    @Test
    public void testGetId() {
        Profile ourProfile = new Profile(1,"Monkey","Monkey","monkey@monkey.com", "8808-07-8",3, "Moneky");
        assertEquals(ourProfile.getID(),1);
    }

    @Test
    public void testGetName() {
        Profile ourProfile = new Profile(1,"Monkey","Monkey","monkey@monkey.com", "8808-07-8",3, "Moneky");
        assertEquals(ourProfile.getName(),"Monkey");
    }

    @Test
    public void testGetUsername() {
        Profile ourProfile = new Profile(1,"Monkey","Monkey1","monkey@monkey.com", "8808-07-8",3, "Moneky");
        assertEquals(ourProfile.getUsername(),"Monkey1");
    }

    @Test
    public void testGetEmail() {
        Profile ourProfile = new Profile(1,"Monkey","Monkey","monkey@monkey.com", "8808-07-8",3, "Moneky");
        assertEquals(ourProfile.getEmail(),"monkey@monkey.com");
    }

    @Test
    public void testGetPhone() {
        Profile ourProfile = new Profile(1,"Monkey","Monkey","monkey@monkey.com", "8808-07-8",3, "Moneky");
        assertEquals(ourProfile.getPhone(),"8808-07-8");
    }

    @Test
    public void testGetNumCourses() {
        Profile ourProfile = new Profile(1,"Monkey","Monkey","monkey@monkey.com", "8808-07-8",3, "Moneky");
        assertEquals(ourProfile.getNumCourses(),3);
    }

    @Test
    public void testGetSemester() {
        Profile ourProfile = new Profile(1,"Monkey","Monkey","monkey@monkey.com", "8808-07-8",3, "Moneky");
        assertEquals(ourProfile.getSemester(),"Moneky");
    }
}
