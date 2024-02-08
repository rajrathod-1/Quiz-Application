package comp3350.srsys.business;

import java.util.Collections;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.objects.Quiz;
import comp3350.srsys.persistence.IQuizPersistence;
import comp3350.srsys.business.validators.QuizValidator;

public class AccessQuizzes {

    private IQuizPersistence dataAccess;
    private List<Quiz> quizzes;
    private Quiz quiz;
    private int currentQuiz;

    public AccessQuizzes() {
        dataAccess = Services.getQuizPersistence();
        quizzes = dataAccess.getQuizList();
        quiz = null;
        currentQuiz = 0;
    }


    public List<Quiz> getQuizzes() {
        return quizzes;
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
        if(currentQuiz < quizzes.size()){
            quiz = quizzes.get(currentQuiz);
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
        if(QuizValidator.validate(currentQuiz)) {
            result = dataAccess.insertQuiz(currentQuiz);
        }
        return result;
    }


    public void deleteQuiz(Quiz currentQuiz) {
        quizzes.remove(currentQuiz);
        dataAccess.deleteQuiz(currentQuiz);
    }

    public void deleteQuizById(int id) {
        if(id > 0) {
            dataAccess.deleteQuizById(id);
        }
    }

    public boolean endOfQuizzes() {return currentQuiz == quizzes.size()-1;}

    public boolean startOfQuizzes() {return currentQuiz == 0;}
}
