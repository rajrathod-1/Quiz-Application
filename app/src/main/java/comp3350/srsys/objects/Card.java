package comp3350.srsys.objects;

import android.nfc.Tag;

import java.util.ArrayList;
import java.util.List;

public abstract class Card extends Item {

    protected List<Tag> cardTagList;

    public Card() {
        super();
        this.cardTagList = new ArrayList<>();
    }

    public List<Tag> getCardTagList() {
        return cardTagList;
    }

    public String toString() {
        return super.toString() +
                "Tag List = " + this.cardTagList.toString();
    }
}
