package com.applefish.ibuffeh;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

public class MainActivity extends FragmentActivity implements MyListFragment1.OnLineSelectedListener{
    Fragment fragment2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        setContentView(R.layout.activity_main);


        fragment2=(Fragment)getSupportFragmentManager().findFragmentById(R.id.fragment2);
        android.support.v4.app.FragmentManager manager=getSupportFragmentManager();
        if(manager.findFragmentById(R.id.fragment2)==null)
            manager.beginTransaction().add(R.id.fragment2,fragment2 ).commit();




    }

    @Override
    public void onItemPicked(int position) {

        if(position==0)
            ((Fragment2)fragment2).updateBackgroundFra(R.drawable.nn);
           // linearLayout.setBackgroundResource(R.drawable.ww);

        if(position==1)
            ((Fragment2)fragment2).updateBackgroundFra(R.drawable.aa);
           // linearLayout.setBackgroundResource(R.drawable.aa);

        if(position==2)
            ((Fragment2)fragment2).updateBackgroundFra(R.drawable.ww);
            //linearLayout.setBackgroundResource(R.drawable.ss);

        if(position==3)
            ((Fragment2)fragment2).updateBackgroundFra(R.drawable.ss);
            //linearLayout.setBackgroundResource(R.drawable.nn);

        }

}
