package com.example.vishallandepatil.incubatore.home;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.reading.Reding_fragment;
import com.example.vishallandepatil.incubatore.trend.TrendFragment;
import com.example.vishallandepatil.incubatore.setting.SettingFragment;


public class MainActivity extends AppCompatActivity {

    Button btnmeasure,btntrend,btnsetting;
    ImageView backpress;
    TextView status;
    public TextView toolbartitle;
public static     int button=1;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        /*Reding_fragment fragment = new Reding_fragment();*/
        MenuFragment incubatorlistFragment = new MenuFragment();
        fragmentTransaction.add(R.id.fragment, incubatorlistFragment);
        fragmentTransaction.commit();
        btnsetting = (Button) findViewById(R.id.btnsetting);
        btnmeasure = (Button) findViewById(R.id.btnmeasure);
        btntrend = (Button) findViewById(R.id.btntrend);
        toolbartitle = (TextView) findViewById(R.id.toolbartitle);


        backpress = (ImageView) findViewById(R.id.backpress);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


}
