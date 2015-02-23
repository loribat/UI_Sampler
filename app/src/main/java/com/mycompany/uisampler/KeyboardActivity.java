//
// FILENAME:  KeyboardActivity.java

package com.mycompany.uisampler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;


public class KeyboardActivity extends Activity{

    public static final String LOGTAG = "KeyboardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		Log.i(LOGTAG, "Inside onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

		// We have to display the text that was passed in from the 
		// Main Activity in the first Edit Text field

        // First, get the edit text from the intent
        Intent intent = getIntent();
        String editText = intent.getStringExtra(MainActivity.EDIT_TEXT);

		if (!editText.isEmpty()) {
            Log.i(LOGTAG, "onCreate, editText is: " + editText);

            // Next, set the text of the first EditText field
            EditText eWidget = (EditText) this.findViewById(R.id.editText1);
            eWidget.setText(editText);

        } // end if there was text in the field

        getActionBar().setHomeButtonEnabled(true);
    } // end KeyboardActivity:onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(LOGTAG, "Inside onCreateOptionsMenu");

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_keyboard, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Log.i(LOGTAG, "onOptionsItemSelected, user hit the home icon");

                // Destroy this activity & go back to the existing instance
                // of the MainActivity.
                // Keyboard Activity doesn't return anything to the caller anyway
                // so just call finish.
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void hideButtonClicked(View view) {
		Log.i(LOGTAG, "Inside hideButtonClicked");

		// Prepare to hide the keyboard
        InputMethodManager m;
		m = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

		// Determine which edit field has focus
		EditText c1 = (EditText) findViewById(R.id.editText1);
		EditText c2 = (EditText) findViewById(R.id.editText2);
        EditText c3 = (EditText) findViewById(R.id.editText3);
		if (c1.hasFocus()) {
			m.hideSoftInputFromWindow(c1.getWindowToken(), 0);
		}
		else if (c2.hasFocus()) {
			m.hideSoftInputFromWindow(c2.getWindowToken(), 0);
		}
		else if (c3.hasFocus()) {
			m.hideSoftInputFromWindow(c3.getWindowToken(), 0);
		}
		else {
			Log.i(LOGTAG, "None of the fields have focus, so do nothing");
		}
    } // end method hideButtonClicked


    public void backButtonClicked(View view) {
        Log.i(LOGTAG, "Inside backButtonClicked");

		// Return control to the existing MainActivity
 		// NOTE:  This does NOT mean create a NEW Main Activity

		// Since we got here from the Main Activity, we can just
		// pop ourselves off the stack and we'll be back to the existing Main.
		finish();
    } // end method hideButtonClicked

} // end Keyboard Activity class
