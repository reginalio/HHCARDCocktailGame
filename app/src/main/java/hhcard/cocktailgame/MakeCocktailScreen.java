package hhcard.cocktailgame;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MakeCocktailScreen extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_makecocktailscreen);

        Intent cocktailToBeMade = getIntent();

        String previousActivity = cocktailToBeMade.getExtras().getString("whichCocktail");

        TextView whatToChooseTextView = (TextView) findViewById(R.id.select_cocktail);

        ImageButton vodkaImageButton = (ImageButton) findViewById(R.id.vodka);
        vodkaImageButton.setOnClickListener(this);
        ImageButton ojImageButton = (ImageButton) findViewById(R.id.orangeJuice);
        ojImageButton.setOnClickListener(this);

        ImageButton iceImageButton = (ImageButton) findViewById(R.id.ice);
        iceImageButton.setOnClickListener(this);

        // choose vodka first, then orange juice
        whatToChooseTextView.setText("Select vodka");



    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vodka:
                Intent intent_vodka = new Intent(this, PourLiquidScreen.class);
                startActivity(intent_vodka);
                break;
            case R.id.orangeJuice:
                Intent intent_oj = new Intent(this, PourLiquidScreen.class);
                startActivity(intent_oj);
                break;
            case R.id.ice:
                Intent intent_ice = new Intent(this, PourLiquidScreen.class);
                startActivity(intent_ice);
                break;
            default:
                break;
        }
    }
}
