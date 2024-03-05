package comp3350.srsys.business.exceptions;

public class NoteNotFoundException extends RuntimeException{

    public NoteNotFoundException(String error) {
        super("The note is not found: \n" + error);
    }

}
