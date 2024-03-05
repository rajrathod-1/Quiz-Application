package comp3350.srsys.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import comp3350.srsys.R;
import comp3350.srsys.business.AccessEvents;
import comp3350.srsys.objects.Event;

public class CalendarEventInfo extends AppCompatActivity {

    private FloatingActionButton mAddReminderButton;
    private Toolbar mToolbar;
    private ListView reminderListView;
    private AlarmCursorAdapter mCursorAdapter;
    private List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event_edit);

        AccessEvents accessEvents = new AccessEvents();
        events = accessEvents.getEvents();
        System.out.println(events);


        reminderListView = findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        reminderListView.setEmptyView(emptyView);

        mCursorAdapter = new AlarmCursorAdapter(this, events);
        reminderListView.setAdapter(mCursorAdapter);

        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(CalendarEventInfo.this, AddReminderActivity.class);
                Event event = events.get(position);
                startActivity(intent);
            }
        });

        mAddReminderButton = findViewById(R.id.fab);
        mAddReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarEventInfo.this, AddReminderActivity.class);
                startActivity(intent);
            }
        });
    }
}
