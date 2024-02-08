package comp3350.srsys.tests.objects;

import static org.junit.Assert.assertEquals;
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
        Date eventDate = new Date(2024, 2, 8);
        Time eventTime = new Time(16, 30, 00);
        Event event = new Event("Iteration 1 due", eventDate, eventTime);

        assertEquals("Iteration 1 due", event.getDetail());
        assertEquals(new Date(2024, 2, 8), event.getEventDate());
        assertEquals(new Time(16, 30, 00), event.getEventTime());
    }

    @Test
    public void testNullCreateEvent() {
        Event event = new Event();

        assertNull(event.getDetail());
        assertNull(event.getEventDate());
        assertNull(event.getEventTime());
    }

    @Test
    public void testLongCreateEvent(){
        Date date = new Date(2024, 2, 8);
        Date eventDate = new Date(2024, 1, 7);
        Time eventTime = new Time(16, 30, 00);
        Event event = new Event(99, date, "Iteration 1 due", eventDate, eventTime);

        assertEquals(99,event.getId());
        assertEquals(date,event.getDate());
        assertEquals("Iteration 1 due", event.getDetail());
        assertEquals(new Date(2024, 1, 7), event.getEventDate());
        assertEquals(new Time(16, 30, 00), event.getEventTime());
    }

    @Test
    public void testSetDetail(){
        Date eventDate = new Date(2024, 1, 7);
        Time eventTime = new Time(16, 30, 00);
        Event event = new Event();

        event.setDetail("COMP 3350 Class");
        event.setEventDate(eventDate);
        event.setEventTime(eventTime);

        assertEquals("COMP 3350 Class",event.getDetail());
        assertEquals(eventDate,event.getEventDate());
        assertEquals(eventTime,event.getEventTime());
    }

    @Test
    public void testEventToString(){
        Date date = new Date(2024, 2, 8);
        Date eventDate = new Date(2024, 1, 7);
        Time eventTime = new Time(16, 30, 00);
        Event event = new Event(99, date, "Iteration 1 due", eventDate, eventTime);


        assertEquals("Event: { " +
                "ID = 99" +
                "Date = " + date.toString() +
                "\nDetail = " + "Iteration 1 due" +
                "\nEvent Date = " + eventDate.toString() +
                "\nEvent Time = " + eventTime.toString() +
                "\n}",event.toString());
    }

    @Test
    public void testEventEqual() {
        Date date = new Date(2024, 2, 8);
        Date eventDate = new Date(2024, 1, 7);
        Time eventTime = new Time(16, 30, 00);
        Event event1 = new Event(99, date, "Iteration 1 due", eventDate, eventTime);
        Event event2 = new Event(99, date, "Iteration 1 due", eventDate, eventTime);

        assertTrue(event1.equal(event2));
    }

    @Test
    public void testSetDate(){
        Event event = new Event();
        Date date = new Date(2024, 2, 8);

        event.setDate(date);
        assertEquals(date,event.getDate());

        event.updateDate();
        assertEquals(new Date(),event.getDate());
    }

}
