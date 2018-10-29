package com.example.vishallandepatil.incubatore;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button btnmeasure,btntrend,btnsetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        /*Reding_fragment fragment = new Reding_fragment();*/
        IncubatorlistFragment incubatorlistFragment = new IncubatorlistFragment();
        fragmentTransaction.add(R.id.fragment, incubatorlistFragment);
        fragmentTransaction.commit();
        btnsetting = (Button) findViewById(R.id.btnsetting);
        btnmeasure = (Button) findViewById(R.id.btnmeasure);
        btnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                SettingFragment settingFragment = new SettingFragment();
                fragmentTransaction.replace(R.id.fragment, settingFragment);
                fragmentTransaction.commit();
                btnsetting.setBackgroundResource(R.drawable.rowbackground);
                btnmeasure.setBackgroundResource(R.drawable.backgroundgraywhite);
                btnmeasure.setTextColor(getResources().getColor(R.color.grey));
                btnsetting.setTextColor(getResources().getColor(R.color.white));
            }
        });

        btnmeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                IncubatorlistFragment incubatorlistFragment1 = new IncubatorlistFragment();
                fragmentTransaction.replace(R.id.fragment, incubatorlistFragment1);
                fragmentTransaction.commit();
                btnsetting.setBackgroundResource(R.drawable.backgroundgraywhite);
                btnmeasure.setBackgroundResource(R.drawable.rowbackground);
                btnmeasure.setTextColor(getResources().getColor(R.color.white));
                btnsetting.setTextColor(getResources().getColor(R.color.grey));
            }
        });
    }
}
