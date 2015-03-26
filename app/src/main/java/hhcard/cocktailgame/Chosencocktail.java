package hhcard.cocktailgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by chenuka on 26/03/2015.
 */

public class Chosencocktail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congratulations_screen);

        Button play_again_button=(Button) findViewById(R.id.play_again_button);
        play_again_button.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View view){


    }


    }
