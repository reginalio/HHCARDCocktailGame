package hhcard.cocktailgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class Chosencocktail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congratulations_screen);

        Button play_again_button=(Button) findViewById(R.id.play_again_button);
//        play_again_button.setOnClickListener((View.OnClickListener) this);


    }

    public void restart_game(View view) {
        Intent goBackToMain = new Intent(this, MainActivity.class);
        Log.d("Finish", "Going back to main");
        startActivity(goBackToMain);
    }
//
//    public void onClick(View view){
//        Intent goBackToMain = new Intent(this, MainActivity.class);
//        Log.d("Finish", "Going back to main");
//        startActivity(goBackToMain);
//    }

}
