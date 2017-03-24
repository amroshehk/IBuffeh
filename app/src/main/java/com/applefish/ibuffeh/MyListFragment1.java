package com.applefish.ibuffeh;

/**
 * Created by Amro on 23/03/2017.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyListFragment1 extends ListFragment {

    String[] image ={
            "Spring"
            ,
            "Autumn"
            ,
            "Winter"
            ,
            "Summer"
    };
    LinearLayout linearLayout;
    private View rootView;
    private View rootview2;
    Activity activity;
    OnLineSelectedListener mCallback=null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            this.activity=activity;
            mCallback = (OnLineSelectedListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLineSelectedListener");
        }
    }

    // Container Activity must implement this interface
    public interface OnLineSelectedListener {
        public void onItemPicked( int position);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListAdapter myListAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                image);
        setListAdapter(myListAdapter);
      //  getListView().setOnItemClickListener((AdapterView.OnItemClickListener) this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.listfragment1, container, false);
        linearLayout= (LinearLayout) rootView.getRootView().findViewById(R.id.www);
        rootview2=inflater.inflate(R.layout.fragment2, null, false);
        linearLayout= (LinearLayout) rootview2.getRootView().findViewById(R.id.lf2);
      //  textView=(TextView) rootview2.getRootView().findViewById(R.id.fragment2text);

        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
                Toast.makeText(
                getActivity(),
                getListView().getItemAtPosition(position).toString(),
                Toast.LENGTH_LONG).show();
        ((OnLineSelectedListener)activity).onItemPicked(position);
    }
}
