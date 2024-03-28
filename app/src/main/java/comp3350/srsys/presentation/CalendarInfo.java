package comp3350.srsys.presentation;

import static comp3350.srsys.business.AccessCalendarUtils.daysInMonthArray;
import static comp3350.srsys.business.AccessCalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import comp3350.srsys.R;
import comp3350.srsys.business.AccessCalendarUtils;

public class CalendarInfo extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView yearMonText;
    private RecyclerView calRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initWidgets();
        AccessCalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        Button buttonToCourses = findViewById(R.id.calendar_classesButton);
        buttonToCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarInfo.this, ClassesInfo.class);
                startActivity(intent);
            }
        });

        Button buttonToCalendar = findViewById(R.id.calendar_calendarButton);
        buttonToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarInfo.this, CalendarInfo.class);
                startActivity(intent);
            }
        });

        Button buttonToProfile = findViewById(R.id.calendar_profileButton);
        buttonToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarInfo.this, ProfileInfo.class);
                startActivity(intent);
            }
        });

        Button buttonToEvent = findViewById(R.id.newEvent);
        buttonToEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarInfo.this, CalendarEventInfo.class);
                startActivity(intent);
            }
        });
    }

    private void initWidgets()
    {
        calRecyclerView = findViewById(R.id.calendarRecyclerView);
        yearMonText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView()
    {
        yearMonText.setText(monthYearFromDate(AccessCalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(AccessCalendarUtils.selectedDate);

        CalendarAdapter calAda = new CalendarAdapter(daysInMonth, (CalendarAdapter.OnItemListener) this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calRecyclerView.setLayoutManager(layoutManager);
        calRecyclerView.setAdapter(calAda);
    }


    public void previousMonthAction(View view)
    {
        AccessCalendarUtils.selectedDate = AccessCalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        AccessCalendarUtils.selectedDate = AccessCalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null) {
            AccessCalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

}