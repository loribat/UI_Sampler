// FILENAME:  MyListFragment.java
//

package com.mycompany.uisampler;

import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * Created by loribatherson on 2/14/15.
 */
public class MyListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    public static final String LOGTAG = "MyListFragment";

    String selectedItemText;
    int selectedItemId=-1;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup c, Bundle savedInstanceState) {
		Log.i(LOGTAG, "Inside onCreateView");
        View view = i.inflate(R.layout.list_fragment, c, false);
        return view;
    } // end onCreateView method


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(LOGTAG, "Inside onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<CharSequence> a = ArrayAdapter.createFromResource(getActivity(),
                R.array.desserts_list, android.R.layout.simple_list_item_activated_1);
        setListAdapter(a);
        getListView().setOnItemClickListener(this);
        getListView().setItemChecked(selectedItemId, true);
    } // end onActivityCreated method


    @Override
    public void onItemClick(AdapterView<?>parent, View v, int position, long id) {
		Log.i(LOGTAG, "Inside onItemClick");

		// Save the selected item id and text in class variables.
		// The calling activity can get them using getSelectedItemText and myGetId
        String d[] = getResources().getStringArray(R.array.desserts_list);
        selectedItemId = position;
        selectedItemText = d[position];
        Log.i(LOGTAG, "onItemClick: selectedItemText is: " + selectedItemText +
                "selectedItemId=" + selectedItemId);
    } // end onItemClick method


    public String getSelectedItemText() {
        Log.i(LOGTAG, "Inside getSelectedItemText");
        return selectedItemText;
    } // end method getSelectedItemText


    public int myGetId() {
        Log.i(LOGTAG, "Inside myGetId, selectedItemId=" + selectedItemId);
        return selectedItemId;
    } // end method myGetId


    public void setListChoice(int index) {
        Log.i(LOGTAG, "Inside setListChoice, index = " + index);

        if (index >= 0) {
            selectedItemId = index;
            String d[] = getResources().getStringArray(R.array.desserts_list);
            selectedItemText = d[selectedItemId];

            getListView().setItemChecked(index, true);
        }
     } // end setListChoice method

} // end MyListFragment class
