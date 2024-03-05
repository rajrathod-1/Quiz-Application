package comp3350.srsys.business.validators;

import comp3350.srsys.objects.Note;

public class NoteValidator {
    public static boolean validate(Note note) {
        return note != null;
    }
}
