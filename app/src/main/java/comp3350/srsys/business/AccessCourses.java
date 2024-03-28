package comp3350.srsys.business;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.objects.Course;
import comp3350.srsys.persistence.ICoursePersistence;

public class AccessCourses {

    private ICoursePersistence coursePersistence;
    private List<Course> courses;
    private Course course;
    private int currentCourse;


    public AccessCourses()
    {
        coursePersistence = Services.getCoursePersistence();
    }

    public AccessCourses(final ICoursePersistence coursePersistence) {
        this.coursePersistence = coursePersistence;
    }

    public List<Course> getCourses(){
        List<Course> courses = coursePersistence.getCourseSequential();
        return courses;
    }

    public Course getSequential()
    {
        String result = null;
        if (courses == null)
        {
            courses = coursePersistence.getCourseSequential();
            currentCourse = 0;
        }
        if (currentCourse < courses.size())
        {
            course = (Course) courses.get(currentCourse);
            currentCourse++;
        }
        else
        {
            courses = null;
            course = null;
            currentCourse = 0;
        }
        return course;
    }

    public Course insertCourse(Course currentCourse)
    {
        return coursePersistence.insertCourse(currentCourse);
    }

    public void deleteCourse(Course currentCourse)
    {
        coursePersistence.deleteCourse(currentCourse);
    }

    public void changeGPA(Course currentCourse, double gpa){
        coursePersistence.changeGPA(currentCourse, gpa);
    }

    public double getTermGPA(){
        List<Course> courses = getCourses();
        double termGPA = 0;
        float creditHours = 0;
        if (courses != null){
            for (int i = 0; i < courses.size(); i++){
                termGPA += courses.get(i).getGPA()*courses.get(i).getCreditHours();
                creditHours += courses.get(i).getCreditHours();
            }
            termGPA = termGPA/creditHours;
        }
        else{
            termGPA = 0.0;
        }

        return Math.round(termGPA * 100.0) / 100.0;
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
                return Integer.compare(course1.getCourseNum(), course2.getCourseNum());
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
                        returnValue = Integer.compare(course1.getStartDate(), course2.getStartDate());
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
