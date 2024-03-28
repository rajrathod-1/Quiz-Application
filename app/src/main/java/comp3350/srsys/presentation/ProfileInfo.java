package comp3350.srsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import comp3350.srsys.R;
import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.business.AccessProfile;
import comp3350.srsys.business.validators.ProfileValidator;
import comp3350.srsys.objects.Profile;

public class ProfileInfo extends Activity {

    AccessProfile accessProfile = new AccessProfile();
    Profile updatedProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        updateProfile();

        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("BUTTON CLICKED");
                TextView fullNameTextView = findViewById(R.id.input_full_name);
                TextView usernameTextView = findViewById(R.id.input_username);
                TextView emailTextView = findViewById(R.id.email);
                TextView phoneTextView = findViewById(R.id.phone);



                AccessCourses accessCourses = new AccessCourses();
                int coursesCount = accessCourses.getCourses().size();

                if (ProfileValidator.validateName(fullNameTextView.getText().toString()) && ProfileValidator.validateUserName(usernameTextView.getText().toString())
                    && ProfileValidator.validateEmail(emailTextView.getText().toString()) && ProfileValidator.validatePhone(phoneTextView.getText().toString())){

                    updatedProfile = new Profile(1, fullNameTextView.getText().toString(),
                            usernameTextView.getText().toString(),
                            emailTextView.getText().toString(),
                            phoneTextView.getText().toString(),
                            coursesCount,
                            "Winter 2024");
                    accessProfile.updateProfile(updatedProfile);

                    TextView new_fullNameTextView = findViewById(R.id.full_name);
                    TextView new_usernameTextView = findViewById(R.id.username);
                    TextView new_inputFullNameTextView = findViewById(R.id.input_full_name);
                    TextView new_inputUsernameTextView = findViewById(R.id.input_username);
                    TextView new_emailTextView = findViewById(R.id.email);
                    TextView new_phoneTextView = findViewById(R.id.phone);
                    TextView new_numCoursesTextView = findViewById(R.id.courses_desc);
                    TextView new_semesterTextView = findViewById(R.id.random_desc);

                    new_fullNameTextView.setText(updatedProfile.getName());
                    new_usernameTextView.setText(updatedProfile.getUsername());
                    new_inputFullNameTextView.setText(updatedProfile.getName());
                    new_inputUsernameTextView.setText(updatedProfile.getUsername());
                    new_emailTextView.setText(updatedProfile.getEmail());
                    new_phoneTextView.setText(updatedProfile.getPhone());
                    new_numCoursesTextView.setText(String.valueOf(updatedProfile.getNumCourses()));
                    new_semesterTextView.setText(updatedProfile.getSemester());
                    Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error in Profile Creation: Invalid Information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonToCalendar = findViewById(R.id.calendarButton);
        buttonToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileInfo.this, CalendarInfo.class);
                startActivity(intent);
            }
        });

        Button buttonToCourses = findViewById(R.id.classesButton);
        buttonToCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileInfo.this, ClassesInfo.class);
                startActivity(intent);
            }
        });

        Button buttonToProfile = findViewById(R.id.profileButton);
        buttonToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileInfo.this, ProfileInfo.class);
                startActivity(intent);
                updateProfile();
            }
        });
    }



    private void updateProfile() {
        TextView fullNameTextView = findViewById(R.id.full_name);
        TextView usernameTextView = findViewById(R.id.username);
        TextView inputFullNameTextView = findViewById(R.id.input_full_name);
        TextView inputUsernameTextView = findViewById(R.id.input_username);
        TextView emailTextView = findViewById(R.id.email);
        TextView phoneTextView = findViewById(R.id.phone);
        TextView numCoursesTextView = findViewById(R.id.courses_desc);
        TextView semesterTextView = findViewById(R.id.random_desc);

        Profile profile = accessProfile.getProfile();

        System.out.println("PROFILE: " + profile.getName() + " " + profile.getUsername() + " " + profile.getEmail() + " " + profile.getPhone() + " " + profile.getNumCourses() + " " + profile.getSemester());

        // Check if profile is not null
        if (profile != null) {
            // Update UI with profile data
            fullNameTextView.setText(profile.getName());
            usernameTextView.setText(profile.getUsername());
            inputFullNameTextView.setText(profile.getName());
            inputUsernameTextView.setText(profile.getUsername());
            emailTextView.setText(profile.getEmail());
            phoneTextView.setText(profile.getPhone());
            numCoursesTextView.setText(String.valueOf(profile.getNumCourses()));
            semesterTextView.setText(profile.getSemester());
        } else {
            // Handle the case when profile is null
            // For example, you can display a message or set default values
            fullNameTextView.setText("");
            usernameTextView.setText("");
            inputFullNameTextView.setText("");
            inputUsernameTextView.setText("");
            emailTextView.setText("");
            phoneTextView.setText("");
            numCoursesTextView.setText("");
            semesterTextView.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}