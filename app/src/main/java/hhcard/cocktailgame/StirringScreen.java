package hhcard.cocktailgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class StirringScreen extends Activity {

    Button goToFinishButton;
    String cocktailChosen;
    int cocktailNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stir);
        Intent stirScreen = getIntent();
        cocktailChosen = stirScreen.getExtras().getString("whichCocktail");
        cocktailNumber = stirScreen.getExtras().getInt("cocktailNumber");

        goToFinishButton = (Button)findViewById(R.id.goToFinishButton);
        goToFinishButton.setVisibility(View.GONE);

        final TextView countDownTextView = (TextView) findViewById(R.id.countDownTextView);

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                countDownTextView.setText(String.valueOf(millisUntilFinished/1000));
            }

            public void onFinish() {
                countDownTextView.setText("done!");
                goToFinishButton.setVisibility(View.VISIBLE);


            }
        }.start();

    }

    public void goToFinish(View view) {
        Intent goToFinishScreen = new Intent(this, Chosencocktail.class);
        goToFinishScreen.putExtra("whichCocktail", cocktailChosen);
        goToFinishScreen.putExtra("cocktailNumber", cocktailNumber);
        startActivity(goToFinishScreen);
    }
}
