package com.example.vishallandepatil.incubatore.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.reading.Reding_fragment;
import com.example.vishallandepatil.incubatore.trend.TrendFragment;
import com.example.vishallandepatil.incubatore.setting.SettingFragment;


public class MainActivity extends AppCompatActivity {

    Button btnmeasure,btntrend,btnsetting;
    ImageView backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        /*Reding_fragment fragment = new Reding_fragment();*/
        IncubatorlistFragment incubatorlistFragment = new IncubatorlistFragment();
        fragmentTransaction.add(R.id.fragment, incubatorlistFragment);
        fragmentTransaction.commit();
        btnsetting = (Button) findViewById(R.id.btnsetting);
        btnmeasure = (Button) findViewById(R.id.btnmeasure);
        btntrend = (Button) findViewById(R.id.btntrend);
        backpress = (ImageView) findViewById(R.id.backpress);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnmeasure.setClickable(true);
                for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                    fragmentManager.popBackStack();
                }

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                SettingFragment settingFragment = new SettingFragment();
                fragmentTransaction.replace(R.id.fragment, settingFragment);
                fragmentTransaction.commit();

            }
        });

        btnmeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((btntrend.getVisibility()==View.VISIBLE)) {

                 onBackPressed();


                }
                else {


                    for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                        fragmentManager.popBackStack();
                    }

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                    IncubatorlistFragment incubatorlistFragment1 = new IncubatorlistFragment();
                    fragmentTransaction.replace(R.id.fragment, incubatorlistFragment1);
                    fragmentTransaction.commit();

                }


                btnmeasure.setClickable(false);
            }
        });

        btntrend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnmeasure.setClickable(true);
                Fragment fragment = fragmentManager.findFragmentById(R.id.fragment);

                if ((fragment instanceof Reding_fragment)) {


                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                    TrendFragment trendFragment =  TrendFragment.newInstance(((Reding_fragment)fragment).getIncubatore());
                    fragmentTransaction.replace(R.id.fragment, trendFragment);
                    fragmentTransaction.commit();
                }

            }
        });

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                //  FragmentDesignDetails
                Fragment fragment = fragmentManager.findFragmentById(R.id.fragment);

                if ((fragment instanceof IncubatorlistFragment)) {

                    btnmeasure.setVisibility(View.VISIBLE);
                    btntrend.setVisibility(View.GONE);
                    btnsetting.setVisibility(View.VISIBLE);
                    btnsetting.setBackgroundResource(R.drawable.backgroundgraywhite);
                    btnmeasure.setBackgroundResource(R.drawable.rowbackground);
                    btntrend.setBackgroundResource(R.drawable.backgroundgraywhite);
                    btnmeasure.setTextColor(getResources().getColor(R.color.white));
                    btnsetting.setTextColor(getResources().getColor(R.color.grey));
                    btntrend.setTextColor(getResources().getColor(R.color.grey));

                }
                if ((fragment instanceof Reding_fragment)) {

                    btnmeasure.setVisibility(View.VISIBLE);
                    btntrend.setVisibility(View.VISIBLE);
                    btnsetting.setVisibility(View.GONE);
                    btnsetting.setBackgroundResource(R.drawable.backgroundgraywhite);
                    btnmeasure.setBackgroundResource(R.drawable.rowbackground);
                    btntrend.setBackgroundResource(R.drawable.backgroundgraywhite);
                    btnmeasure.setTextColor(getResources().getColor(R.color.white));
                    btnsetting.setTextColor(getResources().getColor(R.color.grey));
                    btntrend.setTextColor(getResources().getColor(R.color.grey));

                }
                if ((fragment instanceof TrendFragment)) {

                    btnmeasure.setVisibility(View.VISIBLE);
                    btntrend.setVisibility(View.VISIBLE);
                    btnsetting.setVisibility(View.GONE);
                    btnsetting.setBackgroundResource(R.drawable.backgroundgraywhite);
                    btnmeasure.setBackgroundResource(R.drawable.backgroundgraywhite);
                    btntrend.setBackgroundResource(R.drawable.rowbackground);
                    btnmeasure.setTextColor(getResources().getColor(R.color.grey));
                    btnsetting.setTextColor(getResources().getColor(R.color.grey));
                    btntrend.setTextColor(getResources().getColor(R.color.white));

                }
                if ((fragment instanceof SettingFragment)) {
                    btnmeasure.setVisibility(View.VISIBLE);
                    btntrend.setVisibility(View.GONE);
                    btnsetting.setVisibility(View.VISIBLE);
                    btnsetting.setBackgroundResource(R.drawable.rowbackground);
                    btnmeasure.setBackgroundResource(R.drawable.backgroundgraywhite);
                    btntrend.setBackgroundResource(R.drawable.backgroundgraywhite);
                    btnmeasure.setTextColor(getResources().getColor(R.color.grey));
                    btnsetting.setTextColor(getResources().getColor(R.color.white));
                    btntrend.setTextColor(getResources().getColor(R.color.grey));
                }
            }

        });


    }
}
