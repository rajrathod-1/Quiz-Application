package comp3350.srsys.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.AccessEvents;
import comp3350.srsys.objects.Event;
import comp3350.srsys.tests.utils.TestUtils;


public class AccessEventsIT {
    private AccessEvents accessEvents;
    private File tempDB;


    @Before
    public void setUp() throws IOException {
        Services.clean();
        System.out.println("Starting integration test for AccessEvents");
        this.tempDB = TestUtils.copyDB();
        this.accessEvents = new AccessEvents();

        assertNotNull(this.accessEvents);
    }

    @Test
    public void testGetEvents() {
        final List<Event> events = accessEvents.getEvents();
        assertEquals(3,events.size());

        System.out.println("Finished testGetEvents");
    }

    @Test
    public void testAddEvent() {
        accessEvents.insertEvent(new Event("Something", "2024-03-01", "16:00:00"));

        final List<Event> courses = accessEvents.getEvents();
        assertEquals(4,courses.size());

        System.out.println("Finished testAddEvent");
    }

    @Test
    public void testDeleteEvent() {
        accessEvents.deleteEvent(new Event("Iteration 1 due", "2024-03-01", "16:00:00"));

        final List<Event> courses = accessEvents.getEvents();
        assertEquals(2,courses.size());

        System.out.println("Finished testDeleteEvent");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();

        Services.clean();
    }
}
