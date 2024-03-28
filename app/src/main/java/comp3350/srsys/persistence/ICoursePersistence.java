package comp3350.srsys.persistence;

import java.math.BigDecimal;
import java.util.List;

import comp3350.srsys.objects.Course;

public interface ICoursePersistence {

    List<Course> getCourseSequential();

    Course insertCourse(Course currentCourse);

    void deleteCourse(Course currentCourse);

    void changeGPA(Course currentCourse, double newGPA);
}
