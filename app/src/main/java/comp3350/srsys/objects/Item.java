package comp3350.srsys.objects;

public abstract class Item {
    protected int id;

    public Item() {
        this.id = -1;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "ID = " + this.id;
    }

}