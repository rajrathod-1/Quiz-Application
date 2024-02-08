package comp3350.srsys.tests.business.stubs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import comp3350.srsys.business.AccessEvents;
import comp3350.srsys.objects.Event;

public class AccessEventsTestStub {

    private AccessEvents accessEvents;
    private int expectedSize;

    @Before
    public void setUp() {
        System.out.println("Starting test for AccessEvents");
    }

    @Test
    public void testGetEvents() {
        System.out.println("Starting testGetEvents");

        accessEvents = new AccessEvents();
        expectedSize = accessEvents.getEvents().size();

        assertEquals(1, accessEvents.getEvents().get(0).getId());//check if the middle entry is present
        assertEquals(expectedSize, accessEvents.getEvents().size());

        System.out.println("Finished testGetEvents");
    }

    @Test
    public void testInsertEvent() {
        System.out.println("Starting testInsertEvent");

        accessEvents = new AccessEvents();
        expectedSize = accessEvents.getEvents().size();

        Date eventDate = new Date();
        Time eventTime = new Time(10, 10, 10);
        Event newEvent = new Event("Something", eventDate, eventTime);

        Event event = accessEvents.insertEvent(newEvent);
        assertNotNull(event);

        assertEquals(expectedSize+1, accessEvents.getEvents().size());

        System.out.println("Finished testInsertEvent");
    }

    @Test
    public void testDeleteEvent() {
        System.out.println("Starting testDeleteEvent");

        accessEvents = new AccessEvents();
        expectedSize = accessEvents.getEvents().size();

        Event event = accessEvents.getEvents().get(0);

        accessEvents.deleteEvent(event);

        assertEquals(--expectedSize, accessEvents.getEvents().size());

        System.out.println("Finished testDeleteEvent");
    }
}
