package com.applefish.ibuffeh;

/**
 * Created by Amro on 23/03/2017.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Fragment2 extends Fragment {
    LinearLayout linearLayout;
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment2, container, false);
        linearLayout= (LinearLayout) view.findViewById(R.id.lf2);
        return inflater.inflate(R.layout.fragment2, container, false);
    }
public  void  updateBackgroundFra(int  drawable)
{
    linearLayout= (LinearLayout) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment2).getActivity().findViewById(R.id.fag2);
    if(getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment2).getActivity().findViewById(R.id.fag2).isShown())
    Log.i("isshown",1+"");
    else
        Log.i("isshown",0+"");
    linearLayout.setBackgroundResource(drawable);


}

}