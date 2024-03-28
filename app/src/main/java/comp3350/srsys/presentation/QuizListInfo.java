package comp3350.srsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import comp3350.srsys.R;
import comp3350.srsys.application.Services;
import comp3350.srsys.business.AccessQuizzes;
import comp3350.srsys.business.validators.QuizValidator;
import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Quiz;

public class QuizListInfo extends Activity{
    private final int UNSELECTED = -1;
    private Quiz selectedQuiz = null;
    private int selectedQuizPos = UNSELECTED;
    private int selectedType = 0;
    private Course course;
    private final String FLASHCARD = "FlashCard";
    private final String MULTIPLE_CHOICE = "MC";
    private final String TRUE_OR_FALSE = "TrueOrFalse";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        Class<? extends Course> clazz = Course.class;
        course = getIntent().getSerializableExtra("course", clazz);

        AccessQuizzes accessQuizzes = new AccessQuizzes(course);
        List<Quiz> quizzes = accessQuizzes.getQuizzes();

        ImageButton playButton = findViewById(R.id.playButton);
        ImageButton editButton = findViewById(R.id.editButton);
        ImageButton deleteButton = findViewById(R.id.deleteButton);
        ImageButton addButton = findViewById(R.id.addButton);

        enableToolbarButtons(false);

        final ArrayAdapter<Quiz> quizArrayAdapter = new ArrayAdapter<Quiz>(this, R.layout.quiz_list_item, R.id.itemQuestion,quizzes)
        {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView questionText = (TextView) view.findViewById(R.id.itemQuestion);
                questionText.setText(quizzes.get(position).getQuestion());

                boolean shouldHighlight = (position == selectedQuizPos);

                int backgroundColor = shouldHighlight ? ContextCompat.getColor(getContext(), R.color.highlightColor) : Color.TRANSPARENT;
                view.setBackgroundColor(backgroundColor);

                return view;
            }
        };
        ListView listView = (ListView)findViewById(R.id.listQuizzes);
        listView.setAdapter(quizArrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedQuiz = quizArrayAdapter.getItem(position);
            selectedQuizPos = position;
            quizArrayAdapter.notifyDataSetChanged();
            enableToolbarButtons(true);
        });

        //TOOLBAR BUTTONS
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Services.clean();
            this.finish();
        });

        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(QuizListInfo.this, QuizCardInfo.class);
            intent.putExtra("course", course);
            intent.putExtra("position", selectedQuizPos);
            startActivity(intent);
        });

        ArrayAdapter<CharSequence> typeArrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.quiz_choices,
                android.R.layout.simple_spinner_dropdown_item
        );

        //EDIT QUIZ BUTTON
        editButton.setOnClickListener(new View.OnClickListener() {
            boolean startup;
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_quiz_add_edit, null);

                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView,
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                startup = false;
                Spinner typeList = popupView.findViewById(R.id.listQuizType);
                typeList.setAdapter(typeArrayAdapter);
                determineSelectedQuizType();
                typeList.setSelection(selectedType);
                typeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(startup)
                        {
                            resetChoices(popupView);
                            changeInputDisplay(popupView, position);
                            selectedType = position;
                        } else {
                            startup = true;
                            changeInputDisplay(popupView, selectedType);
                            setupEdit(popupView);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        changeInputDisplay(popupView, selectedType);
                    }
                });

                Button editButton = popupView.findViewById(R.id.createButton);
                editButton.setText("Edit");
                EditText question = (EditText)popupView.findViewById(R.id.inputQuestion);
                EditText choice1 = (EditText)popupView.findViewById(R.id.inputChoice1);
                EditText choice2 = (EditText)popupView.findViewById(R.id.inputChoice2);
                EditText choice3 = (EditText)popupView.findViewById(R.id.inputChoice3);
                EditText choice4 = (EditText)popupView.findViewById(R.id.inputChoice4);
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if(QuizValidator.validateQuestion(question.getText().toString()) && QuizValidator.validateChoice(choice1.getText().toString())
                            && QuizValidator.validateChoice(choice2.getText().toString()) && QuizValidator.validateChoice(choice3.getText().toString())
                            && QuizValidator.validateChoice(choice4.getText().toString())){
                            editQuiz(popupView);
                            accessQuizzes.updateQuiz(selectedQuiz);
                            quizArrayAdapter.notifyDataSetChanged();
                            popupWindow.dismiss();
                            Toast.makeText(getApplicationContext(), "Quiz Successfully Edited", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            question.setText("");
                            choice1.setText("");
                            choice2.setText("");
                            choice3.setText("");
                            choice4.setText("");
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

        //DELETE QUIZ BUTTON
        deleteButton.setOnClickListener(new View.OnClickListener() {
            //delete
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_delete_course, null);

                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView,
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                TextView confirmationText = (TextView)popupView.findViewById(R.id.deleteCourse);
                confirmationText.setText("Are you sure you want to delete this quiz?");

                Button yesButton = popupView.findViewById(R.id.yesButton);
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        accessQuizzes.deleteQuiz(selectedQuiz);
                        selectedQuizPos = UNSELECTED;
                        quizArrayAdapter.notifyDataSetChanged();
                        enableToolbarButtons(false);
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

        //ADD QUIZ BUTTON
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_quiz_add_edit, null);

                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView,
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                Spinner typeList = popupView.findViewById(R.id.listQuizType);
                typeList.setAdapter(typeArrayAdapter);
                typeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        resetChoices(popupView);
                        changeInputDisplay(popupView, position);
                        selectedType = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        typeList.setSelection(0);
                        changeInputDisplay(popupView, 0);
                    }
                });

                EditText question = (EditText)popupView.findViewById(R.id.inputQuestion);
                EditText choice1 = popupView.findViewById(R.id.inputChoice1);
                EditText choice2 = popupView.findViewById(R.id.inputChoice2);
                EditText choice3 = popupView.findViewById(R.id.inputChoice3);
                EditText choice4 = popupView.findViewById(R.id.inputChoice4);

                //Create a quiz
                Button createButton = popupView.findViewById(R.id.createButton);
                createButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(QuizValidator.validateQuestion(question.getText().toString()) && QuizValidator.validateChoice(choice1.getText().toString())
                                && QuizValidator.validateChoice(choice2.getText().toString()) && QuizValidator.validateChoice(choice3.getText().toString())
                                && QuizValidator.validateChoice(choice4.getText().toString())){
                            Quiz newQuiz = createQuiz(popupView);
                            accessQuizzes.insertQuiz(newQuiz);
                            restartActivity();
                            popupWindow.dismiss();
                            Toast.makeText(getApplicationContext(), "Quiz Successfully Created", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            question.setText("");
                            choice1.setText("");
                            choice2.setText("");
                            choice3.setText("");
                            choice4.setText("");
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
    }

    private void enableToolbarButtons(boolean state)
    {
        ImageButton editButton = findViewById(R.id.editButton);
        ImageButton deleteButton = findViewById(R.id.deleteButton);

        deleteButton.setEnabled(state);
        editButton.setEnabled(state);

        if(!state)
            selectedQuiz = null;
    }

    private Quiz createQuiz(View popupView)
    {
        EditText question = (EditText)popupView.findViewById(R.id.inputQuestion);
        EditText choice1 = popupView.findViewById(R.id.inputChoice1);
        EditText choice2 = popupView.findViewById(R.id.inputChoice2);
        EditText choice3 = popupView.findViewById(R.id.inputChoice3);
        EditText choice4 = popupView.findViewById(R.id.inputChoice4);

        List<String> choices = new ArrayList<>();
        choices.add(choice1.getText().toString());
        choices.add(choice2.getText().toString());
        choices.add(choice3.getText().toString());
        choices.add(choice4.getText().toString());

        String type = setQuizType();
        int correctChoice = setCorrectChoice(popupView);

        return new Quiz(question.getText().toString(), choices, correctChoice, type, course.getTopic(), course.getCourseNum());
    }

    private void restartActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    //Changing Add/Edit popup window
    private void changeInputDisplay(View popupView, int selection)
    {
        switch(selection){
            case 0:
                displayFlashcard(popupView);
                break;
            case 1:
                displayTF(popupView);
                break;
            case 2:
                displayMC(popupView);
                break;
        }
    }

    //Flashcard
    private void displayFlashcard(View popupView)
    {
        EditText choice2 = popupView.findViewById(R.id.inputChoice2);
        EditText choice3 = popupView.findViewById(R.id.inputChoice3);
        EditText choice4 = popupView.findViewById(R.id.inputChoice4);
        RadioButton radio1 = popupView.findViewById(R.id.radioButton1);
        RadioButton radio2 = popupView.findViewById(R.id.radioButton2);
        RadioButton radio3 = popupView.findViewById(R.id.radioButton3);
        RadioButton radio4 = popupView.findViewById(R.id.radioButton4);

        choice2.setVisibility(View.GONE);
        choice3.setVisibility(View.GONE);
        choice4.setVisibility(View.GONE);
        radio1.setVisibility(View.GONE);
        radio2.setVisibility(View.GONE);
        radio3.setVisibility(View.GONE);
        radio4.setVisibility(View.GONE);
    }

    //True or False
    private void displayTF(View popupView)
    {
        EditText trueChoice = popupView.findViewById(R.id.inputChoice1);
        EditText falseChoice = popupView.findViewById(R.id.inputChoice2);
        RadioGroup radioGroup = popupView.findViewById(R.id.radioGroup);
        RadioButton radio1 = popupView.findViewById(R.id.radioButton1);
        RadioButton radio2 = popupView.findViewById(R.id.radioButton2);

        trueChoice.setText(R.string.trueString);
        trueChoice.setEnabled(false);
        falseChoice.setText(R.string.falseString);
        falseChoice.setVisibility(View.VISIBLE);
        falseChoice.setEnabled(false);
        radioGroup.setWeightSum(2);
        radio1.setVisibility(View.VISIBLE);
        radio2.setVisibility(View.VISIBLE);

        //hide other choices
        EditText choice3 = popupView.findViewById(R.id.inputChoice3);
        EditText choice4 = popupView.findViewById(R.id.inputChoice4);
        RadioButton radio3 = popupView.findViewById(R.id.radioButton3);
        RadioButton radio4 = popupView.findViewById(R.id.radioButton4);

        choice3.setVisibility(View.GONE);
        choice4.setVisibility(View.GONE);
        radio3.setVisibility(View.GONE);
        radio4.setVisibility(View.GONE);
    }

    //Multiple Choice
    private void displayMC(View popupView)
    {
        EditText choice1 = popupView.findViewById(R.id.inputChoice1);
        EditText choice2 = popupView.findViewById(R.id.inputChoice2);
        EditText choice3 = popupView.findViewById(R.id.inputChoice3);
        EditText choice4 = popupView.findViewById(R.id.inputChoice4);
        RadioButton radio1 = popupView.findViewById(R.id.radioButton1);
        RadioButton radio2 = popupView.findViewById(R.id.radioButton2);
        RadioButton radio3 = popupView.findViewById(R.id.radioButton3);
        RadioButton radio4 = popupView.findViewById(R.id.radioButton4);

        choice1.setVisibility(View.VISIBLE);
        choice2.setVisibility(View.VISIBLE);
        choice3.setVisibility(View.VISIBLE);
        choice4.setVisibility(View.VISIBLE);
        radio1.setVisibility(View.VISIBLE);
        radio2.setVisibility(View.VISIBLE);
        radio3.setVisibility(View.VISIBLE);
        radio4.setVisibility(View.VISIBLE);

        choice1.setHint("Choice 1");
    }

    private void resetChoices(View popupView)
    {
        EditText choice1 = popupView.findViewById(R.id.inputChoice1);
        EditText choice2 = popupView.findViewById(R.id.inputChoice2);
        EditText choice3 = popupView.findViewById(R.id.inputChoice3);
        EditText choice4 = popupView.findViewById(R.id.inputChoice4);
        RadioGroup radioGroup = popupView.findViewById(R.id.radioGroup);

        choice1.getText().clear();
        choice2.getText().clear();
        choice3.getText().clear();
        choice4.getText().clear();
        //default is R.id.radioButton1
        radioGroup.check(R.id.radioButton1);

        choice1.setHint("Answer");
        //for resetting settings made in displayTF
        choice1.setEnabled(true);
        choice2.setEnabled(true);
        radioGroup.setWeightSum(4);
    }

    private void setupEdit(View popupView)
    {
        List<String> choices = selectedQuiz.getChoices();

        EditText question = (EditText)popupView.findViewById(R.id.inputQuestion);
        question.setText(selectedQuiz.getQuestion());

        EditText choice1 = (EditText)popupView.findViewById(R.id.inputChoice1);
        EditText choice2 = (EditText)popupView.findViewById(R.id.inputChoice2);
        EditText choice3 = (EditText)popupView.findViewById(R.id.inputChoice3);
        EditText choice4 = (EditText)popupView.findViewById(R.id.inputChoice4);

        choice1.setText(choices.get(0));
        choice2.setText(choices.get(1));
        choice3.setText(choices.get(2));
        choice4.setText(choices.get(3));

        RadioGroup radioGroup = popupView.findViewById(R.id.radioGroup);
        radioGroup.check(correctRadioButton());
    }

    private int correctRadioButton()
    {
        int radioID = R.id.radioButton1;
        switch(selectedQuiz.getCorrectChoice()){
            case 1:
                radioID = R.id.radioButton2;
                break;
            case 2:
                radioID = R.id.radioButton3;
                break;
            case 3:
                radioID = R.id.radioButton4;
                break;
        }

        return radioID;
    }

    private int setCorrectChoice(View popupView)
    {
        RadioGroup radioGroup = popupView.findViewById(R.id.radioGroup);
        int correctChoice = 0;
        int chosen = radioGroup.getCheckedRadioButtonId();

        if (chosen == R.id.radioButton2) {
            correctChoice = 1;
        } else if (chosen == R.id.radioButton3) {
            correctChoice = 2;
        } else if (chosen == R.id.radioButton4) {
            correctChoice = 3;
        }

        return correctChoice;
    }

    private void determineSelectedQuizType()
    {
        String type = selectedQuiz.getQuizType();

        switch (type) {
            case FLASHCARD:
                selectedType = 0;
                break;
            case TRUE_OR_FALSE:
                selectedType = 1;
                break;
            case MULTIPLE_CHOICE:
                selectedType = 2;
                break;
        }
    }

    private String setQuizType() {
        String type = "";
        switch(selectedType){
            case 0:
                type = FLASHCARD;
                break;
            case 1:
                type = TRUE_OR_FALSE;
                break;
            case 2:
                type = MULTIPLE_CHOICE;
                break;
        }
        return type;
    }

    private void editQuiz(View popupView)
    {
        EditText question = (EditText)popupView.findViewById(R.id.inputQuestion);
        selectedQuiz.setQuestion(question.getText().toString());

        List<String> choices = new ArrayList<>();
        EditText choice1 = (EditText)popupView.findViewById(R.id.inputChoice1);
        EditText choice2 = (EditText)popupView.findViewById(R.id.inputChoice2);
        EditText choice3 = (EditText)popupView.findViewById(R.id.inputChoice3);
        EditText choice4 = (EditText)popupView.findViewById(R.id.inputChoice4);

        choices.add(choice1.getText().toString());
        choices.add(choice2.getText().toString());
        choices.add(choice3.getText().toString());
        choices.add(choice4.getText().toString());
        selectedQuiz.setChoices(choices);

        int indexAnswer = setCorrectChoice(popupView);
        selectedQuiz.setCorrectChoice(indexAnswer);

        selectedQuiz.setQuizType(setQuizType());
    }
}
