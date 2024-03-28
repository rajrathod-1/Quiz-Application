package comp3350.srsys.business;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AccessCalendarUtils {
    public static LocalDate selectedDate;
    public static int numberOfWeeksColumn = 7;
    public static int numberOfDaysColumn = 6;


    public static String monthYearFromDate(LocalDate date)
    {
        selectedDate = date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static ArrayList<LocalDate> daysInMonthArray(LocalDate date)
    {
        selectedDate = date;
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = date.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= numberOfDaysColumn * numberOfWeeksColumn; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
                daysInMonthArray.add(null);
            else
                daysInMonthArray.add(LocalDate.of(date.getYear(),date.getMonth(),i - dayOfWeek));
        }
        return  daysInMonthArray;
    }
}
