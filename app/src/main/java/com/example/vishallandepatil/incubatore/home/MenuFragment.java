package com.example.vishallandepatil.incubatore.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.reading.Reding_fragment;
import com.example.vishallandepatil.incubatore.setting.SettingFragment;


public class MenuFragment extends Fragment {

    Button btnmeasure,btntrend,btnsetting;
    ImageView backpress;
    TextView status;
    public MenuFragment() {
        // Required empty public constructor
    }


    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_blank, container, false);
        // Inflate the layout for this fragment
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        btnsetting = (Button) view.findViewById(R.id.btnsetting);
        btnmeasure = (Button) view.findViewById(R.id.btnmeasure);
        btntrend = (Button) view.findViewById(R.id.btntrend);
        backpress = (ImageView) view.findViewById(R.id.backpress);


        ((MainActivity)getActivity()).toolbartitle.setText("Incumonitor");



        btnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.button=3;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                /*Reding_fragment fragment = new Reding_fragment();*/
                SettingFragment settingFragment = new SettingFragment();
                fragmentTransaction.replace(R.id.fragment, settingFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnmeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.button=1;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                /*Reding_fragment fragment = new Reding_fragment();*/
                Reding_fragment fragment =  Reding_fragment.newInstance( /*list.get(position)*/);
                fragmentTransaction.replace(R.id.fragment, fragment).addToBackStack(null);;
                fragmentTransaction.commit();


            }
        });

        btntrend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.button=2;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                /*Reding_fragment fragment = new Reding_fragment();*/
                IncubatorlistFragment incubatorlistFragment = new IncubatorlistFragment();
                fragmentTransaction.replace(R.id.fragment, incubatorlistFragment).addToBackStack(null);;
                fragmentTransaction.commit();

            }
        });



        return view;
    }




}
