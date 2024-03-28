package comp3350.srsys.persistence.stubs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import comp3350.srsys.objects.Course;
import comp3350.srsys.persistence.ICoursePersistence;

public class CoursePersistenceStub implements ICoursePersistence {
    private List<Course> courses;

    public CoursePersistenceStub() {
        this.courses = new ArrayList<>();

        courses.add(new Course("COMP", 2160, "Programming Practices",1,24,2024,4,10,2024, 3.5,3.0));
        courses.add(new Course("COMP", 3020,"Human-Computer Interaction",1,7,2024,4,10,2024, 3.5,3.0));
        courses.add(new Course("COMP", 3350,"Software Engineering I",2,1,2024,4,10,2024, 3.5,3.0));
        courses.add(new Course("ECE", 4830,"Signal Processing 2",2,8,2024,4,10,2024, 3.5,3.0));
        courses.add(new Course("BIOL", 3420,"Biology 1",2,7,2024,4,10,2024,3.5,3.0));
        courses.add(new Course("AGRI", 3500,"Economics of Agriculture 1",2,7,2024,4,10,2024,3.5,3.0));
    }
    @Override
    public List<Course> getCourseSequential() {
        return courses;
    }

    @Override
    public Course insertCourse(Course currentCourse) {
        // don't bother checking for duplicates
        courses.add(currentCourse);
        return currentCourse;
    }

    @Override
    public void deleteCourse(Course currentCourse) {
        int index;

        index = courses.indexOf(currentCourse);
        if (index >= 0)
        {
            courses.remove(index);
        }
    }

    @Override
    public void changeGPA(Course currentCourse, double newGPA) {
        int index;

        index = courses.indexOf(currentCourse);
        if (index >= 0)
        {
            courses.get(index).setGPA(newGPA);
        }
    }
}
