package comp3350.srsys.persistence;

import java.util.List;

import comp3350.srsys.objects.Event;

public interface IEventPersistence {

    List<Event> getEventList();

    Event insertEvent(final Event event);

    void deleteEvent(final Event event);
}

