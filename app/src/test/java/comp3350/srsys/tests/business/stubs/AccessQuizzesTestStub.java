package comp3350.srsys.tests.business.stubs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.srsys.business.AccessQuizzes;
import comp3350.srsys.objects.Quiz;

public class AccessQuizzesTestStub {

    private AccessQuizzes accessQuizzes;
    private Quiz quiz;
    private int expectedQuizId;
    private int expectedSize;

    @Before
    public void setUp() {
        System.out.println("Starting test for AccessQuizzes");
    }

    @Test
    public void testGetQuizzesSequential() {
        this.accessQuizzes = new AccessQuizzes();

        System.out.println("Starting testGetQuizzesSequential");

        quiz = accessQuizzes.getNextQuizSequential();   // quiz 2

        assertNotNull(quiz);
        assertEquals(2, quiz.getId());

        quiz = accessQuizzes.getNextQuizSequential();        // quiz 3

        assertNotNull(quiz);
        assertEquals(3, quiz.getId());

        System.out.println("Finished testGetQuizzesSequential");
    }

    @Test
    public void testGetQuizzesSequentialToEnd() {
        initialize();
        System.out.println("Starting testGetQuizzesSequentialToEnd");

        quiz = accessQuizzes.getNextQuizSequential();        // quiz 1
        quiz = accessQuizzes.getNextQuizSequential();        // quiz 2
        quiz = accessQuizzes.getNextQuizSequential();        // quiz 3
        quiz = accessQuizzes.getNextQuizSequential();        // quiz 4
        quiz = accessQuizzes.getNextQuizSequential();        // quiz 5

        //expecting to be at the last entry
        expectedQuizId = 4;

        assertNotNull(quiz);
        assertEquals(expectedQuizId, quiz.getId());

        System.out.println("Finished testGetQuizzesSequentialToEnd");
    }

    @Test
    public void testGetReverseSequential() {
        initialize();
        System.out.println("Starting testGetReverseSequential");

        // move up once, then go back to first quiz

        quiz = accessQuizzes.getNextQuizSequential();        // quiz 2
        quiz = accessQuizzes.getPrevQuizSequential();        // quiz 1

        assertNotNull(quiz);
        assertEquals(expectedQuizId, quiz.getId());

        System.out.println("Finished testGetReverseSequential");
    }

    @Test
    public void testInsertQuiz() {
        initialize();
        System.out.println("Starting testInsertQuiz");

        String question = "What is 100 + 100?";
        List<String> choices = new ArrayList<>();
        choices.add("100");     // 0
        choices.add("200");     // 1
        choices.add("300");     // 2
        choices.add("400");     // 3

        expectedSize = accessQuizzes.getQuizzes().size();

        Quiz newQuiz = new Quiz(question, choices, 1);

        accessQuizzes.insertQuiz(newQuiz);

        assertEquals(++expectedSize, accessQuizzes.getQuizzes().size());

        System.out.println("Finished testInsertQuiz");
    }


    @Test
    public void testDeleteQuiz() {
        initialize();
        System.out.println("Starting testDeleteQuiz");

        expectedSize = accessQuizzes.getQuizzes().size();
        quiz = accessQuizzes.getNextQuizSequential();
        accessQuizzes.deleteQuiz(quiz);

        assertEquals(--expectedSize, accessQuizzes.getQuizzes().size());

        System.out.println("Finished testDeleteQuiz");
    }

    @Test
    public void testDeleteQuizById() {
        initialize();
        System.out.println("Starting testDeleteQuizById");

        quiz = accessQuizzes.getNextQuizSequential();
        accessQuizzes.deleteQuizById(quiz.getId());

        assertEquals(--expectedSize, accessQuizzes.getQuizzes().size());

        System.out.println("Finished testDeleteQuizById");
    }


    private void initialize() {
        this.accessQuizzes = new AccessQuizzes();
        this.expectedQuizId = 1;
        this.expectedSize = 4;
    }
}
