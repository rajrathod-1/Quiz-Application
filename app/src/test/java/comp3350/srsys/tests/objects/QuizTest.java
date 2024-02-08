package comp3350.srsys.tests.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp3350.srsys.objects.Quiz;
import comp3350.srsys.objects.Tag;

public class QuizTest {

    private Quiz quiz;
    private List<String> choices;
    private int expectedTagNumbers;

    @Before
    public void setup() {
        System.out.println("Starting test for Quiz");
    }

    @Test
    public void testCreateQuizWithoutTag() {
        System.out.println("\nStarting testCreateQuizWithoutTag");

        initialize();
        testHelper();

        assertEquals(expectedTagNumbers, quiz.getCardTagList().size());

        System.out.println("Finished testCreateQuizWithoutTag");
    }

    @Test
    public void testCreateQuizWithOneTag() {
        System.out.println("\nStarting testCreateQuizWithOneTag");

        initialize();
        testHelper();

        quiz.addCardTag(new Tag("Software Engineering"));
        expectedTagNumbers++;

        assertEquals(expectedTagNumbers, quiz.getCardTagList().size());
        assertTrue(quiz.containsTag(new Tag("Software Engineering")));

        System.out.println("Finished testCreateQuizWithOneTag");
    }

    @Test
    public void testCreateQuizWithMultipleTags() {
        System.out.println("\nStarting testCreateQuizWithMultipleTags");

        initialize();
        testHelper();

        // Add Two Tags
        quiz.addCardTag(new Tag("Math"));
        quiz.addCardTag(new Tag("Science"));

        assertEquals(expectedTagNumbers+2, quiz.getCardTagList().size());
        assertTrue(quiz.containsTag(new Tag("Math")));
        assertTrue(quiz.containsTag(new Tag("Science")));

        System.out.println("Finished testCreateQuizWithMultipleTags");
    }


    @Test
    public void testCreateQuizWithSameTags() {
        System.out.println("\nStarting testCreateQuizWithSameTags");

        initialize();
        testHelper();

        quiz.addCardTag(new Tag("Math"));
        quiz.addCardTag(new Tag("Science"));
        expectedTagNumbers += 2;

        // Add two existing Tags, expect size to be unchanged
        quiz.addCardTag(new Tag("Math"));
        quiz.addCardTag(new Tag("Science"));

        assertEquals(expectedTagNumbers, quiz.getCardTagList().size());
        assertTrue(quiz.containsTag(new Tag("Math")));
        assertTrue(quiz.containsTag(new Tag("Science")));

        System.out.println("Finished testCreateQuizWithSameTags");
    }

    @Test
    public void testRemoveCardTag() {
        System.out.println("\nStarting testRemoveCardTag");

        initialize();
        testHelper();

        quiz.addCardTag(new Tag("Science"));
        expectedTagNumbers++;
        assertEquals(expectedTagNumbers, quiz.getCardTagList().size());

        quiz.removeCardTag(new Tag("Science"));
        expectedTagNumbers--;

        assertEquals(expectedTagNumbers, quiz.getCardTagList().size());
        assertFalse(quiz.containsTag(new Tag("Math")));
        assertFalse(quiz.containsTag(new Tag("Science")));

        System.out.println("Finished testRemoveCardTag");
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
    public void quizToStringTest(){
        initialize();
        assertEquals("Quiz: {" +
                "ID = " + quiz.getId() + "Date = " +
                new Date() +
                "Tag List = []"  +
                "\nQuestion = " + quiz.getQuestion() +
                "\nChoices = " + quiz.getChoices() +
                "\nCorrect Choice = " + quiz.getCorrectChoice() +
                "\n}",quiz.toString());
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
        expectedTagNumbers = 0;
    }
}
