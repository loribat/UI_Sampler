// FILENAME:  DateActivity.java

package com.mycompany.uisampler;

import android.app.DialogFragment;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;

public class DateActivity extends Activity
        implements SaveDateConfirmDialogFragment.SaveDateDialogListener {

    public static final String LOGTAG = "DateActivity";

    // private member variables to store the current user-selected date
    private int curYear, curMonth, curDay;
    private String curDateString;

	@Override
 	public void onCreate(Bundle savedInstanceState) {
		Log.i(LOGTAG, "Inside onCreate method");
  		super.onCreate(savedInstanceState);
  		setContentView(R.layout.activity_date);

        // First, get the edit text from the intent.  Use my birthday for the default.
        Intent intent = getIntent();
        curDay = intent.getIntExtra(MainActivity.INTENT_DAY, 10);
        curMonth = intent.getIntExtra(MainActivity.INTENT_MONTH, 9);
        curYear = intent.getIntExtra(MainActivity.INTENT_YEAR, 1963);

        // IF no date saved in permanent storage, initialize the date picker to my birthday
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        dp.updateDate(curYear, curMonth, curDay);
        curDateString = getMonth(curMonth) + " " + String.valueOf(curDay) + ", " +
                String.valueOf(curYear);
        TextView tv = (TextView) findViewById(R.id.tvDate);
        tv.setText(curDateString);

        // Add this line so that the home button will work
        getActionBar().setHomeButtonEnabled(true);
    } // end onCreate


    // Little helper method to format the month nicely
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    } // getMonth


    // This method will be called when the pick date button is clicked
    public void changeDateButtonClicked(View v) {
        Log.i(LOGTAG, "Inside changeDateButtonClicked");

        // Display a confirmation dialog, allow user to say yes or now
        DialogFragment newFragment = new SaveDateConfirmDialogFragment();
        newFragment.show(getFragmentManager(), "saveDate");
    } // changeDateButtonClicked


    @Override
    public void onDialogSaveDateYes(DialogFragment dialog) {
        Log.i(LOGTAG, "Inside onDateSaveDialogYes");

        // Get the date from datePicker widget
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        curYear = dp.getYear();
        curMonth = dp.getMonth();
        curDay = dp.getDayOfMonth();

        curDateString = getMonth(curMonth) + " " + String.valueOf(curDay) + ", " +
                String.valueOf(curYear);
        Log.i(LOGTAG, "onDateSaveDialogYes, newDate is: " + curDateString);

        // Stuff the date in the tvDate TextEdit widget
        TextView tv = (TextView) findViewById(R.id.tvDate);
        tv.setText(curDateString);

        // Save the current date info in persistent storage (preferences)
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(MainActivity.PREF_DAY, curDay);
        editor.putInt(MainActivity.PREF_MONTH, curMonth);
        editor.putInt(MainActivity.PREF_YEAR, curYear);
        editor.commit();

        Toast.makeText(getApplicationContext(), "Date Saved", Toast.LENGTH_SHORT).show();
    } // onDateSaveDialogYes


    @Override
    public void onDialogSaveDateNo(DialogFragment dialog) {
        Log.i(LOGTAG, "Inside onDialogSaveDateNo");

        // Set the DatePicker widget back to the current user-selected date
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        dp.updateDate(curYear, curMonth, curDay);
    } // onDialogSaveDateNo


 	@Override
 	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i(LOGTAG, "Inside onCreateOptionsMenu method");
  		getMenuInflater().inflate(R.menu.menu_date, menu);
  		return true;
 	} // onCreateOptionsMenu


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
                // Date Activity doesn't return anything to the caller anyway
                // so just call finish.
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

} // end DateActivity Class
