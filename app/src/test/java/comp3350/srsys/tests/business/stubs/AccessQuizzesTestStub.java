package comp3350.srsys.tests.business.stubs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.AccessQuizzes;
import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Quiz;
import comp3350.srsys.persistence.stubs.CoursePersistenceStub;
import comp3350.srsys.persistence.stubs.QuizPersistenceStub;

public class AccessQuizzesTestStub {

    private AccessQuizzes accessQuizzes;
    private Quiz quiz;
    private int expectedQuizId;
    private int expectedSize;

    @Before
    public void setUp() {
        this.accessQuizzes = new AccessQuizzes(new QuizPersistenceStub());
        this.expectedQuizId = 1;
        this.expectedSize = 4;
        System.out.println("Starting test for AccessQuizzes");
    }

    @Test
    public void testGetQuizzesSequential() {
        System.out.println("Starting testGetQuizzesSequential");

        quiz = accessQuizzes.getNextQuizSequential();   // quiz 2

        assertNotNull(quiz);
        assertEquals(1, quiz.getId());

        quiz = accessQuizzes.getNextQuizSequential();        // quiz 3

        assertNotNull(quiz);
        assertEquals(2, quiz.getId());

        System.out.println("Finished testGetQuizzesSequential");
    }

    @Test
    public void testGetQuizzesSequentialToEnd() {
        System.out.println("Starting testGetQuizzesSequentialToEnd");

        quiz = accessQuizzes.getNextQuizSequential();        // quiz 1
        assertNotNull(quiz);
        assertEquals(1, quiz.getId());
        quiz = accessQuizzes.getNextQuizSequential();        // quiz 2
        assertNotNull(quiz);
        assertEquals(2, quiz.getId());
        quiz = accessQuizzes.getNextQuizSequential();        // quiz 3
        assertNotNull(quiz);
        assertEquals(3, quiz.getId());
        quiz = accessQuizzes.getNextQuizSequential();        // quiz 4
        assertNotNull(quiz);
        assertEquals(4, quiz.getId());
        quiz = accessQuizzes.getNextQuizSequential();        // quiz 5

        assertNull(quiz);

        System.out.println("Finished testGetQuizzesSequentialToEnd");
    }

    @Test
    public void nullGetPrevQuizSequential(){
        System.out.println("Starting testGetReverseSequential");

        accessQuizzes.getPrevQuizSequential();
        accessQuizzes.getNextQuizSequential();        // quiz 2
        quiz = accessQuizzes.getPrevQuizSequential();        // quiz 1

        assertNotNull(quiz);
        assertEquals(expectedQuizId, quiz.getId());

        System.out.println("Finished testGetReverseSequential");
    }

    @Test
    public void testGetReverseSequential() {
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
        System.out.println("Starting testDeleteQuiz");

        expectedSize = accessQuizzes.getQuizzes().size();
        quiz = accessQuizzes.getNextQuizSequential();
        accessQuizzes.deleteQuiz(quiz);

        assertEquals(--expectedSize, accessQuizzes.getQuizzes().size());

        System.out.println("Finished testDeleteQuiz");
    }

    @Test
    public void testDeleteQuizById() {
        System.out.println("Starting testDeleteQuizById");

        quiz = accessQuizzes.getNextQuizSequential();
        accessQuizzes.deleteQuizById(quiz.getId());

        assertEquals(--expectedSize, accessQuizzes.getQuizzes().size());

        System.out.println("Finished testDeleteQuizById");
    }

    @Test
    public void testUpdateQuiz() {
        System.out.println("Starting testUpdateQuiz");

        String question = "What is 100 + 100?";
        List<String> choices = new ArrayList<>();
        choices.add("100");     // 0
        choices.add("201");     // 1
        choices.add("300");     // 2
        choices.add("400");     // 3
        Quiz newQuiz = new Quiz(question, choices, 1);
        accessQuizzes.insertQuiz(newQuiz);
        newQuiz.setAnswer("200");
        accessQuizzes.updateQuiz(newQuiz);

        assertEquals("200",accessQuizzes.getQuizzes().get(4).getAnswer());

        System.out.println("Finished testUpdateQuiz");
    }

    @Test
    public void testEndOfQuizzes() {
        System.out.println("Starting testEndOfQuizzes");

        accessQuizzes.getNextQuizSequential();
        accessQuizzes.getNextQuizSequential();
        accessQuizzes.getNextQuizSequential();
        accessQuizzes.getNextQuizSequential();
        assertTrue(accessQuizzes.endOfQuizzes());

        System.out.println("Finished testEndOfQuizzes");
    }

    @Test
    public void testDeleteQuizByCourse() {
        System.out.println("Starting testEndOfQuizzes");

        accessQuizzes.getNextQuizSequential();
        accessQuizzes.getNextQuizSequential();
        accessQuizzes.getNextQuizSequential();
        accessQuizzes.getNextQuizSequential();
        assertTrue(accessQuizzes.endOfQuizzes());

        System.out.println("Finished testEndOfQuizzes");
    }

    @Test
    public void testStartOfQuizzes() {
        System.out.println("Starting testStartOfQuizzes");

        accessQuizzes.getNextQuizSequential();
        assertTrue(accessQuizzes.startOfQuizzes());

        System.out.println("Finished testStartOfQuizzes");
    }

    @Test
    public void testSetCurrentQuiz() {
        System.out.println("Starting testSetCurrentQuiz");

        accessQuizzes.getNextQuizSequential();
        assertTrue(accessQuizzes.startOfQuizzes());
        accessQuizzes.getNextQuizSequential();

        accessQuizzes.setCurrentQuiz(0);
        assertTrue(accessQuizzes.startOfQuizzes());
    }

    @After
    public void tearDown() {
        Services.clean();
    }
}
