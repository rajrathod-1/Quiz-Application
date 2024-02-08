package comp3350.srsys.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Quiz extends Card{

    private static int quizCount = 1;   // number of cards created

    //Instance Variables
    private String question;
    private List<String> choices;
    private int correctChoice;   // index


    /*
     *  Quiz Default Constructor
     */
    public Quiz()
    {
        super();
        this.question = null;
        this.choices = new ArrayList<>();
        this.correctChoice = -1;
    }

    /*
     *  Quiz Constructor
     *      - used by the app when a new Quiz is created
     *      - sets the id to quizCount
     */
    public Quiz(String question, List<String> choices, int correctChoice)
    {
        super();
        this.id = quizCount++;
        this.question = question;
        this.choices = choices;
        this.correctChoice = correctChoice;
    }

    /*
     *  Quiz Constructor
     *      - used when DB creates Quizzes with data from database
     */
    public Quiz (int id, Date date, String question, List<String> choices, int correctChoice)
    {
        super();
        this.id = id;
        this.date = date;
        this.question = question;
        this.choices = choices;
        this.correctChoice = correctChoice;
    }

    // Instance Methods

    /*
    *   GETTERS
    */
    public String getQuestion() {
        return question;
    }

    public List<String> getChoices() {
        return choices;
    }

    public int getCorrectChoice() {
        return correctChoice;
    }

    // Gets the string of the correct choice
    public String getAnswer() {
        return choices.get(correctChoice);
    }

    /*
    *   SETTERS
    */
    public void setQuestion(String newQuestion) {
        this.question = newQuestion;
    }

    public void setChoices(List<String> newChoices) {
        this.choices = newChoices;
    }

    public void setCorrectChoice(int newCC) {
        this.correctChoice = newCC;
    }

    /*
     *  HELPERS
     */
    @Override
    public String toString() {
        return "Quiz: {" +
                super.toString() +
                "\nQuestion = " + this.question +
                "\nChoices = " + this.choices.toString() +
                "\nCorrect Choice = " + this.choices.get(correctChoice) +
                "\n}";
    }
    public boolean equal(Object obj) {
        boolean result = false;
        if(obj instanceof Quiz) {
            Quiz aQuiz = (Quiz) obj;
            result = this.id == aQuiz.id;
        }
        return result;
    }
}
