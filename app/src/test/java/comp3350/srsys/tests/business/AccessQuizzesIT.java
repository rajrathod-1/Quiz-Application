package comp3350.srsys.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.business.AccessQuizzes;
import comp3350.srsys.business.exceptions.QuizNotFoundException;
import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Note;
import comp3350.srsys.objects.Quiz;
import comp3350.srsys.tests.utils.TestUtils;


public class AccessQuizzesIT {
    private AccessQuizzes accessQuizzes;
    private File tempDB;


    @Before
    public void setUp() throws IOException {
        Services.clean();
        System.out.println("Starting integration test for AccessQuizzes");
        this.tempDB = TestUtils.copyDB();
        this.accessQuizzes = new AccessQuizzes();

        assertNotNull(this.accessQuizzes);
    }

    @Test
    public void testGetQuizzes() {
        final List<Quiz> quizzes = accessQuizzes.getQuizzes();
        assertEquals(3,quizzes.size());

        System.out.println("Finished testGetCourses");
    }

    @Test
    public void testGetQuizzesSequential() {
        final Quiz quiz = accessQuizzes.getNextQuizSequential();
        assertNotNull("first sequential course should not be null", quiz);
        assertTrue("What is 1+1?".equals(quiz.getQuestion()));

        System.out.println("Finished testGetQuizzesSequential");
    }

    @Test
    public void testGetReverseSequential() {
        accessQuizzes.getNextQuizSequential();
        final Quiz quiz1 = accessQuizzes.getPrevQuizSequential();
        assertNotNull("first sequential course should not be null", quiz1);
        assertTrue("What is 1+1?".equals(quiz1.getQuestion()));

        System.out.println("Finished testGetReverseSequential");
    }

    @Test
    public void testInsertQuiz() {
        String question = "What is 100 + 100?";
        List<String> choices = new ArrayList<>();
        choices.add("100");     // 0
        choices.add("200");     // 1
        choices.add("300");     // 2
        choices.add("400");     // 3
        Quiz newQuiz = new Quiz(question, choices, 1);

        List<Quiz> courses1 = accessQuizzes.getQuizzes();
        assertEquals(3,courses1.size());
        accessQuizzes.insertQuiz(newQuiz);
        List<Quiz> courses2 = accessQuizzes.getQuizzes();
        assertEquals(4,courses2.size());

        System.out.println("Finished testInsertQuiz");
    }

    @Test
    public void testDeleteQuizById() {
        accessQuizzes.deleteQuizById(2);
        accessQuizzes.getNextQuizSequential();
        Quiz ourQuiz = accessQuizzes.getNextQuizSequential();
        assertEquals(2, accessQuizzes.getQuizzes().size());
        assertTrue("What is the first step in Software Development Lifecycle?".equals(ourQuiz.getQuestion()));

        System.out.println("Finished testDeleteQuizById");
    }
    @Test
    public void testDeleteQuiz() {
        final List<Quiz> initialQuizzes = accessQuizzes.getQuizzes();
        accessQuizzes.deleteQuiz(initialQuizzes.get(0));
        final List<Quiz> finalQuizzes = accessQuizzes.getQuizzes();
        assertNotNull("first sequential course should not be null", finalQuizzes);
        assertTrue("What is the PH of H20?".equals(finalQuizzes.get(0).getQuestion()));
        assertEquals(2,accessQuizzes.getQuizzes().size());

        System.out.println("Finished testGetQuizzesSequential");
    }

    @Test
    public void testUpdateQuiz() {
        Quiz firstQuiz = accessQuizzes.getNextQuizSequential();
        firstQuiz.setAnswer("TestAnswer");

        accessQuizzes.updateQuiz(firstQuiz);

        assertEquals(accessQuizzes.getQuizzes().get(0).getAnswer(),"TestAnswer");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();

        Services.clean();
    }
}
