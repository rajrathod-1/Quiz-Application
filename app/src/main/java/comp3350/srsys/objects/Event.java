package comp3350.srsys.objects;

public class Event extends Item{

    private static int eventCount = 1;
    private String newDate;
    private String time;
    private String title;
    private int id;


    /*
     *  Event Constructor
     *      - Used to create Event from the app
     *      - set id to eventCount
     */


    public Event(int id, String title, String newDate, String time) {
        this.id = id;
        this.title = title;
        this.newDate = newDate;
        this.time = time;
    }
    /*
     *  Event Constructor
     *      - Used to create Event with data from database
     */


    @Override
    public String toString() {
        return "Event: { " +
                "ID = " + id +
                "\nTitle = " + title+
                "\nEvent Date = " + newDate +
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

    public int getId() {
        return this.id;
    }
}
