package hhcard.cocktailgame;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MakeCocktailScreen extends Activity implements View.OnClickListener{
    int state;
    int endState;
    String[] ingredient = {"Vodka", "Orange Juice", "Cointreau", "Strawberry Juice",
                            "Lime Juice", "Pineapple Juice", "White Rum", "Tequila"};
    int[] recipe = {0,1,3,6,2,7,5,6};
    int[] ml = {10,30,30,10,20,20,30,10};
    String cocktailChosen;
    int cocktailNumber;

    TextView youAreMaking;
    int amountAlreadyInCup = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_makecocktailscreen);

        Intent cocktailToBeMade = getIntent();
        cocktailChosen = cocktailToBeMade.getExtras().getString("whichCocktail");
        cocktailNumber = cocktailToBeMade.getExtras().getInt("cocktailNumber");
        youAreMaking = (TextView) findViewById(R.id.cocktail_chosen);

        state = cocktailNumber*2;
        endState = state + 2;
        youAreMaking.setText("Let's make " + cocktailChosen + "\nAdd " + ml[state] + "ml of "+ingredient[recipe[state]]);

        ImageButton vodkaImageButton = (ImageButton) findViewById(R.id.vodka);
        vodkaImageButton.setOnClickListener(this);
        ImageButton ojImageButton = (ImageButton) findViewById(R.id.orangeJuice);
        ojImageButton.setOnClickListener(this);

        ImageButton cointreauImageButton = (ImageButton) findViewById(R.id.cointreau);
        cointreauImageButton.setOnClickListener(this);
        ImageButton sjImageButton = (ImageButton) findViewById(R.id.strawberry_juice);
        sjImageButton.setOnClickListener(this);

        ImageButton ljImageButton = (ImageButton) findViewById(R.id.lime_juice);
        ljImageButton.setOnClickListener(this);
        ImageButton pjImageButton = (ImageButton) findViewById(R.id.pineapple_juice);
        pjImageButton.setOnClickListener(this);

        ImageButton rumImageButton = (ImageButton) findViewById(R.id.light_rum);
        rumImageButton.setOnClickListener(this);
        ImageButton tequilaImageButton = (ImageButton) findViewById(R.id.tequila);
        tequilaImageButton.setOnClickListener(this);


    }

    public void onClick(View v) {
        final int result = 1;

        switch (v.getId()) {
            case R.id.vodka:
                if (recipe[state] == 0) {
                    Toast.makeText(MakeCocktailScreen.this, "Getting "+ingredient[recipe[state]],Toast.LENGTH_SHORT);
                    Intent intent_pour = new Intent(this, PourLiquidScreen.class);
                    intent_pour.putExtra("amountToPour",ml[state]);
                    intent_pour.putExtra("amountAlreadyInCup", amountAlreadyInCup);
                    startActivityForResult(intent_pour, result);
                }else
                    Toast.makeText(MakeCocktailScreen.this, "This is not "+ ingredient[recipe[state]] + "!",Toast.LENGTH_SHORT).show();

                break;
            case R.id.orangeJuice:
                if(recipe[state] == 1) {
                    Toast.makeText(MakeCocktailScreen.this, "Getting "+ingredient[recipe[state]],Toast.LENGTH_SHORT);
                    Intent intent_pour = new Intent(this, PourLiquidScreen.class);
                    intent_pour.putExtra("amountToPour",ml[state]);
                    intent_pour.putExtra("amountAlreadyInCup", amountAlreadyInCup);
                    startActivityForResult(intent_pour, result);
                }else
                    Toast.makeText(MakeCocktailScreen.this, "This is not "+ ingredient[recipe[state]] + "!",Toast.LENGTH_SHORT).show();

                break;
            case R.id.cointreau:
                if(recipe[state] == 2) {
                    Toast.makeText(MakeCocktailScreen.this, "Getting "+ingredient[recipe[state]],Toast.LENGTH_SHORT);
                    Intent intent_pour = new Intent(this, PourLiquidScreen.class);
                    intent_pour.putExtra("amountToPour", ml[state]);
                    intent_pour.putExtra("amountAlreadyInCup", amountAlreadyInCup);
                    startActivityForResult(intent_pour, result);
                }else
                    Toast.makeText(MakeCocktailScreen.this, "This is not "+ ingredient[recipe[state]] + "!",Toast.LENGTH_SHORT).show();

                break;
            case R.id.strawberry_juice:
                if(recipe[state] == 3) {
                    Toast.makeText(MakeCocktailScreen.this, "Getting "+ingredient[recipe[state]],Toast.LENGTH_SHORT);
                    Intent intent_oj = new Intent(this, PourLiquidScreen.class);
                    intent_oj.putExtra("amountToPour",ml[state]);
                    intent_oj.putExtra("amountAlreadyInCup", amountAlreadyInCup);
                    startActivityForResult(intent_oj, result);
                }else
                    Toast.makeText(MakeCocktailScreen.this, "This is not "+ ingredient[recipe[state]] + "!",Toast.LENGTH_SHORT).show();

                break;
            case R.id.lime_juice:
                if(recipe[state] == 4) {
                    Toast.makeText(MakeCocktailScreen.this, "Getting "+ingredient[recipe[state]],Toast.LENGTH_SHORT);
                    Intent intent_oj = new Intent(this, PourLiquidScreen.class);
                    intent_oj.putExtra("amountToPour",ml[state]);
                    intent_oj.putExtra("amountAlreadyInCup", amountAlreadyInCup);
                    startActivityForResult(intent_oj, result);
                }else
                    Toast.makeText(MakeCocktailScreen.this, "This is not "+ ingredient[recipe[state]] + "!",Toast.LENGTH_SHORT).show();

                break;
            case R.id.pineapple_juice:
                if(recipe[state] == 5) {
                    Toast.makeText(MakeCocktailScreen.this, "Getting "+ingredient[recipe[state]],Toast.LENGTH_SHORT);
                    Intent intent_oj = new Intent(this, PourLiquidScreen.class);
                    intent_oj.putExtra("amountToPour",ml[state]);
                    intent_oj.putExtra("amountAlreadyInCup", amountAlreadyInCup);
                    startActivityForResult(intent_oj, result);
                }else
                    Toast.makeText(MakeCocktailScreen.this, "This is not "+ ingredient[recipe[state]] + "!",Toast.LENGTH_SHORT).show();

                break;
            case R.id.light_rum:
                if(recipe[state] == 6) {
                    Toast.makeText(MakeCocktailScreen.this, "Getting "+ingredient[recipe[state]],Toast.LENGTH_SHORT);
                    Intent intent_oj = new Intent(this, PourLiquidScreen.class);
                    intent_oj.putExtra("amountToPour",ml[state]);
                    intent_oj.putExtra("amountAlreadyInCup", amountAlreadyInCup);
                    startActivityForResult(intent_oj, result);
                }else
                    Toast.makeText(MakeCocktailScreen.this, "This is not "+ ingredient[recipe[state]] + "!",Toast.LENGTH_SHORT).show();

                break;
            case R.id.tequila:
                if(recipe[state] == 7) {
                    Toast.makeText(MakeCocktailScreen.this, "Getting "+ingredient[recipe[state]],Toast.LENGTH_SHORT);
                    Intent intent_oj = new Intent(this, PourLiquidScreen.class);
                    intent_oj.putExtra("amountToPour",ml[state]);
                    intent_oj.putExtra("amountAlreadyInCup", amountAlreadyInCup);
                    startActivityForResult(intent_oj, result);
                }else
                    Toast.makeText(MakeCocktailScreen.this, "This is not "+ ingredient[recipe[state]] + "!",Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        amountAlreadyInCup = data.getIntExtra("pouredAmount", 0);
        state++;
        if(state < endState) {
            youAreMaking.setText("Now let's add " + ml[state] + "ml of " + ingredient[recipe[state]]);
        }else{
            Intent shakeIt = new Intent(this, ShakeItScreen.class);
            shakeIt.putExtra("whichCocktail", cocktailChosen);
            shakeIt.putExtra("cocktailNumber", cocktailNumber);
            startActivity(shakeIt);

        }
    }


}
