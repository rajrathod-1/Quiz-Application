package comp3350.srsys.persistence.stubs;

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
        eventList.add(new Event(1,"Event1","2023-08-13","12:00:01"));

        // Event 2
        eventList.add(new Event(2,"Event2","2023-08-14","12:00:02"));

        // Event 3
        eventList.add(new Event(3,"Event3","2023-08-15","12:00:03"));

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
            if(eventList.get(i).getId() == id) {
                index = i;
            }
        }

        if(index >= 0) {
            eventList.remove(index);
        }
    }
}

