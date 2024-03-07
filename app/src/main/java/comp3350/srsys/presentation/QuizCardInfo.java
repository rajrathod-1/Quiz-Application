package comp3350.srsys.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import comp3350.srsys.R;
import comp3350.srsys.business.AccessQuizzes;
import comp3350.srsys.business.QuizMark;
import comp3350.srsys.objects.Quiz;

public class QuizCardInfo extends Activity {
    private QuizMark quizMark;
    private int position;
    private AccessQuizzes accessQuizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_card);

        accessQuizzes = new AccessQuizzes();

        changeQuizText(accessQuizzes.getNextQuizSequential());
        checkButtons();

        //Change quiz cards
        Button buttonToNextCard = findViewById(R.id.nextCardButton);
        buttonToNextCard.setOnClickListener(v -> {
            position++;
            changeQuizText(accessQuizzes.getNextQuizSequential());
            checkButtons();
        });

        Button buttonToPrevCard = findViewById(R.id.previousCardButton);
        buttonToPrevCard.setOnClickListener(v -> {
            position--;
            changeQuizText(accessQuizzes.getPrevQuizSequential());
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

        Button correctButton = findViewById(R.id.correctButton);
        correctButton.setOnClickListener(v -> {
            showResult(R.id.quizCorrectMark);
            quizMark.setQuestionSeenAt(position);
            quizMark.setQuestionCorrectAt(position);
        });

        Button wrongButton = findViewById(R.id.wrongButton);
        wrongButton.setOnClickListener(v -> {
            showResult(R.id.quizWrongMark);
            quizMark.setQuestionSeenAt(position);
        });

    }

    @Override
    protected void onDestroy() {super.onDestroy();}

    //Change question and answer text
    //Sets answer text to invisible and enables answer button
    private void changeQuizText(Quiz currentQuiz)
    {
        TextView questionText = findViewById(R.id.quizQuestion);
        TextView answerText = findViewById(R.id.quizAnswer);
        questionText.setText(currentQuiz.getQuestion());
        //Only reveal the answer for now
        answerText.setText(currentQuiz.getAnswer());
        if(quizMark == null)
        {
            quizMark = new QuizMark(accessQuizzes.getQuizzes().size());
            position = 0;
        }
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
        Button revealAnswerButton = findViewById(R.id.revealAnswerButton);
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
            revealAnswerButton.setEnabled(false);
            revealAnswerButton.setVisibility(View.INVISIBLE);
        }
        else
        {
            answerText.setVisibility(View.INVISIBLE);
            revealAnswerButton.setEnabled(true);
            revealAnswerButton.setVisibility(View.VISIBLE);
        }
    }

    private void showResult(int id)
    {
        ImageView mark = findViewById(id);
        LinearLayout bottomLayout = findViewById(R.id.bottomLayout);
        mark.setVisibility(View.VISIBLE);
        bottomLayout.setVisibility(View.INVISIBLE);
    }
}
