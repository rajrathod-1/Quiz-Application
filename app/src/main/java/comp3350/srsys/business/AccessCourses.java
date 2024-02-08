package comp3350.srsys.business;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.objects.Course;
import comp3350.srsys.persistence.ICoursePersistence;
import comp3350.srsys.persistence.ICoursePersistence;

public class AccessCourses {

    private ICoursePersistence coursePersistence;

    public AccessCourses()
    {
        coursePersistence = Services.getCoursePersistence();
    }

    public List<Course> getCourses(){
        List<Course> courses = coursePersistence.getCourseSequential();
        return courses;
    }

    public Course insertCourse(Course currentCourse)
    {
        return coursePersistence.insertCourse(currentCourse);
    }

    public void deleteCourse(Course currentCourse)
    {
        coursePersistence.deleteCourse(currentCourse);
    }

    public List<Course> sortCoursesByFavorite(){
        List<Course> courses = coursePersistence.getCourseSequential();
        courses.sort(new Comparator<Course>() {
            @Override
            public int compare(Course course1, Course course2) {
                int returnValue;
                if (course1.getmarked()){
                    returnValue = -1;
                }
                else if (course2.getmarked()){
                    returnValue = 1;
                }
                else {
                    returnValue = 0;
                }

                return returnValue;
            }
        });
        return courses;
    }

    public List<Course> sortCoursesByID(){
        List<Course> courses = coursePersistence.getCourseSequential();
        courses.sort(new Comparator<Course>() {
            @Override
            public int compare(Course course1, Course course2) {
                return Integer.compare(course1.getCourseID(), course2.getCourseID());
            }
        });
        return courses;
    }

    public List<Course> sortCoursesByName(){
        List<Course> courses = coursePersistence.getCourseSequential();
        courses.sort(new Comparator<Course>() {
            @Override
            public int compare(Course course1, Course course2) {
                return course1.getCourseName().compareToIgnoreCase(course2.getCourseName());
            }
        });
        return courses;
    }

    public List<Course> sortCoursesBySubject(){
        List<Course> courses = coursePersistence.getCourseSequential();
        courses.sort(new Comparator<Course>() {
            @Override
            public int compare(Course course1, Course course2) {
                return course1.getTopic().compareToIgnoreCase(course2.getTopic());
            }
        });
        return courses;
    }

    public List<Course> sortCoursesByDate(){
        List<Course> courses = coursePersistence.getCourseSequential();
        courses.sort(new Comparator<Course>() {
            @Override
            public int compare(Course course1, Course course2) {
                int returnValue;
                if (course1.getStartYear() == course2.getStartYear()){
                    if (course1.getStartMonth() == course2.getStartMonth()){
                        returnValue = Integer.compare(course1.getStartDay(), course2.getStartDay());
                    }
                    else{
                        returnValue = Integer.compare(course1.getStartMonth(), course2.getStartMonth());
                    }
                }
                else{
                    returnValue = Integer.compare(course1.getStartYear(), course2.getStartYear());
                }
                return returnValue;
            }
        });
        return courses;
    }
}
