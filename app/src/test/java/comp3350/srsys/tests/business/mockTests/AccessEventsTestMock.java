package comp3350.srsys.tests.business.mockTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import comp3350.srsys.business.AccessEvents;
import comp3350.srsys.objects.Event;
import comp3350.srsys.persistence.IEventPersistence;

public class AccessEventsTestMock {

    @Mock
    private IEventPersistence mockEventPersistence;

    private AccessEvents accessEvents;

    @Before
    public void setUp() {
        mockEventPersistence = mock(IEventPersistence.class);
        accessEvents = new AccessEvents(mockEventPersistence);
    }

    @Test
    public void testGetEvents() {
        List<Event> expectedEvents = Arrays.asList(new Event(1, "Event 1", "2023-03-28","12:00:00"), new Event(2, "Event 2", "2023-03-29","12:00:00"));
        when(mockEventPersistence.getEventList()).thenReturn(expectedEvents);

        List<Event> actualEvents = accessEvents.getEvents();

        assertNotNull(actualEvents);
        assertEquals(expectedEvents, actualEvents);
        verify(mockEventPersistence).getEventList();
    }

    @Test
    public void testInsertEvent() {
        Event newEvent = new Event(3, "Event 3", "2023-04-01","12:00:00");
        when(mockEventPersistence.insertEvent(any(Event.class))).thenReturn(newEvent);

        Event result = accessEvents.insertEvent(newEvent);

        assertNotNull(result);
        assertEquals(newEvent, result);
        verify(mockEventPersistence).insertEvent(newEvent);
    }

    @Test
    public void testDeleteEvent() {
        Event eventToDelete = new Event(1, "Event to Delete", "2023-03-30","12:00:00");

        doNothing().when(mockEventPersistence).deleteEvent(any(Event.class));

        accessEvents.deleteEvent(eventToDelete);

        verify(mockEventPersistence).deleteEvent(eventToDelete);
    }

    @Test
    public void testDeleteEventById() {
        int eventIdToDelete = 1;

        doNothing().when(mockEventPersistence).deleteEventById(anyInt());

        accessEvents.deleteEventById(eventIdToDelete);

        verify(mockEventPersistence).deleteEventById(eventIdToDelete);
    }
}
