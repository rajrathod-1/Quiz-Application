package comp3350.srsys.persistence.stubs;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp3350.srsys.objects.Event;
import comp3350.srsys.persistence.IEventPersistence;

public class EventPersistenceStub implements IEventPersistence {

    private List<Event> eventList;

    public EventPersistenceStub() {

        this.eventList = new ArrayList<>();

        Date date = new Date();

        // Event 1
        eventList.add(new Event(
                "Iteration 0", "2024-01-23", "2024-01-24"
        ));

        // Event 2
        eventList.add(new Event(
                "Iteration 1", "2024-02-07", "2024-02-08"
        ));

        // Event 3
        eventList.add(new Event(
                "Iteration 2", "2024-02-27", "2024-02-28"
        ));

    }

    @Override
    public List<Event> getEventList() {
        return eventList;
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

    @Override
    public void deleteEventById(int id) {
        int index = -1;
        for(int i=0; i<eventList.size(); i++) {
            if(i == id) {
                index = i;
            }
        }

        if(index >= 0) {
            eventList.remove(index);
        }
    }
}

