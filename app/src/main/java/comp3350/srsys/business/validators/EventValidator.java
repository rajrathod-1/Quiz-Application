package comp3350.srsys.business.validators;

import comp3350.srsys.objects.Event;

public class EventValidator {

    public static boolean validateTitle(String title) {
        return (title.length() < 101);
    }

    public static boolean validateDate(String date){
        boolean checkBool = true;

        String[] parts = date.split("/");

        if (parts.length == 3){
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            // Check the range of year, month, and day
            if (year < 1 || month < 1 || month > 12 || day < 1) {
                checkBool = false;
            }

            // Array to hold the number of days in each month
            int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

            // Adjust for leap years
            if (month == 2 && ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0))) {
                daysInMonth[1] = 29;
            }

            if (month <= 0  || month - 1 >= 12 || !(day <= daysInMonth[month - 1])){
                checkBool = false;
            }

        }
        else{
            checkBool = false;
        }

        return checkBool;
    }

    public static boolean validateTime(String time){
        boolean returnBool = true;
        String[] parts = time.split(":");

        if (parts.length != 2) {
            returnBool = false;
        }
        else{
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);

            // Validate hours, minutes, and seconds
            if (hours < 0 || hours > 23) returnBool = false;
            if (minutes < 0 || minutes > 59) returnBool = false;
        }

        return returnBool;

    }
}
