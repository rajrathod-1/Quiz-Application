package comp3350.srsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import comp3350.srsys.R;
import comp3350.srsys.business.AccessQuizzes;
import comp3350.srsys.objects.Quiz;

public class QuizCardInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizcard);

        AccessQuizzes accessQuizzes = new AccessQuizzes();
        changeQuizText(accessQuizzes.getNextQuizSequential());
        checkButtons(accessQuizzes);

        //Change quiz cards
        Button buttonToNextCard = findViewById(R.id.nextCardButton);
        buttonToNextCard.setOnClickListener(v -> {
            changeQuizText(accessQuizzes.getNextQuizSequential());
            checkButtons(accessQuizzes);
        });

        Button buttonToPrevCard = findViewById(R.id.previousCardButton);
        buttonToPrevCard.setOnClickListener(v -> {
            changeQuizText(accessQuizzes.getPrevQuizSequential());
            checkButtons(accessQuizzes);
        });

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(QuizCardInfo.this, ClassesInfo.class);
            startActivity(intent);
        });

        Button revealAnswerButton = findViewById(R.id.revealAnswerButton);
        revealAnswerButton.setOnClickListener(v -> {
            TextView answerText = (TextView)findViewById(R.id.quizAnswer);
            answerText.setVisibility(View.VISIBLE);
            revealAnswerButton.setEnabled(false);
        });

    }

    @Override
    protected void onDestroy() {super.onDestroy();}

    //Change question and answer text
    //Sets answer text to invisible and enables answer button
    private void changeQuizText(Quiz currentQuiz)
    {
        TextView questionText = (TextView)findViewById(R.id.quizQuestion);
        TextView answerText = (TextView)findViewById(R.id.quizAnswer);
        Button revealAnswerButton = findViewById(R.id.revealAnswerButton);
        questionText.setText(currentQuiz.getQuestion());
        //Only reveal the answer for now
        answerText.setText(currentQuiz.getAnswer());
        answerText.setVisibility(View.INVISIBLE);
        revealAnswerButton.setEnabled(true);
    }

    private void checkButtons(AccessQuizzes accessQuizzes)
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
}
