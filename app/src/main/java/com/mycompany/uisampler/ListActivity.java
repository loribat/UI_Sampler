//
// FILENAME:  ListActivity.java


package com.mycompany.uisampler;

import android.app.FragmentManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


public class ListActivity extends Activity {

    public static final String LOGTAG = "ListActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOGTAG, "Inside onCreate method");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // First, get the DESSERT_SELECTED from the intent
        Intent intent = getIntent();
        int inDessert = intent.getIntExtra(MainActivity.DESSERT, -1);
        Log.i(LOGTAG, "onCreate, inDessert=" + inDessert);

        // Call the list fragment's setListChoice method to select the right dessert in the list
        FragmentManager fm = getFragmentManager();
        MyListFragment lf = (MyListFragment)fm.findFragmentById(R.id.list_activity_listfrag);
        lf.setListChoice(inDessert);

        getActionBar().setHomeButtonEnabled(true);
    } // end onCreate

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Log.i(LOGTAG, "onOptionsItemSelected, user hit the home icon");

                // Destroy this activity & go back to the existing instance
                // of the MainActivity.
                // returnToMain method already handles the back button, so just
                // use that here, too.  returnToMain doesn't use the view, so just
                // send in any old View, hopefully that will work
                View v = findViewById(R.id.listActivityBackButton);
                returnToMain(v);
                return true;
        }

        return super.onOptionsItemSelected(item);
    } // end onOptionsItemSelected


    public void returnToMain(View view) {
        Log.i(LOGTAG, "Inside returnToMain");
        Intent toPassBack = getIntent();
        FragmentManager fm = getFragmentManager();
        MyListFragment listFrag = (MyListFragment)
                fm.findFragmentById((R.id.list_activity_listfrag));
        String choice = listFrag.getSelectedItemText();
        int choiceId = listFrag.myGetId();

        Log.i(LOGTAG, "returnToMain, selected dessert is: " + choice +
                " with id=" + choiceId);

        toPassBack.putExtra("dessertString", choice);
        toPassBack.putExtra(MainActivity.DESSERT, choiceId);
        setResult(RESULT_OK, toPassBack);

        finish();
    } // end returnToMain method

} // end class ListActivity