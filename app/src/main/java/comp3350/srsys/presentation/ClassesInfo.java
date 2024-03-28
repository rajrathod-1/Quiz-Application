package comp3350.srsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import comp3350.srsys.R;
import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.business.validators.CourseValidator;
import comp3350.srsys.objects.Course;


public class ClassesInfo extends Activity {

    private int selectedCoursePosition = -1;

    private ArrayAdapter<Course> courseArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        ImageButton favoriteButton = findViewById(R.id.favoriteButton);
        ImageButton notesButton = findViewById(R.id.notesButton);
        ImageButton quizButton = findViewById(R.id.quizButton);
        ImageButton eventsButton = findViewById(R.id.eventsButton);

        favoriteButton.setEnabled(false);
        notesButton.setEnabled(false);
        quizButton.setEnabled(false);
        eventsButton.setEnabled(false);

        ImageButton buttonHome = findViewById(R.id.homeButton);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassesInfo.this, HomePage.class);
                startActivity(intent);
            }
        });

        Button buttonToCalendar = findViewById(R.id.calendarButton);
        buttonToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassesInfo.this, CalendarInfo.class);
                startActivity(intent);
            }
        });

        Button buttonToProfile = findViewById(R.id.profileButton);
        buttonToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassesInfo.this, ProfileInfo.class);
                startActivity(intent);
            }
        });

        Button buttonToCourses = findViewById(R.id.classesButton);
        buttonToCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassesInfo.this, ClassesInfo.class);
                startActivity(intent);
            }
        });

        buttonToCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassesInfo.this, ClassesInfo.class);
                startActivity(intent);
            }
        });

        AccessCourses accessCourse = new AccessCourses();
        List<Course> registrations = accessCourse.getCourses();

        courseArrayAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_2, android.R.id.text1, registrations)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                EditText coursesEnrolled = (EditText)findViewById(R.id.numCoursesEnrolled);
                coursesEnrolled.setText(Integer.toString(accessCourse.getCourses().size()));

                EditText termGPA = (EditText)findViewById(R.id.termGPA);
                termGPA.setText(Double.toString(accessCourse.getTermGPA()));

                text1.setText(registrations.get(position).getTopic() + " " + registrations.get(position).getCourseNum() + ": " + registrations.get(position).getCourseName());
                text2.setText("Start Date : " + registrations.get(position).getStartDateString() + " End Date : " + registrations.get(position).getEndDateString());

                return view;
            }
        };

        ListView listView = (ListView)findViewById(R.id.listCourses);
        listView.setAdapter(courseArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course selected;
                Button deleteButton = (Button)findViewById(R.id.deleteEntry);
                Button gpaButton = (Button)findViewById(R.id.gradeButton);
                ImageButton favoriteButton = findViewById(R.id.favoriteButton);
                ImageButton notesButton = findViewById(R.id.notesButton);
                ImageButton quizButton = findViewById(R.id.quizButton);
                ImageButton eventsButton = findViewById(R.id.eventsButton);
                EditText courseCode = (EditText)findViewById(R.id.codeOutput);
                EditText courseName = (EditText)findViewById(R.id.nameOutput);
                EditText dateEnrolled = (EditText)findViewById(R.id.startOutput);
                EditText dateEnded = (EditText)findViewById(R.id.endOutput);
                EditText subjectOutput = (EditText)findViewById(R.id.subjectOutput);
                EditText notesOutput = (EditText)findViewById(R.id.notesOutput);
                EditText quizOutput = (EditText)findViewById(R.id.quizOutput);

                if (position == selectedCoursePosition) {
                    listView.setItemChecked(position, false);
                    deleteButton.setEnabled(false);
                    gpaButton.setEnabled(false);
                    favoriteButton.setEnabled(false);
                    notesButton.setEnabled(false);
                    quizButton.setEnabled(false);
                    eventsButton.setEnabled(false);
                    courseCode.setText("");
                    courseName.setText("");
                    dateEnrolled.setText("");
                    dateEnded.setText("");
                    subjectOutput.setText("");
                    notesOutput.setText("");
                    quizOutput.setText("");
                    gpaButton.setText("Grade:");
                    selectedCoursePosition = -1;
                    favoriteButton.setImageResource(android.R.drawable.btn_star_big_off);
                } else {
                    listView.setItemChecked(position, true);
                    deleteButton.setEnabled(true);
                    gpaButton.setEnabled(true);
                    favoriteButton.setEnabled(true);
                    notesButton.setEnabled(true);
                    quizButton.setEnabled(true);
                    eventsButton.setEnabled(true);
                    selected = courseArrayAdapter.getItem(position);
                    gpaButton.setText("Grade: " + selected.getGPA());
                    selectedCoursePosition = position;
                    selectCourseAtPosition(position);
                    if (selected.getmarked()){
                        favoriteButton.setImageResource(android.R.drawable.btn_star_big_on);
                    }
                    else{
                        favoriteButton.setImageResource(android.R.drawable.btn_star_big_off);
                    }
                }
            }
        });

        ImageButton buttonToNewCourse = findViewById(R.id.addCourse);
        buttonToNewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_new_course_popup, null);

                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView,
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                Button cancelButton = popupView.findViewById(R.id.cancelButton);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                Button createButton = popupView.findViewById(R.id.createButton);
                createButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText newCode = (EditText)popupView.findViewById(R.id.codeOutput);
                        EditText newName = (EditText)popupView.findViewById(R.id.nameOutput);
                        EditText newDateCreated = (EditText)popupView.findViewById(R.id.startOutput);
                        EditText newDateEnding = (EditText)popupView.findViewById(R.id.endOutput);
                        EditText newTopic = (EditText)popupView.findViewById(R.id.subjectOutput);
                        EditText newCreditHours = (EditText)popupView.findViewById(R.id.creditHoursInput);
                        ToggleButton favoriteButton = (ToggleButton)popupView.findViewById(R.id.newFavoriteButton);

                        if (CourseValidator.validateCourseNum(Double.parseDouble(newCode.getText().toString())) && CourseValidator.validateCourseSubject(newTopic.getText().toString())
                            && CourseValidator.validateCourseName(newName.getText().toString()) && CourseValidator.validateDate(newDateCreated.getText().toString())
                            && CourseValidator.validateDate(newDateEnding.getText().toString()) && CourseValidator.validateCreditHours(Double.parseDouble(newCreditHours.getText().toString()))) {

                            String[] arrOfDateStartedInfo = newDateCreated.getText().toString().split("-", 0);
                            String[] arrOfDateEndedInfo = newDateEnding.getText().toString().split("-", 0);

                            Course newCourse = new Course(newTopic.getText().toString(),
                                    Integer.parseInt(newCode.getText().toString()), newName.getText().toString(), Integer.parseInt(arrOfDateStartedInfo[1]),
                                    Integer.parseInt(arrOfDateStartedInfo[2]), Integer.parseInt(arrOfDateStartedInfo[0]), Integer.parseInt(arrOfDateEndedInfo[1]),
                                    Integer.parseInt(arrOfDateEndedInfo[2]), Integer.parseInt(arrOfDateEndedInfo[0]), Double.parseDouble(newCreditHours.getText().toString()));

                            if (favoriteButton.isChecked()) {
                                newCourse.favoriteCourse();
                            }

                            accessCourse.insertCourse(newCourse);
                            courseArrayAdapter.notifyDataSetChanged();
                            popupWindow.dismiss();
                            Toast.makeText(getApplicationContext(), "New Course Successfully Created", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Invalid input, please try again.", Toast.LENGTH_SHORT).show();
                            newCode.setText("");
                            newName.setText("");
                            newDateCreated.setText("");
                            newDateEnding.setText("");
                            newTopic.setText("");
                            newCreditHours.setText("");
                            favoriteButton.setChecked(false);
                        }
                    }
                });
            }
        });

        Button buttonToDeleteCourse = findViewById(R.id.deleteEntry);
        buttonToDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_delete_course, null);

                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView,
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                Button yesButton = popupView.findViewById(R.id.yesButton);
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Course selected = courseArrayAdapter.getItem(selectedCoursePosition);
                        accessCourse.deleteCourse(selected);
                        courseArrayAdapter.notifyDataSetChanged();
                        popupWindow.dismiss();
                    }
                });

                Button noButton = popupView.findViewById(R.id.noButton);
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

        Button buttonToSetGPA = findViewById(R.id.gradeButton);
        buttonToSetGPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_change_gpa, null);

                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView,
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);


                EditText gpaField = (EditText)popupView.findViewById(R.id.gpaInput);

                Button saveButton = popupView.findViewById(R.id.saveButton);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CourseValidator.validateNewGPA(Double.parseDouble(gpaField.getText().toString()))){
                            Course selected = courseArrayAdapter.getItem(selectedCoursePosition);
                            accessCourse.changeGPA(selected, Double.parseDouble(gpaField.getText().toString()));
                            courseArrayAdapter.notifyDataSetChanged();
                            popupWindow.dismiss();
                        }
                        else{
                            gpaField.setText("");
                            Toast.makeText(getApplicationContext(), "Invalid input, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Button cancelButton = popupView.findViewById(R.id.cancelButton);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course selected = courseArrayAdapter.getItem(selectedCoursePosition);
                ImageButton favouriteButton = (ImageButton)findViewById(R.id.favoriteButton);
                if (selected.getmarked()){
                    selected.unfavoriteCourse();
                    favouriteButton.setImageResource(android.R.drawable.btn_star_big_off);
                }
                else{
                    selected.favoriteCourse();
                    favouriteButton.setImageResource(android.R.drawable.btn_star_big_on);
                }
            }
        });


        Button sortByDateButton = findViewById(R.id.dateButton);
        sortByDateButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){

               accessCourse.sortCoursesByDate();
               courseArrayAdapter.notifyDataSetChanged();
           }
        });

        Button sortBySubjectButton = findViewById(R.id.subjectButton);
        sortBySubjectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                accessCourse.sortCoursesBySubject();
                courseArrayAdapter.notifyDataSetChanged();
            }
        });

        Button sortByNameButton = findViewById(R.id.nameSort);
        sortByNameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                accessCourse.sortCoursesByName();
                courseArrayAdapter.notifyDataSetChanged();
            }
        });

        Button sortByCourseIDButton = findViewById(R.id.codeSort);
        sortByCourseIDButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                accessCourse.sortCoursesByID();
                courseArrayAdapter.notifyDataSetChanged();
            }
        });

        Button sortByFavoriteButton = findViewById(R.id.favouriteSort);
        sortByFavoriteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                accessCourse.sortCoursesByFavorite();
                courseArrayAdapter.notifyDataSetChanged();
            }
        });
        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassesInfo.this, NotesInfo.class);
                Course selected = courseArrayAdapter.getItem(selectedCoursePosition);
                intent.putExtra("courseKey",selected);
                startActivity(intent);
            }
        });

        //Navigate to quiz cards
        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassesInfo.this, QuizListInfo.class);
                Course selected = courseArrayAdapter.getItem(selectedCoursePosition);
                intent.putExtra("course", selected);
                startActivity(intent);
            }
        });

        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassesInfo.this, CalendarInfo.class);
                startActivity(intent);
            }
        });

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void selectCourseAtPosition(int position) {
        Course selected = courseArrayAdapter.getItem(position);

        EditText courseCode = (EditText)findViewById(R.id.codeOutput);
        EditText courseName = (EditText)findViewById(R.id.nameOutput);
        EditText dateEnrolled = (EditText)findViewById(R.id.startOutput);
        EditText dateEnded = (EditText)findViewById(R.id.endOutput);
        EditText subjectOutput = (EditText)findViewById(R.id.subjectOutput);
        EditText notesOutput = (EditText)findViewById(R.id.notesOutput);
        EditText quizOutput = (EditText)findViewById(R.id.quizOutput);

        ImageButton favouriteButton = (ImageButton)findViewById(R.id.favoriteButton);
        if (selected.getmarked()){
            favouriteButton.setImageResource(android.R.drawable.btn_star_big_on);
        }
        else{
            favouriteButton.setImageResource(android.R.drawable.btn_star_big_off);
        }

        String outputCode = (selected.getTopic() + " " + selected.getCourseNum());
        courseCode.setText(outputCode);
        courseName.setText(selected.getCourseName());
        dateEnrolled.setText(selected.getStartDateString());
        dateEnded.setText(selected.getEndDateString());
        subjectOutput.setText(selected.getTopic());
        notesOutput.setText(selected.getNotesCreated() + "");
        quizOutput.setText(selected.getQuizCreated() + "");
    }
}