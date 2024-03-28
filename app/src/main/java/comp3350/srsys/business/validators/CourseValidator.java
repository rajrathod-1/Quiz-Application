package comp3350.srsys.business.validators;

import comp3350.srsys.objects.Course;

public class CourseValidator {
    private static final int MAX_COURSE_NUM = 8000;
    private static final int MAX_SUBJECT_LENGTH = 10;
    private static final int MIN_SUBJECT_LENGTH = 2;
    private static final int MAX_COURSE_NAME = 50;
    private static final int MIN_COURSE_NAME = 2;
    private static final int MAX_CREDIT_NUM = 7;
    private static final double MAX_GPA = 4.5;

    //Ensures that the course number is valid. Assuming U of M standard course numbering where courses
    // max out at the 8000 level. This prevents overflow. Also check that decimals are not present.
    public static boolean validateCourseNum(double number) {
        return number < MAX_COURSE_NUM && (number % 1 == 0) && number > 0;
    }

    public static boolean validateCourseSubject(String ourString){
        return ourString.length() < MAX_SUBJECT_LENGTH && ourString.length() > MIN_SUBJECT_LENGTH;
    }

    public static boolean validateCourseName(String ourString){
        return ourString.length() < MAX_COURSE_NAME && ourString.length() > MIN_COURSE_NAME;
    }

    //Assuming that the used has inputted 3 integer variables as a string into this text bar and that
    // all of those variables correspond to proper dates
    public static boolean validateDate(String date){
        boolean checkBool = true;

        String[] parts = date.split("-");
        if (parts.length == 3){
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

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

    //Ensures that the course number is valid. Assuming U of M standard course numbering where courses
    // max out at the 6 credit Hours. This prevents overflow. Decimals allowed dude to 1.5 credit hour courses existing
    public static boolean validateCreditHours(double creditHours){
        return creditHours < MAX_CREDIT_NUM && creditHours > 0;
    }
    //makes sure that GPA is within the proper ranges
    public static boolean validateNewGPA(double gpa){
        return gpa <= MAX_GPA &&  gpa >= 0;
    }
}
