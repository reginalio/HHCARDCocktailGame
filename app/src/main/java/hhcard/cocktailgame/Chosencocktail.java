package hhcard.cocktailgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Chosencocktail extends Activity {

    int[] imageResource = {R.drawable.electric_screwdriver_final, R.drawable.strawberry_daiquiri_final,
            R.drawable.margherita_final, R.drawable.pinacolada_final};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congratulations_screen);
        Intent finishScreen = getIntent();
        String cocktailChosen = finishScreen.getExtras().getString("whichCocktail");
        int cocktailNumber = finishScreen.getExtras().getInt("cocktailNumber");

        Button play_again_button=(Button) findViewById(R.id.play_again_button);
        ImageView cocktailMadeImageView = (ImageView)findViewById(R.id.youMadeImageView);

        cocktailMadeImageView.setImageResource(imageResource[cocktailNumber]);

        TextView congratsTextView = (TextView)findViewById(R.id.congratsTextView);
        congratsTextView.setText("Congratulations! \nYou have made " + cocktailChosen);
//        play_again_button.setOnClickListener((View.OnClickListener) this);


    }

    public void restart_game(View view) {
        Intent goBackToMain = new Intent(this, MainActivity.class);
        Log.d("Finish", "Going back to main");
        startActivity(goBackToMain);
    }

}
