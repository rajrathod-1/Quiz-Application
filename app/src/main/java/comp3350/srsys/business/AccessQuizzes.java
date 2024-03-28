package comp3350.srsys.business;

import java.util.Collections;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Quiz;
import comp3350.srsys.persistence.IQuizPersistence;
import comp3350.srsys.business.validators.QuizValidator;

public class AccessQuizzes {

    private IQuizPersistence dataAccess;
    private List<Quiz> quizzes;
    private Quiz quiz;
    private int currentQuiz;

    public AccessQuizzes() {
        this.initialize();
        dataAccess = Services.getQuizPersistence(null);
    }

    public AccessQuizzes(Course course) {
        this.initialize();
        dataAccess = Services.getQuizPersistence(course);
    }

    public AccessQuizzes(final IQuizPersistence quizPersistence) {
        this.dataAccess = quizPersistence;
    }

    public List<Quiz> getQuizzes() {
        quizzes = dataAccess.getQuizList();
        return Collections.unmodifiableList(quizzes);
    }

    /*
     *  Access the quizzes one at a time in sequential order.
     */
    public Quiz getNextQuizSequential() {
        if(quizzes == null) {
            quizzes = dataAccess.getQuizList();
            currentQuiz = 0;
        }
        else
        {
            currentQuiz++;
        }
        if(currentQuiz < quizzes.size()) {
            quiz = quizzes.get(currentQuiz);
        }
        else {
            this.initialize();
        }
        return quiz;
    }

    public Quiz getPrevQuizSequential() {
        if(quizzes == null) {
            quizzes = dataAccess.getQuizList();
            currentQuiz = 0;
        }
        if(currentQuiz > 0) {
            currentQuiz--;
            quiz = quizzes.get(currentQuiz);
        }
        return quiz;
    }

    public Quiz insertQuiz(Quiz currentQuiz) {
        Quiz result = null;
        if (currentQuiz != null){
            if (quizzes == null){
                quizzes = dataAccess.getQuizList();
            }
        }
        result = dataAccess.insertQuiz(currentQuiz);
        return result;
    }

    public Quiz updateQuiz(Quiz currentQuiz) {
        Quiz result = null;
        result = dataAccess.updateQuiz(currentQuiz);
        return result;
    }

    public void deleteQuiz(Quiz currentQuiz) {
        dataAccess.deleteQuiz(currentQuiz);
        quizzes.remove(currentQuiz);
    }

    public void deleteQuizById(int id) {
        if(id > 0) {
            dataAccess.deleteQuizById(id);
        }
    }

    private void initialize() {
        quizzes = null;
        quiz = null;
        currentQuiz = 0;
    }

    public void setCurrentQuiz(int position)
    {
        if(position >= 0 && position < quizzes.size())
        {
            currentQuiz = position;
        }
    }

    public boolean endOfQuizzes() {
        return currentQuiz == quizzes.size()-1;
    }

    public boolean startOfQuizzes() {
        return currentQuiz == 0;
    }
}
