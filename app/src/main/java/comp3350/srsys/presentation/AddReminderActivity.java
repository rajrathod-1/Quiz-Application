package comp3350.srsys.presentation;

import android.app.AlarmManager;
import android.app.PendingIntent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import comp3350.srsys.R;
import comp3350.srsys.business.AccessEvents;
import comp3350.srsys.business.validators.EventValidator;
import comp3350.srsys.objects.Event;

public class AddReminderActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private EditText titleText;
    private TextView dateText, timeText;
    private Calendar calendar;
    private int year, month, hour, minute, day;
    private String time = "";
    private String date = "";

    AccessEvents accessEvents = new AccessEvents();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event_main);

        Intent intent = getIntent();

        titleText = findViewById(R.id.reminder_title);
        dateText = findViewById(R.id.set_date);
        timeText = findViewById(R.id.set_time);

        Button btnDone = findViewById(R.id.btn_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleText.getText().toString();
                String date = dateText.getText().toString();
                String time = timeText.getText().toString();
                if (EventValidator.validateTitle(title) && EventValidator.validateDate(date) && EventValidator.validateTime(time)){
                    saveReminder();
                    Intent intent = new Intent(AddReminderActivity.this, CalendarEventInfo.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "New Event Successfully Created", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Event Information Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);

        date = day + "/" + month + "/" + year;
        time = hour + ":" + minute;

        // Setup TextViews using reminder values
        dateText.setText(date);
        timeText.setText(time);

    }

    // On clicking Time picker
    public void setTime(View v) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePicker = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        timePicker.setThemeDark(false);
        timePicker.show(getSupportFragmentManager(), "Timepickerdialog");

    }

    // On clicking Date picker
    public void setDate(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePicker.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    // Obtain time from time picker
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        hour = hourOfDay;
        this.minute = minute;
        if (minute < 10) {
            time = hourOfDay + ":" + "0" + minute;
        } else {
            time = hourOfDay + ":" + minute;
        }
        timeText.setText(time);
    }

    // Obtain date from date picker
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        day = dayOfMonth;
        month = monthOfYear;
        this.year = year;
        date = dayOfMonth + "/" + monthOfYear + "/" + year;
        dateText.setText(date);
    }

    // On clicking the save button
    private void saveReminder() {

        String title = titleText.getText().toString();
        String date = dateText.getText().toString();
        String time = timeText.getText().toString();

        Event newEvent = new Event(0, title, date, time);
        accessEvents.insertEvent(newEvent);
        scheduleNotification(newEvent);
    }

    private void scheduleNotification(Event event) {
        // Parse event date and time to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        long eventTimeMillis = 0;
        try {
            Date eventDateTime = sdf.parse(event.getNewDate() + " " + event.getTime());
            eventTimeMillis = eventDateTime.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Create an intent for the notification
        Intent notificationIntent = new Intent(this, NotificationReceiver.class);
        notificationIntent.putExtra("title", event.getTitle());
        notificationIntent.putExtra("message", "Event time: " + event.getTime());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, event.getId(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        // Set the notification to trigger at the event time
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, eventTimeMillis, pendingIntent);
        }
    }
}