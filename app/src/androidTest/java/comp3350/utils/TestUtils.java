package comp3350.utils;

import comp3350.srsys.business.AccessCourses;

import java.util.List;

import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.business.AccessNotes;
import comp3350.srsys.business.AccessQuizzes;
import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Note;
import comp3350.srsys.objects.Quiz;

public class TestUtils {
    private AccessCourses courseHandler;
    private AccessQuizzes quizHandler;
    private AccessNotes notesHandler;

    public TestUtils() {
        courseHandler = new AccessCourses();
        quizHandler = new AccessQuizzes();
        notesHandler = new AccessNotes();
    }

    public List<Course> getCourses(){
        return courseHandler.getCourses();
    }

    public Course getSequential()
    {
        return courseHandler.getSequential();
    }

    public Course insertCourse(Course currentCourse)
    {
        return courseHandler.insertCourse(currentCourse);
    }

    public void deleteCourse(Course currentCourse)
    {
        courseHandler.deleteCourse(currentCourse);
    }

    public List<Course> sortCoursesByDate() {return courseHandler.sortCoursesByDate();}

    public List<Course> sortCoursesBySubject() {return courseHandler.sortCoursesBySubject();}

    public List<Course> sortCoursesByName() {return courseHandler.sortCoursesByName();}

    public List<Course> sortCoursesByID() {return courseHandler.sortCoursesByID();}

    public List<Course> sortCoursesByFavorite() {return courseHandler.sortCoursesByFavorite();}

    public Quiz getNextQuizSequential(){
        return quizHandler.getNextQuizSequential();
    }

    public Note getNotesSequential(){
        return notesHandler.getNotesSequential();
    }
}
