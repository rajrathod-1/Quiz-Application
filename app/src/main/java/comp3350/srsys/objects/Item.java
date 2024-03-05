package comp3350.srsys.objects;

import java.util.Date;

public abstract class Item {
    protected int id;
    protected Date date;

    public Item() {
        this.id = -1;
        this.date = new Date();     // Current date
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void updateDate() {
        this.date = new Date();
    }

    public String toString() {
        return "ID = " + this.id +
                "Date = " + this.date.toString();
    }

}