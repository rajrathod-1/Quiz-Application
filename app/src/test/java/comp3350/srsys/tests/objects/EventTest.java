package comp3350.srsys.tests.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.Date;

import comp3350.srsys.objects.Event;

public class EventTest {

    @Before
    public void setup() {
        System.out.println("Stating test for Note");
    }

    @Test
    public void testCreateEvent() {
        Event event = new Event("Iteration 1 due", "2024-03-01", "16:00:00");

        assertEquals("Iteration 1 due", event.getTitle());
        assertEquals("2024-03-01", event.getNewDate());
    }

    @Test
    public void testEventToString(){
        Event event = new Event("Iteration 1 due", "2024-03-01", "16:00:00");
        assertEquals("Event: { " +
                "Title = " + event.getTitle() +
                "\nDate = " + event.getNewDate() +
                "\nTime = " + event.getTime() +
                "\n}",event.toString());
    }

    @Test
    public void testGetTitle(){
        Event event = new Event("Iteration 1 due", "2024-03-01", "16:00:00");
        assertEquals("Iteration 1 due",event.getTitle());
    }

    @Test
    public void testNewDate(){
        Event event = new Event("Iteration 1 due", "2024-03-01", "16:00:00");
        assertEquals("2024-03-01",event.getNewDate());
    }

    @Test
    public void testTime(){
        Event event = new Event("Iteration 1 due", "2024-03-01", "16:00:00");
        assertEquals("16:00:00",event.getTime());
    }

}
