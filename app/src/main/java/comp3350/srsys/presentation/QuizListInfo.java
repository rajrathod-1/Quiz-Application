package comp3350.srsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import comp3350.srsys.R;
import comp3350.srsys.business.AccessQuizzes;
import comp3350.srsys.objects.Quiz;

public class QuizListInfo extends Activity{
    private Quiz selected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        AccessQuizzes accessQuizzes = new AccessQuizzes();
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

                return view;
            }
        };
        ListView listView = (ListView)findViewById(R.id.listQuizzes);
        listView.setAdapter(quizArrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selected = quizArrayAdapter.getItem(position);
            enableToolbarButtons(true);
        });

        //TOOLBAR BUTTONS
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> this.finish());

        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(QuizListInfo.this, QuizCardInfo.class);
            startActivity(intent);
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_quiz_add_edit, null);

                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView,
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                EditText question = (EditText)popupView.findViewById(R.id.inputQuestion);
                question.setText(selected.getQuestion());
                EditText answer = (EditText)popupView.findViewById(R.id.inputAnswer);
                answer.setText(selected.getAnswer());

                Button editButton = popupView.findViewById(R.id.createButton);
                editButton.setText("Edit");
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Quiz quiz = createQuiz(popupView);
                        selected.setQuestion(quiz.getQuestion());
                        selected.setAnswer(quiz.getAnswer());
                        accessQuizzes.updateQuiz(quiz);
                        quizArrayAdapter.notifyDataSetChanged();
                        popupWindow.dismiss();
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
                        accessQuizzes.deleteQuiz(selected);
                        quizArrayAdapter.notifyDataSetChanged();
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

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_quiz_add_edit, null);

                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView,
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                //Create a quiz
                Button createButton = popupView.findViewById(R.id.createButton);
                createButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Quiz newQuiz = createQuiz(popupView);
                        accessQuizzes.insertQuiz(newQuiz);
                        quizArrayAdapter.notifyDataSetChanged();
                        popupWindow.dismiss();
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
            selected = null;
    }

    private Quiz createQuiz(View popupView)
    {
        final int NUM_CHOICES = 4;
        EditText question = (EditText)popupView.findViewById(R.id.inputQuestion);
        List<String> choices = new ArrayList<>();

        //default single answer quiz
        EditText answer = (EditText)popupView.findViewById(R.id.inputAnswer);
        choices.add(answer.getText().toString());
        for(int i = 1; i < NUM_CHOICES; i++)
        {
            choices.add("");
        }
        int correctChoice = 0;

        return new Quiz(question.getText().toString(), choices, correctChoice);
    }
}
