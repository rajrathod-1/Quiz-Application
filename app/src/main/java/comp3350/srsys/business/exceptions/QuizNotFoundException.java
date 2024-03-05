package comp3350.srsys.business.exceptions;

public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException(String error) {
        super("The quiz is not found: \n" + error);
    }
}
