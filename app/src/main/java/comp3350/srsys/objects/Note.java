package comp3350.srsys.objects;

import java.util.Date;
import java.util.List;

public class Note extends Card{

    private static int noteCount = 1;

    //Instance Variables
    private String title;
    private String content;

    /*
     *   Note Default Constructor
     */
    public Note() {
        super();
        this.content = null;
        this.title = null;
    }

    /*
     *  Note Constructor  - without Course relationship
     */
    public Note(String title, String content) {
        super();
        this.id = noteCount++;
        this.title = title;
        this.content = content;
    }

    /*
     *  Note Constructor - with Course relationship
     *      - used by the app when a new Note is created
     *      - sets the id to noteCount
     */
    public Note(String title, String content, String topic, int num) {
        super(topic, num);
        this.id = noteCount++;
        this.title = title;
        this.content = content;
    }

    /*
     *  Note Constructor - with Course relationship
     *      - used when DB creates Notes with data from database
     */
    public Note(int id, Date date, String title, String content, String topic, int num)
    {
        super(topic, num);
        this.id = id;
        this.date = date;
        this.title = title;
        this.content = content;
    }


    // Instance Variables
    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public void setContent(String newContent) {
        this.content = newContent;
    }

    // HELPERS

    @Override
    public String toString() {
        return "Note: { " +
                super.toString() +
                "\nTitle = " + this.title +
                "\nContent = " + this.content +
                "\n}";
    }

    public boolean equal(Object obj) {
        boolean result = false;
        if(obj instanceof Note) {
            Note aNote = (Note) obj;
            result = this.id == aNote.id;
        }
        return result;
    }

}
