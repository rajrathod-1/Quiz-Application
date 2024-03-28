package comp3350.srsys.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.srsys.objects.Course;
import comp3350.srsys.persistence.ICoursePersistence;
import comp3350.srsys.persistence.INotePersistence;
import comp3350.srsys.persistence.IQuizPersistence;

public class CoursePersistenceHSQLDB implements ICoursePersistence {

    private final String dbPath;
    private List<Course> courses;

    public CoursePersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
        this.courses = new ArrayList<>();
        loadCourses();
    }

    @Override
    public List<Course> getCourseSequential() {
        return courses;
    }

    @Override
    public Course insertCourse(Course course) {
        System.out.println("[LOG] Inserting Course " + course.toString());

        try (final Connection con = connect()) {
            String query = "INSERT INTO COURSE VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, course.getTopic());
            ps.setInt(2, course.getCourseNum());
            ps.setString(3, course.getCourseName());
            ps.setInt(4, course.getStartMonth());
            ps.setInt(5, course.getStartDate());
            ps.setInt(6, course.getStartYear());
            ps.setInt(7, course.getEndMonth());
            ps.setInt(8, course.getEndDate());
            ps.setInt(9, course.getEndYear());
            ps.setBoolean(10, course.getmarked());
            ps.setInt(11, course.getNotesCreated());
            ps.setInt(12, course.getQuizCreated());
            ps.setDouble(13, course.getGPA());
            ps.setDouble(14, course.getCreditHours());

            ps.executeUpdate();
            ps.close();

            courses.add(course);

            return course;

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteCourse(Course course) {
        System.out.println("[LOG] Deleting Course " + course.toString());

        try (final Connection con = connect()) {
            // Deleting the quizzes and notes first due to the FOREIGN KEY CONSTRAINTS placed
            // on the NOTE and QUIZ table in the database.

            // Delete Notes and Quizzes that belong to this course
            IQuizPersistence quizPersistence = new QuizPersistenceHSQLDB(this.dbPath);
            quizPersistence.deleteQuizzesByCourse(course);

            INotePersistence notePersistence = new NotePersistenceHSQLDB(this.dbPath);
            notePersistence.deleteNotesByCourse(course);


            // Delete the Course
            String query = "DELETE FROM COURSE WHERE courseTopic = ? AND courseNum = ?";
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, course.getTopic());
            ps.setInt(2, course.getCourseNum());

            ps.executeUpdate();
            ps.close();

            courses.remove(course);

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void changeGPA(Course currentCourse, double newGPA){
        System.out.println("[LOG] Adding GPA =  " + newGPA + " to " + currentCourse.toString());

        try (final Connection con = connect()) {
            String query = "UPDATE COURSE SET currentGpa = ? WHERE courseTopic = ? AND courseNum = ?";
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, (int) (newGPA*100));
            ps.setString(2, currentCourse.getTopic());
            ps.setInt(3, currentCourse.getCourseNum());

            ps.executeUpdate();
            ps.close();

            int index = courses.indexOf(currentCourse);
            courses.get(index).setGPA(newGPA);

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }


    /*
     *    PRIVATE METHODS
     */

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Course fromResultSet(final ResultSet rs) throws SQLException {
        final String topic = rs.getString("courseTopic");
        final int courseNum = Integer.parseInt(rs.getString("courseNum"));
        final String courseName = rs.getString("courseName");
        final int startMonth = Integer.parseInt(rs.getString("startMonth"));
        final int startDate = Integer.parseInt(rs.getString("startDate"));
        final int startYear = Integer.parseInt(rs.getString("startYear"));
        final int endMonth = Integer.parseInt(rs.getString("endMonth"));
        final int endDate = Integer.parseInt(rs.getString("endDate"));
        final int endYear = Integer.parseInt(rs.getString("endYear"));
        final boolean marked = rs.getBoolean("marked");
        final int notesCreated = Integer.parseInt(rs.getString("notesCreated"));
        final int quizCreated = Integer.parseInt(rs.getString("quizCreated"));
        final int currentGPA = Integer.parseInt(rs.getString("currentGpa"));

        final int creditHours = Integer.parseInt(rs.getString("creditHour"));

        return new Course(topic, courseNum, courseName, startMonth, startDate,startYear,
                endMonth, endDate,endYear, marked, notesCreated, quizCreated,(double) creditHours/100, (double) currentGPA /100);
    }

    private void loadCourses() {
        try (Connection con = connect()) {
            String query = "SELECT * FROM COURSE";
            final Statement statement = con.createStatement();
            final ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                final Course course = fromResultSet(rs);
                this.courses.add(course);
            }

            statement.close();
            rs.close();

        } catch (final SQLException e) {
            System.out.println("FAILED to load Courses");
            throw new PersistenceException(e);
        }
    }
}
