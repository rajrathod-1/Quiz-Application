package comp3350.srsys.business.validators;

import comp3350.srsys.objects.Quiz;

public class QuizValidator {

    private static final int MAX_QUESTION_CHOICE_LENGTH = 100;
    public static boolean validateQuestion(String inputtedQuestion) {
        return inputtedQuestion.length() < MAX_QUESTION_CHOICE_LENGTH;
    }

    public static boolean validateChoice(String inputtedChoice) {
        return inputtedChoice.length() < MAX_QUESTION_CHOICE_LENGTH;
    }
}
