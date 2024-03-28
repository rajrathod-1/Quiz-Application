package comp3350.srsys.business.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileValidator {

    private static final int MAX_STRING_LENGTH = 20;
    public static boolean validateName(String inputtedName) {
        return inputtedName.length() < MAX_STRING_LENGTH;
    }

    public static boolean validateUserName(String content) {
        return content.length() < MAX_STRING_LENGTH;
    }

    public static boolean validateEmail(String content) {
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    public static boolean validatePhone(String content) {
        String PHONE_REGEX = "^\\d{3}-\\d{3}-\\d{4}$";
        return content.matches(PHONE_REGEX);
    }
}
