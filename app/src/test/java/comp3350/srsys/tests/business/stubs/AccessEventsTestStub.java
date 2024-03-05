package comp3350.srsys.tests.business.stubs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.AccessEvents;
import comp3350.srsys.objects.Event;
import comp3350.srsys.persistence.stubs.CoursePersistenceStub;
import comp3350.srsys.persistence.stubs.EventPersistenceStub;

public class AccessEventsTestStub {

    private AccessEvents accessEvents;
    private int expectedSize;

    @Before
    public void setUp() {
        accessEvents = new AccessEvents(new EventPersistenceStub());
        expectedSize = accessEvents.getEvents().size();
        System.out.println("Starting test for AccessEvents");
    }

    @Test
    public void testGetEvents() {
        System.out.println("Starting testGetEvents");

        assertEquals("Iteration 0", accessEvents.getEvents().get(0).getTitle());//check if the middle entry is present
        assertEquals(3, accessEvents.getEvents().size());

        System.out.println("Finished testGetEvents");
    }

    @Test
    public void testInsertEvent() {
        System.out.println("Starting testInsertEvent");

        Date eventDate = new Date();
        Time eventTime = new Time(10, 10, 10);
        Event newEvent = new Event("Something", "2024-03-01", "16:00:00");

        Event event = accessEvents.insertEvent(newEvent);
        assertNotNull(event);

        assertEquals(expectedSize+1, accessEvents.getEvents().size());

        System.out.println("Finished testInsertEvent");
    }

    @Test
    public void testDeleteEvent() {
        System.out.println("Starting testDeleteEvent");

        Event event = accessEvents.getEvents().get(0);

        accessEvents.deleteEvent(event);

        assertEquals(--expectedSize, accessEvents.getEvents().size());

        System.out.println("Finished testDeleteEvent");
    }

    @Test
    public void testDeleteEventById() {
        System.out.println("Starting testDeleteEventById");

        Event event1 = accessEvents.getEvents().get(0);

        accessEvents.deleteEventById(0);

        Event event2 = accessEvents.getEvents().get(0);

        assertNotEquals(event1,event2);

        System.out.println("Finished testDeleteEventById");
    }

    @After
    public void tearDown() {
        Services.clean();
    }
}
