package comp3350.srsys.business.validators;

import comp3350.srsys.objects.Note;

public class NoteValidator {
    private static final int MAX_NOTE_NAME = 101;
    private static final int MAX_CONTENT_LENGTH = 101;

    public static boolean validateNoteName(String inputtedName) {
        return inputtedName.length() < MAX_NOTE_NAME;
    }

    public static boolean validateContent(String content) {
        return content.length() < MAX_CONTENT_LENGTH;
    }
}
