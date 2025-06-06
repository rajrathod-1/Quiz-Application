package comp3350.srsys.tests.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import comp3350.srsys.objects.Quiz;

public class QuizTest {

    private Quiz quiz;
    private List<String> choices;

    @Before
    public void setup() {
        System.out.println("Starting test for Quiz");
    }

    @Test
    public void testCreateQuiz() {
        System.out.println("\nStarting testCreateQuizWithoutTag");

        initialize();
        testHelper();

        assertEquals("What is 1+1?", quiz.getQuestion());
        assertEquals(2, quiz.getCorrectChoice());

        quiz.setQuizType("MC");

        assertEquals("MC", quiz.getQuizType());

        System.out.println("Finished testCreateQuizWithoutTag");
    }
    @Test
    public void testQuizSecondaryConstructor() {
        System.out.println("\nStarting testQuizSecondaryConstructor");
        List<String> choices = Arrays.asList("Choice1", "Choice2", "Choice3");
        Quiz quiz = new Quiz("What is 2+2?", choices, 1, "Math", "Addition", 101);

        assertEquals("What is 2+2?", quiz.getQuestion());
        assertEquals(choices, quiz.getChoices());
        assertEquals(1, quiz.getCorrectChoice());
        assertEquals("Math", quiz.getQuizType());
        assertEquals("Addition", quiz.getCourseTopic());
    }

    @Test
    public void testNullQuiz(){
        Quiz ourQuiz = new Quiz();

        assertNull(ourQuiz.getQuestion());
        assertEquals(-1,ourQuiz.getCorrectChoice());
        assertEquals(0,ourQuiz.getChoices().size());
    }

    @Test
    public void testGetChoices(){
        initialize();
        ArrayList ourList = new ArrayList<>();
        ourList.add("3");   // 0
        ourList.add("11");  // 1
        ourList.add("2");   // 2
        ourList.add("100"); // 3

        assertEquals(ourList,quiz.getChoices());
        assertEquals("2",quiz.getAnswer());
    }

    @Test
    public void setQuizContent(){
        Quiz quiz = new Quiz();
        String question = "What is the airspeed velocity of an unladed sparrow?";
        quiz.setQuestion(question);

        List<String> choices = new ArrayList<>();
        choices.add("African or European?");
        choices.add("40m/s");

        quiz.setChoices(choices);

        quiz.setCorrectChoice(0);

        assertEquals(question,quiz.getQuestion());
        assertEquals(choices,quiz.getChoices());
        assertEquals(0,quiz.getCorrectChoice());
    }

    @Test
    public void quizEqualsTest(){
        Quiz quiz1 = new Quiz();
        Quiz quiz2 = new Quiz();
        String question = "What is the airspeed velocity of an unladed sparrow?";
        quiz1.setQuestion(question);
        quiz2.setQuestion(question);
        List<String> choices = new ArrayList<>();
        choices.add("African or European?");
        choices.add("40m/s");
        quiz1.setChoices(choices);
        quiz2.setChoices(choices);
        quiz1.setCorrectChoice(0);
        quiz2.setCorrectChoice(0);

        assertTrue(quiz1.equal(quiz2));
    }

    @Test
    public void quizSetAnswer(){
        Quiz quiz = new Quiz();
        String question = "What is the airspeed velocity of an unladed sparrow?";
        quiz.setQuestion(question);

        List<String> choices = new ArrayList<>();
        choices.add("African or European?");
        choices.add("40m/s");

        quiz.setChoices(choices);

        quiz.setCorrectChoice(0);

        quiz.setAnswer("This is the answer");

        assertEquals("This is the answer",quiz.getAnswer());
    }

    @Test
    public void testQuizToString() {
        System.out.println("\nStarting testQuizToString");
        List<String> choices = Arrays.asList("Choice1", "Choice2", "Choice3");
        Quiz quiz = new Quiz("What is 2+2?", choices, 1, "Math", "Addition", 101);

        String expectedString = "Quiz: {" +
                // Assuming super.toString() returns something like "Topic: Addition, Number: 101"
                "ID = " + quiz.getId() +
                "\nQuestion = What is 2+2?" +
                "\nChoices = [Choice1, Choice2, Choice3]" +
                "\nCorrect Choice = Choice2" +
                "\n}";

        assertEquals(expectedString, quiz.toString());
    }

    private void testHelper() {
        assertNotNull(quiz);
        assertEquals("What is 1+1?", quiz.getQuestion());
        assertEquals("3", choices.get(0));
        assertEquals("11", choices.get(1));
        assertEquals("2", choices.get(2));
        assertEquals("100", choices.get(3));
        assertEquals(2, quiz.getCorrectChoice());
    }

    private void initialize() {
        String question = "What is 1+1?";
        choices = new ArrayList<>();
        choices.add("3");   // 0
        choices.add("11");  // 1
        choices.add("2");   // 2
        choices.add("100"); // 3
        int correctChoice = 2;

        quiz = new Quiz(question, choices, correctChoice);
    }
}
