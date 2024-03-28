package comp3350.srsys.objects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Course implements Serializable {
    private final String topic;
    private final int courseNum;
    private final String courseName;
    private final int startMonth;
    private final int startDate;
    private final int startYear;
    private final int endMonth;
    private final int endDate;
    private final int endYear;
    private boolean marked;
    private int notesCreated;
    private int quizCreated;
    private double creditHours;
    private double gpa;

    /*
           COURSE constructor
              - used when creating Course instance with data from database
     */
    public Course(final String newTopic, final int newNum, final String newName, final int newMonth,
                  final int newDate, final int newYear, final int lastMonth, final int lastDate,
                  final int lastYear, final boolean marked, final int notesCreated, final int quizCreated,
                  final double creditHours, final double gpa)
    {
        this(newTopic, newNum, newName, newMonth, newDate, newYear, lastMonth, lastDate, lastYear,gpa,creditHours);
        this.marked = marked;
        this.notesCreated = notesCreated;
        this.quizCreated = quizCreated;
        this.gpa = gpa;
    }

    public Course(final String newTopic, final int newNum, final String newName, final int newMonth,
                  final int newDate, final int newYear, final int lastMonth, final int lastDate,
                  final int lastYear,final double gpa,final double creditHours){
        topic = newTopic;
        courseNum = newNum;
        courseName = newName;
        startMonth = newMonth;
        startDate = newDate;
        startYear = newYear;
        endMonth = lastMonth;
        endDate = lastDate;
        endYear = lastYear;
        marked = false;
        notesCreated = 0;
        quizCreated = 0;
        this.creditHours = creditHours;
        this.gpa = gpa;
    }

    public Course(final String newTopic, final int newNum, final String newName, final int newMonth,
                  final int newDate, final int newYear, final int lastMonth, final int lastDate,
                  final int lastYear,final double creditHours){
        topic = newTopic;
        courseNum = newNum;
        courseName = newName;
        startMonth = newMonth;
        startDate = newDate;
        startYear = newYear;
        endMonth = lastMonth;
        endDate = lastDate;
        endYear = lastYear;
        marked = false;
        notesCreated = 0;
        quizCreated = 0;
        this.creditHours = creditHours;
        this.gpa = 0.0;
    }

    public Course(final String newTopic, final int newNum){
        topic = newTopic;
        courseNum = newNum;
        courseName = "";
        startMonth = 0;
        startDate = 0;
        startYear = 0;
        endMonth = 0;
        endDate = 0;
        endYear = 0;
        marked = false;
        notesCreated = 0;
        quizCreated = 0;
        this.creditHours = 3;
        this.gpa = 0.0;
    }

    public String getTopic(){
        return topic;
    }
    public int getCourseNum()
    {
        return courseNum;
    }

    public void favoriteCourse(){
        marked = true;
    }

    public void unfavoriteCourse(){
        marked = false;
    }

    public boolean getmarked(){
        return marked;
    }

    public String getCourseName()
    {
        return courseName;
    }

    public int getStartYear(){
        return startYear;
    }

    public int getStartMonth(){
        return startMonth;
    }

    public int getStartDate(){
        return startDate;
    }

    public int getEndYear() {
        return endYear;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public int getEndDate() {
        return endDate;
    }

    public int getNotesCreated(){
        return notesCreated;
    }

    public int getQuizCreated(){
        return quizCreated;
    }

    public String getStartDateString(){
        return startYear + "-"+ startMonth + "-" + startDate;
    }

    public String getEndDateString(){
        return endYear + "-"+ endMonth + "-" + endDate;
    }

    public double getCreditHours(){
        return this.creditHours;
    }
    public void setGPA(double inputtedGPA){
        this.gpa = inputtedGPA;
    }

    public double getGPA(){
        return this.gpa;
    }

    public String toString()
    {
        return String.format("Course: %s  %s: %s",topic, courseNum, courseName);
    }

    public int hashCode()
    {
        return Objects.hash(topic, courseNum);
    }

}
