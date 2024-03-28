package comp3350.srsys.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comp3350.srsys.R;
import comp3350.srsys.business.AccessQuizzes;
import comp3350.srsys.business.QuizMark;
import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Quiz;

public class QuizCardInfo extends Activity {
    private QuizMark quizMark;
    private int position;
    private Quiz currentQuiz;
    private AccessQuizzes accessQuizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_card);

        Class<? extends Course> clazz = Course.class;
        Course course = getIntent().getSerializableExtra("course", clazz);
        position = getIntent().getIntExtra("position", 0);

        accessQuizzes = new AccessQuizzes(course);
        List<Quiz> quizzes = accessQuizzes.getQuizzes();

        if(position < 0 || position >= quizzes.size())
        {
            position = 0;
        }

        accessQuizzes.setCurrentQuiz(position);
        quizMark = new QuizMark(quizzes.size());
        currentQuiz = quizzes.get(position);
        changeQuizText();
        checkButtons();

        //Change quiz cards
        Button buttonToNextCard = findViewById(R.id.nextCardButton);
        buttonToNextCard.setOnClickListener(v -> {
            position++;
            currentQuiz = accessQuizzes.getNextQuizSequential();
            changeQuizText();
            checkButtons();
        });

        Button buttonToPrevCard = findViewById(R.id.previousCardButton);
        buttonToPrevCard.setOnClickListener(v -> {
            position--;
            currentQuiz = accessQuizzes.getPrevQuizSequential();
            changeQuizText();
            checkButtons();
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> this.finish());

        Button revealAnswerButton = findViewById(R.id.revealAnswerButton);
        revealAnswerButton.setOnClickListener(v -> {
            TextView answerText = findViewById(R.id.quizAnswer);
            answerText.setVisibility(View.VISIBLE);
            revealAnswerButton.setEnabled(false);
            askResult();
        });

        Button choice1 = findViewById(R.id.choice1);
        choice1.setOnClickListener(v -> {
            quizMark.checkChoice(0, currentQuiz.getCorrectChoice(), position);
            hideResult();
        });

        Button choice2 = findViewById(R.id.choice2);
        choice2.setOnClickListener(v -> {
            quizMark.checkChoice(1, currentQuiz.getCorrectChoice(), position);
            hideResult();
        });

        Button choice3 = findViewById(R.id.choice3);
        choice3.setOnClickListener(v -> {
            quizMark.checkChoice(2, currentQuiz.getCorrectChoice(), position);
            hideResult();
        });

        Button choice4 = findViewById(R.id.choice4);
        choice4.setOnClickListener(v -> {
            quizMark.checkChoice(3, currentQuiz.getCorrectChoice(), position);
            hideResult();
        });
    }

    @Override
    protected void onDestroy() {super.onDestroy();}

    //Change question and answer text
    //Sets answer text to invisible and enables answer button
    private void changeQuizText()
    {
        TextView questionText = findViewById(R.id.quizQuestion);
        TextView answerText = findViewById(R.id.quizAnswer);
        questionText.setText(currentQuiz.getQuestion());
        answerText.setText(currentQuiz.getAnswer());
        hideResult();
    }

    private void checkButtons()
    {
        disableNextButton(accessQuizzes.endOfQuizzes());
        disablePrevButton(accessQuizzes.startOfQuizzes());
    }

    //Disable next quiz button if there are no more quizzes left
    private void disableNextButton(boolean endOfQuiz)
    {
        Button buttonToNextCard = findViewById(R.id.nextCardButton);
        buttonToNextCard.setEnabled(!endOfQuiz);
    }

    private void disablePrevButton(boolean startOfQuiz)
    {
        Button buttonToPrevCard = findViewById(R.id.previousCardButton);
        buttonToPrevCard.setEnabled(!startOfQuiz);
    }

    private void askResult()
    {
        Button revealAnswerButton = findViewById(R.id.revealAnswerButton);
        LinearLayout askResult = findViewById(R.id.bottomLayout);

        revealAnswerButton.setVisibility(View.INVISIBLE);
        askResult.setVisibility(View.VISIBLE);
    }

    private void hideResult()
    {
        LinearLayout askResult = findViewById(R.id.bottomLayout);
        ImageView correctMark = findViewById(R.id.quizCorrectMark);
        ImageView wrongMark = findViewById(R.id.quizWrongMark);
        TextView answerText = findViewById(R.id.quizAnswer);

        askResult.setVisibility(View.INVISIBLE);
        correctMark.setVisibility(View.INVISIBLE);
        wrongMark.setVisibility(View.INVISIBLE);

        if(quizMark.questionAlreadySeen(position))
        {
            if(quizMark.gotQuestionCorrectAt(position))
            {
                showResult(R.id.quizCorrectMark);
            }
            else
            {
                showResult(R.id.quizWrongMark);
            }

            answerText.setVisibility(View.VISIBLE);
        }
        else
        {
            answerText.setVisibility(View.INVISIBLE);
            displayQuiz();
        }
    }

    private void showResult(int id)
    {
        ImageView mark = findViewById(id);
        LinearLayout bottomLayout = findViewById(R.id.bottomLayout);
        mark.setVisibility(View.VISIBLE);
        bottomLayout.setVisibility(View.INVISIBLE);
    }

    private void displayQuiz()

    {
        final String FLASHCARD = "FlashCard";
        final String TRUE_OR_FALSE = "TrueOrFalse";
        final String MULTIPLE_CHOICE = "MC";

        switch(currentQuiz.getQuizType())
        {
            case FLASHCARD:
                displayFlashcard();
                break;
            case TRUE_OR_FALSE:
                displayTF();
                break;
            case MULTIPLE_CHOICE:
                displayMC();
                break;
        }
    }

    private void displayFlashcard()
    {
        Button revealAnswerButton = findViewById(R.id.revealAnswerButton);
        LinearLayout moreButtons = findViewById(R.id.moreButtons);
        Button choice1 = findViewById(R.id.choice1);
        Button choice2 = findViewById(R.id.choice2);
        TextView prompt = findViewById(R.id.textPrompt);

        revealAnswerButton.setEnabled(true);
        revealAnswerButton.setVisibility(View.VISIBLE);
        moreButtons.setVisibility(View.GONE);

        choice1.setText(R.string.yes);
        choice2.setText(R.string.no);
        prompt.setText(R.string.did_you_answer_correctly);
    }

    private void displayTF()
    {
        LinearLayout bottomLayout = findViewById(R.id.bottomLayout);
        LinearLayout moreButtons = findViewById(R.id.moreButtons);
        List<String> choices = currentQuiz.getChoices();
        Button choice1 = findViewById(R.id.choice1);
        Button choice2 = findViewById(R.id.choice2);
        TextView prompt = findViewById(R.id.textPrompt);

        bottomLayout.setVisibility(View.VISIBLE);
        moreButtons.setVisibility(View.GONE);

        choice1.setText(choices.get(0));
        choice2.setText(choices.get(1));
        prompt.setText(R.string.true_or_false);
    }

    private void displayMC()
    {
        LinearLayout bottomLayout = findViewById(R.id.bottomLayout);
        LinearLayout moreButtons = findViewById(R.id.moreButtons);
        List<String> choices = currentQuiz.getChoices();
        Button choice1 = findViewById(R.id.choice1);
        Button choice2 = findViewById(R.id.choice2);
        Button choice3 = findViewById(R.id.choice3);
        Button choice4 = findViewById(R.id.choice4);
        TextView prompt = findViewById(R.id.textPrompt);

        bottomLayout.setVisibility(View.VISIBLE);
        moreButtons.setVisibility(View.VISIBLE);

        choice1.setText(choices.get(0));
        choice2.setText(choices.get(1));
        choice3.setText(choices.get(2));
        choice4.setText(choices.get(3));
        prompt.setText(R.string.mc_prompt);
    }
}
