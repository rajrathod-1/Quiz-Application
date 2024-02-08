package comp3350.srsys.persistence;

import java.util.List;

import comp3350.srsys.objects.Quiz;

public interface IQuizPersistence {

    List<Quiz> getQuizList();

    Quiz insertQuiz(final Quiz quiz);

    void deleteQuiz(final Quiz quiz);

    void deleteQuizById(final int id);

}
