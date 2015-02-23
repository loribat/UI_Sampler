//// FILE:  MainActivity.java

package com.mycompany.uisampler;


import android.app.FragmentManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.text.DateFormatSymbols;
import java.util.List;


public class MainActivity extends Activity {

    public static final String LOGTAG = "MainActivity";

    // Variables for Persistent Storage
    public static final String PREFS_NAME = "MyPrefsFile";
    static final String PREF_DAY = "day";
    static final String PREF_MONTH = "month";
    static final String PREF_YEAR = "year";


    // Extras strings for putting & getting intent data
    public final static String EDIT_TEXT = "com.mycompany.uisampler.EDITTEXT";
    public final static String DESSERT = "com.mycompany.uisampler.DESSERT";
    public final static String INTENT_YEAR = "com.mycompany.uisampler.YEAR";
    public final static String INTENT_MONTH = "com.mycompany.uisampler.MONTH";
    public final static String INTENT_DAY = "com.mycompany.uisampler.DAY";

    // Activity id
    private static final int LIST_ACTIVITY = 123;

    private int curDay, curMonth, curYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
		Log.i(LOGTAG, "Inside onCreate method");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter =
            ArrayAdapter.createFromResource(this, R.array.activities_list,
                    android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Restore state from preferences.  Default = my birthday
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        curDay = settings.getInt(PREF_DAY, 0);
        curMonth = settings.getInt(PREF_MONTH, 0);
        curYear = settings.getInt(PREF_YEAR, 0);

        Log.i(LOGTAG, "onCreate, date in preferences is: " + curMonth + "/" +
                curDay + "/" + curYear);

        // LADOT: TODO: Extra credit is to add the Action Bar stuff
        // getActionBar().setHomeButtonEnabled(true);

    } // end method MainActivity:onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        Log.i(LOGTAG, "Inside onCreateOptionsMenu method");

        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main, menu);
        return(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(LOGTAG, "Inside onResume method");

        // If the previous activity was "Select Date", restore state from preferences.
        // Use my birthday for the default.
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        String spinnerText = mySpinner.getSelectedItem().toString();

        if (spinnerText.equals(getString(R.string.title_activity_date))) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            curDay = settings.getInt(PREF_DAY, 9);
            curMonth = settings.getInt(PREF_MONTH, 10);
            curYear = settings.getInt(PREF_YEAR, 1963);

            DateFormatSymbols df = new DateFormatSymbols();
            String dateString = df.getMonths()[curMonth] + " " + String.valueOf(curDay) + ", " +
                    String.valueOf(curYear);
            Log.i(LOGTAG, "onResume, date in preferences is: " + dateString);

            EditText e = (EditText) findViewById(R.id.mainEditText);
            e.setText(dateString);
        }

    } // onResume


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Log.i(LOGTAG, "Inside onOptionsItemSelected method, id is: " + id);

        switch (id) {
            case R.id.goToDateMenuItem:
                Log.i(LOGTAG, "onOptionsItemSelected, user chose date");
                commonLauncher(DateActivity.class);
                return true;
            case R.id.goToDessertMenuItem:
                Log.i(LOGTAG, "onOptionsItemSelected, user chose dessert");
                commonLauncher(ListActivity.class);
                return true;
            case R.id.goToKeyboardMenuItem:
                Log.i(LOGTAG, "onOptionsItemSelected, user chose text");
                commonLauncher(KeyboardActivity.class);
                return true;
        } // end switch on menu item id

        return super.onOptionsItemSelected(item);
    } // end MainActivity:onOptionsItemSelected method


    public void commonLauncher(Class destClass) {
        Log.i(LOGTAG, "Inside commonLauncher");

        // Create the intent for the selected activity
        Intent intent = new Intent(this, destClass);

        // Get the text that's inside the EditText widget & pass it to the intent
        EditText eWidget = (EditText) findViewById(R.id.mainEditText);
        String editContents = eWidget.getText().toString();
        Log.i(LOGTAG, "commonLauncher, edit text is: " + editContents);
        intent.putExtra(EDIT_TEXT, editContents);

        if (destClass == ListActivity.class) {
            FragmentManager fm = getFragmentManager();
            MyListFragment listFrag =
                    (MyListFragment) fm.findFragmentById((R.id.main_activity_listfrag));
            int choiceId = listFrag.myGetId();
            intent.putExtra(DESSERT, choiceId);
            startActivityForResult(intent, LIST_ACTIVITY);
        }

        else if (destClass == DateActivity.class) {
            intent.putExtra(INTENT_DAY, curDay);
            intent.putExtra(INTENT_MONTH, curMonth);
            intent.putExtra(INTENT_YEAR, curYear);
            startActivity(intent);
        }

        else {
            startActivity(intent);
        }
    } // end commonLauncher


    // this method is called when the user clicks the "Go" button on the Main Activity screen
    public void launchActivity(View v) {
		Log.i(LOGTAG, "Inside launchActivity method");

        // Get the name of the activity selected in the Spinner
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        String spinnerText = mySpinner.getSelectedItem().toString();
        Log.i(LOGTAG, "launchActivity, spinner text is: " + spinnerText);


        // Now we need to:
        // 1.  Activate the selected activity
        // 2.  Pass the text from the EditText field to the selected Activity
        // 3.  Pass the selected dessert to the List Activity

        // Set the destination class to be the Activity chosen in the spinner
        Class destClass = MainActivity.class;
        if (spinnerText.equals(getString(R.string.title_activity_date)))
            destClass = DateActivity.class;
        else if (spinnerText.equals(getString(R.string.title_activity_keyboard)))
            destClass = KeyboardActivity.class;
		else if (spinnerText.equals(getString(R.string.title_activity_list)))
            destClass = ListActivity.class;

        commonLauncher(destClass);


    } // end method launchActivity


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Did the Activity fail?
        if (resultCode != RESULT_OK) {
            Log.i(LOGTAG, "onActivityResult: resultCode is bad (" + resultCode + ")");
            return;
        }

        // Check that we're getting a result back from the List Activity
        if (requestCode == LIST_ACTIVITY) {
            String newEditText = data.getStringExtra("dessertString");
            Log.i(LOGTAG, "onActivityResult: newEditText is: " + newEditText);
            EditText eWidget = (EditText) findViewById(R.id.mainEditText);
            eWidget.setText(newEditText);

            int p = data.getIntExtra(DESSERT, 0);
            Log.i(LOGTAG, "onActivityResult: dessertId is: " + p);

            FragmentManager fm = getFragmentManager();
            MyListFragment listFrag = (MyListFragment)
                    fm.findFragmentById((R.id.main_activity_listfrag));
            listFrag.setListChoice(p);
        } // end if we are handing the result from the LIST Activity

        else {
            Log.i(LOGTAG, "onActivityResult, requestCode is bad (" + requestCode + ")");
        }
    } // end method onActivityResult

} // end class MainActivity
