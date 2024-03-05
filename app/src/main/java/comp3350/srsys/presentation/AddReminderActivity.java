package comp3350.srsys.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import comp3350.srsys.R;
import comp3350.srsys.business.AccessEvents;
import comp3350.srsys.objects.Event;
public class AddReminderActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private EditText mTitleText;
    private TextView mDateText, mTimeText;
    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private String mTime = "";
    private String mDate = "";

    AccessEvents accessEvents = new AccessEvents();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event_main);

        Intent intent = getIntent();

        mTitleText = findViewById(R.id.reminder_title);
        mDateText = findViewById(R.id.set_date);
        mTimeText = findViewById(R.id.set_time);

        Button btnDone = findViewById(R.id.btn_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReminder();
                Intent intent = new Intent(AddReminderActivity.this, CalendarEventInfo.class);
                startActivity(intent);
            }
        });

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;

        // Setup TextViews using reminder values
        mDateText.setText(mDate);
        mTimeText.setText(mTime);

    }

    // On clicking Time picker
    public void setTime(View v) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setThemeDark(false);
        tpd.show(getSupportFragmentManager(), "Timepickerdialog");

    }

    // On clicking Date picker
    public void setDate(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    // Obtain time from time picker
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10) {
            mTime = hourOfDay + ":" + "0" + minute;
        } else {
            mTime = hourOfDay + ":" + minute;
        }
        mTimeText.setText(mTime);
    }

    // Obtain date from date picker
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
        mDateText.setText(mDate);
    }

    // On clicking the save button
    public void saveReminder() {

        String title = mTitleText.getText().toString();
        String date = mDateText.getText().toString();
        String time = mTimeText.getText().toString();

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a title for the reminder", Toast.LENGTH_SHORT).show();
            return; // Exit the method without saving the reminder
        }
        Event newEvent = new Event(title, date, time);
        accessEvents.insertEvent(newEvent);

    }
    // On pressing the back button
}

