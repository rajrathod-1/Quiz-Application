package comp3350.srsys.objects;

import java.util.Date;

public abstract class Card extends Item {

    protected Date date;
    protected String courseTopic;
    protected int courseNum;

    public Card() {
        super();
        courseTopic = null;
        this.courseNum = -1;
        this.date = new Date();
    }

    public Card(String topic, int num) {
        super();
        this.courseTopic = topic;
        this.courseNum = num;
        this.date = new Date();
    }

    public String getCourseTopic() {
        return courseTopic;
    }

    public int getCourseNum() {
        return courseNum;
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
