package comp3350.srsys.objects;

import java.util.Date;

public class Event extends Item{
    private final String newDate;
    private final String time;
    private final String title;

    /*
     *  Event Constructor
     *      - Used to create Event from the app
     *      - set id to eventCount
     */

    public Event(String title, String newDate, String time) {
        this.title = title;
        this.newDate = newDate;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Event: { " +
                "Title = " + title +
                "\nDate = " +  newDate +
                "\nTime = " + time +
                "\n}";
    }

    public String getTitle() {
        return this.title;
    }

    public String getNewDate() {
        return this.newDate;
    }

    public String getTime() {
        return this.time;
    }
}
