package comp3350.srsys.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import comp3350.srsys.business.exceptions.NoteNotFoundException;
import comp3350.srsys.business.exceptions.QuizNotFoundException;
import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Quiz;
import comp3350.srsys.persistence.IQuizPersistence;
import comp3350.srsys.persistence.utils.DBHelper;

public class QuizPersistenceHSQLDB implements IQuizPersistence
{
    private final String url;
    private List<Quiz> quizzes;
    private Course course;

    public QuizPersistenceHSQLDB(String dbPath) {
        this.url = "jdbc:hsqldb:file:" + dbPath + ";shutdown=true";
        quizzes = new ArrayList<>();
        course = null;
        loadAllQuizzes();
    }

    public QuizPersistenceHSQLDB(String dbPath, Course course) {
        this.url = "jdbc:hsqldb:file:" + dbPath + ";shutdown=true";
        quizzes = new ArrayList<>();
        this.course = course;
        loadQuizzesByCourse(course);
    }

    @Override
    public List<Quiz> getQuizList() {
        return quizzes;
    }

    @Override
    public Quiz insertQuiz(Quiz quiz) {
        System.out.println("[LOG] Inserting Quiz " + quiz.getQuestion());
        String dateString = DBHelper.getSQLDateString(quiz.getDate());

        List<String> choices = quiz.getChoices();
        String query;

        try (Connection con = connect()) {
            query = "INSERT INTO QUIZ VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, quiz.getQuestion());
            ps.setString(2, choices.get(0));
            ps.setString(3, choices.get(1));
            ps.setString(4, choices.get(2));
            ps.setString(5, choices.get(3));
            ps.setInt(6, quiz.getCorrectChoice());
            ps.setString(7, quiz.getQuizType());
            ps.setString(8, dateString);
            ps.setString(9, quiz.getCourseTopic());
            ps.setInt(10, quiz.getCourseNum());

            ps.executeUpdate();     //updating database
            ps.close();

            //reset quizzes
            this.quizzes = new ArrayList<>();
            loadQuizzes(this.course);

            return quiz;

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        System.out.println("[LOG] Updating Quiz");

        // get choices
        List<String> choices = quiz.getChoices();
        String c1 = choices.get(0);
        String c2 = choices.get(1);
        String c3 = choices.get(2);
        String c4 = choices.get(3);

        try (Connection con = connect()) {
            String query = "UPDATE QUIZ SET question=?, choice1=?, choice2=?, choice3=?, choice4=?, correctChoice=? WHERE id=?";
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, quiz.getQuestion());
            ps.setString(2, c1);
            ps.setString(3, c2);
            ps.setString(4, c3);
            ps.setString(5, c4);
            ps.setInt(6, quiz.getCorrectChoice());
            ps.setInt(7, quiz.getId());

            ps.executeUpdate();
            ps.close();

            //reset quizzes
            this.quizzes = new ArrayList<>();
            loadQuizzes(course);

            return quiz;

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
            throw new QuizNotFoundException("Quiz could not be found in the database");
        }
    }

    @Override
    public void deleteQuiz(Quiz quiz) {
        System.out.println("[LOG] Deleting quiz: " + quiz.getQuestion());
        deleteQuizById(quiz.getId());
    }

    @Override
    public void deleteQuizById(int id) throws QuizNotFoundException {
        System.out.println("[LOG] Deleting quiz by ID: " + id);

        String query;
        try (Connection con = connect()) {
            query = "DELETE FROM QUIZ WHERE id = ?";
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ps.executeUpdate();
            ps.close();

            for (Quiz quiz : quizzes) {
                if (quiz.getId() == id) {
                    quizzes.remove(quiz);
                    break;
                }
            }

        } catch (final SQLException error) {
            Log.e("Connect SQL", error.getMessage() + error.getSQLState());
            error.printStackTrace();
            throw new QuizNotFoundException("Quiz with id=" +id+ " could not be deleted in the database.");
        }
    }

    @Override
    public void deleteQuizzesByCourse(Course course) {
        System.out.println("[LOG] Deleting quizzes in course " + course.getCourseName());

        try (Connection con = connect()) {
            String query = "DELETE FROM QUIZ WHERE courseTopic=? AND courseNum=?";
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, course.getTopic());
            ps.setInt(2, course.getCourseNum());

            ps.executeUpdate();
            ps.close();

        } catch (final SQLException error) {
            Log.e("Connect SQL", error.getMessage() + error.getSQLState());
            error.printStackTrace();
        }
    }


    /*
     *      PRIVATE METHODS
     */

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(this.url, "SA", "");
    }

    /*
     *  @param: ResultSet from database
     *  @return: a Quiz instance created using ResultSet's data
     */
    private Quiz fromResultSet(final ResultSet rs) throws SQLException {
        int quizID = rs.getInt("id");
        String question = rs.getString("question");
        String choice1 = rs.getString("choice1");
        String choice2 = rs.getString("choice2");
        String choice3 = rs.getString("choice3");
        String choice4 = rs.getString("choice4");
        int correctChoice = rs.getInt("correctChoice");
        String quizType = rs.getString("quizType");
        Date date = rs.getTimestamp("date");
        String topic = rs.getString("courseTopic");
        int num = rs.getInt("courseNum");

        List<String> choices = new ArrayList<>();
        choices.add(choice1);
        choices.add(choice2);
        choices.add(choice3);
        choices.add(choice4);

        return new Quiz(quizID, date, question, choices, correctChoice, quizType, topic, num);
    }

    private void loadQuizzes(Course course) {
        if (course != null) {
            loadQuizzesByCourse(course);
        }
        else {
            loadAllQuizzes();
        }
    }

    private void loadAllQuizzes() {
        try (Connection con = connect()) {
            String query = "SELECT * FROM QUIZ";
            final Statement statement = con.createStatement();
            final ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                final Quiz quiz = fromResultSet(rs);
                this.quizzes.add(quiz);
            }

            statement.close();
            rs.close();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    private void loadQuizzesByCourse(Course course) {
        try (Connection con = connect()) {
            String query = "SELECT * FROM QUIZ WHERE courseTopic=? AND courseNum=?";
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, course.getTopic());
            ps.setInt(2, course.getCourseNum());

            final ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                final Quiz quiz = fromResultSet(rs);
                this.quizzes.add(quiz);
            }

            ps.close();
            rs.close();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }
}
