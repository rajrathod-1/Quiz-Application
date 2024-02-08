package comp3350.srsys.objects;

import java.sql.Time;
import java.util.Date;

public class Event extends Item{

    private static int eventCount = 1;

    private String detail;
    private Date eventDate;
    private Time eventTime;

    /*
     *  Event Default Constructor
     */
    public Event() {
        super();
        this.detail = null;
        this.eventDate = null;
        this.eventTime = null;
    }

    /*
     *  Event Constructor
     *      - Used to create Event from the app
     *      - set id to eventCount
     */
    public Event(String detail, Date eventDate, Time eventTime) {
        super();
        this.id = eventCount++;
        this.detail = detail;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }

    /*
     *  Event Constructor
     *      - Used to create Event with data from database
     */
    public Event(int id, Date date, String detail, Date eventDate, Time eventTime) {
        super();
        this.id = id;
        this.date = date;
        this.detail = detail;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }


    public String getDetail() {
        return detail;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public Time getEventTime() {
        return eventTime;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setEventDate(Date date) {
        this.eventDate = date;
    }

    public void setEventTime(Time time) {
        this.eventTime = time;
    }

    @Override
    public String toString() {
        return "Event: { " +
                super.toString() +
                "\nDetail = " + this.detail +
                "\nEvent Date = " + this.eventDate.toString() +
                "\nEvent Time = " + this.eventTime.toString() +
                "\n}";
    }

    public boolean equal(Object obj) {
        boolean result = false;
        if(obj instanceof Event) {
            Event aEvent = (Event) obj;
            result = (this.detail == aEvent.detail) &&
                    (this.eventDate == aEvent.eventDate) &&
                    (this.eventTime == aEvent.eventTime);
        }
        return result;
    }
}
