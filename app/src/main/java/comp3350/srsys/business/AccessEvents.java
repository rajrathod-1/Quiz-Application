package comp3350.srsys.business;

import java.util.Collections;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.validators.EventValidator;
import comp3350.srsys.objects.Event;
import comp3350.srsys.persistence.IEventPersistence;

public class AccessEvents {

    private IEventPersistence dataAccess;

    public AccessEvents()
    {
        dataAccess = Services.getEventPersistence();
    }

    public AccessEvents(final IEventPersistence eventPersistence) {
        this.dataAccess = eventPersistence;
    }

    public List<Event> getEvents(){
        List<Event> events = dataAccess.getEventList();
        return Collections.unmodifiableList(events);
    }

    public Event insertEvent(Event event) {
        Event result = null;
        dataAccess.insertEvent(event);
        result = event;
        return result;
    }

    public void deleteEvent(Event event) {
        dataAccess.deleteEvent(event);
    }

    public void deleteEventById(int id) {
        dataAccess.deleteEventById(id);
    }

}
