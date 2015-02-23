//
// FILENAME:  SaveDateConfirmDialogFragment
//

package com.mycompany.uisampler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Created by loribatherson on 2/18/15.
 */
public class SaveDateConfirmDialogFragment extends DialogFragment {

    // define the public interface that the DateActivity will implement
    // to handle Yes & No answers from the user
    public interface SaveDateDialogListener {
        public void onDialogSaveDateYes(DialogFragment dialog);
        public void onDialogSaveDateNo(DialogFragment dialog);
    }

    // use this instance of the interface to deliver action events
    SaveDateDialogListener sdListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

        b.setMessage(R.string.dialog_save_date)
                .setPositiveButton(R.string.yesSaveDate, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the yes button event back to the host activity (DateActivity)
                        sdListener.onDialogSaveDateYes(SaveDateConfirmDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.noSaveDate, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the no button event back to the host activity (Date Activity)
                        sdListener.onDialogSaveDateNo(SaveDateConfirmDialogFragment.this);
                    }
                });

        return b.create();
    } // end onCreateDialog


    // Instantiate the SaveDateDialogListener in the onAttach method
    // so we can send events to the calling activity (DateActivity)
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        sdListener = (SaveDateDialogListener) activity;
    } // end onAttach method

} // end class SaveDateConfirmDialogFragment

