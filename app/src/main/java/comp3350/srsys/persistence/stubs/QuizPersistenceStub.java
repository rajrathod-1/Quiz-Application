package comp3350.srsys.persistence.stubs;

import android.nfc.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Objects;

import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Quiz;
import comp3350.srsys.persistence.IQuizPersistence;

public class QuizPersistenceStub implements IQuizPersistence {

    private List<Quiz> quizList;

    public QuizPersistenceStub() {

        this.quizList = new ArrayList<>();

        Date date = new Date();

        // QUIZ 1
        String question1 = "What is 1+1?";
        List<String> choices1 = new ArrayList<>();
        choices1.add("1");
        choices1.add("2");
        choices1.add("3");
        choices1.add("4");
        Quiz quiz1 = new Quiz(1, date, question1, choices1, 1, "MC", "COMP", 3350);

        // QUIZ 2
        String question2 = "What is the PH of H20?";
        List<String> choices2 = new ArrayList<>();
        choices2.add("5");
        choices2.add("6");
        choices2.add("7");
        choices2.add("8");
        Quiz quiz2 = new Quiz(2, date, question2, choices2, 2, "MC", "COMP", 3350);

        // QUIZ 3
        String question3 = "What is the first step in Software Development Lifecycle?";
        List<String> choices3 = new ArrayList<>();
        choices3.add("Implementation");
        choices3.add("Maintenance");
        choices3.add("Analysis / Requirements");
        choices3.add("Design");
        Quiz quiz3 = new Quiz(3, date, question3, choices3, 3, "MC", "COMP", 3350);

        // QUIZ 4
//        String question4 = "Which one is correct in Java?"; saving this for multiple choice
        String question4 = "How do you print \"Hello, World\" in Java?";
        List<String> choices4 = new ArrayList<>();
        choices4.add("print('Hello, World')");
        choices4.add("printf('Hello, World')");
        choices4.add("System.out.println('Hello, World')");
        choices4.add("cout << 'Hello, World' << endl");
        Quiz quiz4 = new Quiz(4, date, question4, choices4, 2, "MC", "COMP", 3350);

        // ADD QUIZZES TO THE LIST
        quizList.add(quiz1);
        quizList.add(quiz2);
        quizList.add(quiz3);
        quizList.add(quiz4);
    }


    @Override
    public List<Quiz> getQuizList() {return quizList;}

    // Returns null if failed to insert
    @Override
    public Quiz insertQuiz(Quiz quiz) {
        Quiz result = null;
        if(!quizList.contains(quiz)) {
            quizList.add(quiz);
            result = quiz;
        }
        return result;
    }

    // Returns null if failed to update
    @Override
    public Quiz updateQuiz(Quiz newQuiz) {
        Quiz result = null;
        int index = quizList.indexOf(newQuiz);

        if(index >= 0) {
            quizList.set(index, newQuiz);
            result = newQuiz;
        }

        return result;
    }

    @Override
    public void deleteQuiz(Quiz quiz) {

        if(quizList.contains(quiz)) {
            quizList.remove(quiz);
        }
    }

    @Override
    public void deleteQuizById(int id) {

        int index = -1;
        for(int i=0; i<quizList.size(); i++) {
            if(quizList.get(i).getId() == id) {
                index = i;
            }
        }

        if(index >= 0) {
            quizList.remove(index);
        }
    }

    //the below function is used to delete a specific quiz from a course.
    //This function is utilized for hsqldb database connections to change the presently presented quizzes
    //after a quiz has been deleted. As such it is used in database connection and cannot be tested with a unit test.
    @Override
    public void deleteQuizzesByCourse(Course course) {
        for (Quiz quiz : quizList) {
            if (quiz.getCourseNum() == course.getCourseNum() &&
                    Objects.equals(quiz.getCourseTopic(), course.getTopic())) {
                quizList.remove(quiz);
            }
        }
    }
}
