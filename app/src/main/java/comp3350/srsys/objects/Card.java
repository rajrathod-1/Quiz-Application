package comp3350.srsys.objects;

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


    public void addCardTag(Tag newTag) {
        if(!this.containsTagByName(newTag.getName())) {
            cardTagList.add(newTag);
        }
    }

    public void removeCardTag(Tag removeTag) {
        if(this.containsTag(removeTag)) {
            cardTagList.remove(indexOf(removeTag));
        }
    }


    public String toString() {
        return super.toString() +
                "Tag List = " + this.cardTagList.toString();
    }

    public boolean containsTag(Tag tag) {
        return this.containsTagByName(tag.getName());
    }

    private int indexOf(Tag tag) {
        int index = -1;

        for(int i=0; i<cardTagList.size(); i++) {
            if(cardTagList.get(i).equal(tag)) {
                index = i;
            }
        }

        return index;
    }

    private boolean containsTagByName(String name) {
        boolean result = false;
        for(int i=0; i<cardTagList.size(); i++) {
            if(cardTagList.get(i).getName().equals(name)) {
                result = true;
            }
        }
        return result;
    }
}
