package comp3350.srsys.persistence.stubs;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import comp3350.srsys.objects.Event;
import comp3350.srsys.persistence.IEventPersistence;

public class EventPersistenceStub implements IEventPersistence {

    private final List<Event> eventList;

    public EventPersistenceStub() {

        this.eventList = new ArrayList<>();

        Date date = new Date();
        Time time = new Time(6, 30, 0);

        // Event 1
        eventList.add(new Event(
                1,
                date,
                "Iteration 1 due",
                date,
                new Time(16, 30, 0)
        ));


        // Event 2
        eventList.add(new Event(
                2,
                date,
                "Winter break start",
                date,
                new Time(0, 0, 0)
        ));

        // Event 3
        eventList.add(new Event(
                3,
                date,
                "Winter break ends",
                date,
                new Time(23, 59, 59)
        ));

    }

    @Override
    public List<Event> getEventList() {
        return Collections.unmodifiableList(eventList);
    }

    @Override
    public Event insertEvent(Event event) {
        Event result = null;
        if(!eventList.contains(event)) {
            eventList.add(event);
            result = event;
        }
        return result;
    }

    @Override
    public void deleteEvent(Event event) {

        if(eventList.contains(event)) {
            eventList.remove(event);
        }
    }
}

