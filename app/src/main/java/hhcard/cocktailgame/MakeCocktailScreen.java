package hhcard.cocktailgame;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MakeCocktailScreen extends Activity implements View.OnClickListener{
    int state = 0;
    String[] ingredient = {"Vodka", "Orange Juice"};
    TextView youAreMaking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_makecocktailscreen);

        Intent cocktailToBeMade = getIntent();
        String previousActivity = cocktailToBeMade.getExtras().getString("whichCocktail");
        int cocktailNumber = cocktailToBeMade.getExtras().getInt("cocktailNumber");
        youAreMaking = (TextView) findViewById(R.id.cocktail_chosen);
        youAreMaking.setText("Let's make " + previousActivity + "\nChoose " + ingredient[state]);

       // TextView whatToChooseTextView = (TextView) findViewById(R.id.select_cocktail);

        ImageButton vodkaImageButton = (ImageButton) findViewById(R.id.vodka);
        vodkaImageButton.setOnClickListener(this);
        ImageButton ojImageButton = (ImageButton) findViewById(R.id.orangeJuice);
        ojImageButton.setOnClickListener(this);

//        ImageButton iceImageButton = (ImageButton) findViewById(R.id.ice);
//        iceImageButton.setOnClickListener(this);

        // choose vodka first, then orange juice
//        whatToChooseTextView.setText("Select vodka");

    }

    public void onClick(View v) {
        final int result = 1;

        switch (v.getId()) {
            case R.id.vodka:
                if (state == 0) {
                    Intent intent_vodka = new Intent(this, PourLiquidScreen.class);
                    intent_vodka.putExtra("amountToPour",10);
                    startActivityForResult(intent_vodka, result);
                }else
                    Toast.makeText(MakeCocktailScreen.this, "This is not "+ ingredient[state] + "!",Toast.LENGTH_SHORT).show();

                break;
            case R.id.orangeJuice:
                if(state == 1) {
                    Intent intent_oj = new Intent(this, PourLiquidScreen.class);
                    intent_oj.putExtra("amountToPour",30);
                    startActivityForResult(intent_oj, result);
                }else
                    Toast.makeText(MakeCocktailScreen.this, "This is not "+ ingredient[state] + "!",Toast.LENGTH_SHORT).show();

                break;
//            case R.id.ice:
//                Intent intent_ice = new Intent(this, PourLiquidScreen.class);
//                startActivity(intent_ice);
//                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        state++;
        if(state<2) {
            youAreMaking.setText("Now let's choose " + ingredient[state]);
        }else{
            Intent shakeIt = new Intent(this, ShakeItScreen.class);
            startActivity(shakeIt);

        }
    }


}
