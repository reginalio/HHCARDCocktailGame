package hhcard.cocktailgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Regina on 10/03/2015.
 */
public class MakeCocktailScreen extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_makecocktailscreen);

        Intent activityThatCalled = getIntent();

        String previousActivity = activityThatCalled.getExtras().getString("calling_activity");
    }
}
