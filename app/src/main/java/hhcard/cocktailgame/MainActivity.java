package hhcard.cocktailgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Simple array with a list of my favorite TV shows
        String[] cocktails = {"Screwdriver", "Strawberry Daiquiri", "Margarita", "Pina Colada" };

        // The ListAdapter acts as a bridge between the data and each ListItem
        // You fill the ListView with a ListAdapter. You pass it a context represented by
        // this. A Context provides access to resources you need.
        // android.R.layout.simple_list_item_1 is one of the resources needed.
        // It is a predefined layout provided by Android that stands in as a default

        ListAdapter theAdapter;
        theAdapter = new MyAdapter(this, cocktails);

        // ListViews display data in a scrollable list
        ListView theListView = (ListView) findViewById(R.id.lv_cocktails);

        // Tells the ListView what data to use
        theListView.setAdapter(theAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String cocktailPicked = "You selected " +
                        String.valueOf(adapterView.getItemAtPosition(position));

                Toast.makeText(MainActivity.this, cocktailPicked, Toast.LENGTH_SHORT).show();

                Intent goToMakeCocktail = new Intent(view.getContext(), MakeCocktailScreen.class);
                goToMakeCocktail.putExtra("whichCocktail", String.valueOf(adapterView.getItemAtPosition(position)));
                goToMakeCocktail.putExtra("cocktailNumber", position);
                startActivity(goToMakeCocktail);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        return id == R.id.action_settings || super.onOptionsItemSelected(item);
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SetPreferenceActivity.class);
        startActivityForResult(intent, 0);

        return true;
    }
}