package comp3350.srsys.business;

public class QuizMark {

    private boolean[] questionSeen;
    private boolean[] questionCorrect;

    public QuizMark(int size) {
        questionSeen = new boolean[size];
        questionCorrect = new boolean[size];
    }

    //User viewed the answer for the question
    public void setQuestionSeenAt(int position)
    {
        questionSeen[position] = true;
    }

    //User got the answer correct
    public void setQuestionCorrectAt(int position)
    {
        questionCorrect[position] = true;
    }

    //Check if user already saw the answer for this question
    public boolean questionAlreadySeen(int position)
    {
        return questionSeen[position];
    }

    //Check if user got the answer for the question correct
    public boolean gotQuestionCorrectAt(int position)
    {
        return questionCorrect[position];
    }

    public void checkChoice(int choice, int correctChoice, int position)
    {
        questionSeen[position] = true;
        questionCorrect[position] = (choice == correctChoice);
    }
}
