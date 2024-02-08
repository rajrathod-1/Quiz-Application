package comp3350.srsys.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import comp3350.srsys.R;

public class QuizCardInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizcard);

        Button buttonToNextCard = findViewById(R.id.nextCardButton);
        buttonToNextCard.setOnClickListener(v -> {
            Intent intent = new Intent(QuizCardInfo.this, QuizCardInfo.class);
            startActivity(intent);
        });

        Button buttonToPrevCard = findViewById(R.id.previousCardButton);
        buttonToPrevCard.setOnClickListener(v -> {
            Intent intent = new Intent(QuizCardInfo.this, QuizCardInfo.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {super.onDestroy();}
}
