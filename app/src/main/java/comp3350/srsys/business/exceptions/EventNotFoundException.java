package comp3350.srsys.business.exceptions;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(String error) {
        super("The event is not found: \n" + error);
    }

}
