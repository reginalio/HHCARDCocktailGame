package hhcard.cocktailgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MakeCocktailScreen extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_makecocktailscreen);

        Intent cocktailToBeMade = getIntent();

        String previousActivity = cocktailToBeMade.getExtras().getString("whichCocktail");
    }
}
