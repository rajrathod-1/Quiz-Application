package comp3350.srsys.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import comp3350.srsys.R;
import comp3350.srsys.business.AccessEvents;
import comp3350.srsys.objects.Event;

public class CalendarEventInfo extends AppCompatActivity {

    private FloatingActionButton addReminderButton;
    private ListView reminderListView;
    private List<Event> events;
    private ColorGenerator colorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable drawableBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event_edit);

        AccessEvents accessEvents = new AccessEvents();
        events = accessEvents.getEvents();
        System.out.println(events);

        Button buttonToCourses = findViewById(R.id.classesButton);
        buttonToCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarEventInfo.this, ClassesInfo.class);
                startActivity(intent);
            }
        });

        Button buttonToCalendar = findViewById(R.id.calendarButton);
        buttonToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarEventInfo.this, CalendarInfo.class);
                startActivity(intent);
            }
        });

        Button buttonToProfile = findViewById(R.id.profileButton);
        buttonToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarEventInfo.this, ProfileInfo.class);
                startActivity(intent);
            }
        });

        reminderListView = findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        reminderListView.setEmptyView(emptyView);

        reminderListView.setAdapter(new ArrayAdapter<Event>(this, 0, events) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View listItemView = convertView;
                if (listItemView == null) {
                    listItemView = getLayoutInflater().inflate(R.layout.activity_calendar_event_items, parent, false);
                }

                Event event = getItem(position);

                TextView mTitleText = listItemView.findViewById(R.id.event_title);
                TextView mDateAndTimeText = listItemView.findViewById(R.id.event_date_time);
                ImageView mThumbnailImage = listItemView.findViewById(R.id.thumbnail_image);

                mTitleText.setText(event.getTitle());

                // Handle null eventDateTime
                String dateTimeString;
                if (event.getNewDate() != null && event.getTime() != null) {
                    dateTimeString = "Date: " + event.getNewDate() + " " + "Time:" + event.getTime();
                } else {
                    dateTimeString = "No date/time set";
                }
                mDateAndTimeText.setText(dateTimeString);

                String letter = "A";
                if (event.getTitle() != null && !event.getTitle().isEmpty()) {
                    letter = event.getTitle().substring(0, 1);
                }
                int color = colorGenerator.getRandomColor();
                drawableBuilder = TextDrawable.builder().buildRound(letter, color);
                mThumbnailImage.setImageDrawable(drawableBuilder);

                return listItemView;
            }
        });

        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(CalendarEventInfo.this, AddReminderActivity.class);
                startActivity(intent);
            }
        });

        addReminderButton = findViewById(R.id.floatingaddbutton);
        addReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarEventInfo.this, AddReminderActivity.class);
                startActivity(intent);
            }
        });
    }
}
