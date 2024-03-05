package comp3350.srsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comp3350.srsys.R;
import comp3350.srsys.persistence.utils.DBHelper;

public class HomePage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DBHelper.copyDatabaseToDevice(this);

        Button buttonToCourses = findViewById(R.id.classesButton);
        buttonToCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, ClassesInfo.class);
                startActivity(intent);
            }
        });

        Button buttonToCalendar = findViewById(R.id.calendarButton);
        buttonToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, CalendarInfo.class);
                startActivity(intent);
            }
        });

        Button buttonToProfile = findViewById(R.id.profileButton);
        buttonToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, ProfileInfo.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}