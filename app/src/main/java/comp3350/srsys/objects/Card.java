package comp3350.srsys.objects;

import java.util.Date;

public abstract class Card extends Item {

    protected Date date;

    public Card() {
        super();
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void updateDate() {
        this.date = new Date();
    }

    public String toString() {
        return super.toString();
    }
}
