package comp3350.srsys.objects;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.Objects;

public class Tag{
    private static int counter = -1;

    private String tagName;
    private int tagId;

    /*
     *  Tag Default Constructor
     */
    public Tag(final int tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public Tag(String name) {
        this.tagId = setTagID();
        this.tagName = name;
    }

    private int setTagID() {
        counter++;
        return counter;
    }

    public int getTagId() {
        return tagId;
    }

    public String getName() {
        return tagName;
    }

    @NonNull
    @Override
    public String toString() {
        return "Tag: {" +
                "Tag Name = " + this.tagName +
                "}\n";
    }


    public boolean equal(Object obj) {
        boolean result = false;
        if(obj instanceof Tag) {
            Tag newTag = (Tag) obj;
            result = this.getName().equals(newTag.getName());
        }
        return result;
    }
}
