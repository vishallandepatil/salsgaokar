package com.example.vishallandepatil.incubatore.home;

import android.arch.lifecycle.Observer;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.vishallandepatil.incubatore.home.adpators.AdapterIncubatorList;
import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.login.AppDatabase;
import com.example.vishallandepatil.incubatore.reading.Reding_fragment;
import com.example.vishallandepatil.incubatore.reading.database.ReadingTable;
import com.example.vishallandepatil.incubatore.reading.database.ReadingTableDao;
import com.example.vishallandepatil.incubatore.setting.database.Incubatore;
import com.example.vishallandepatil.incubatore.setting.database.IncubatorsDao;
import com.example.vishallandepatil.incubatore.trend.TrendFragment;

import java.util.ArrayList;
import java.util.List;


public class IncubatorlistFragment extends Fragment {

    ListView listincubators;

    public IncubatorlistFragment() {
        // Required empty public constructor
    }



    public static IncubatorlistFragment newInstance(String param1, String param2) {
        IncubatorlistFragment fragment = new IncubatorlistFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_incubatorlist, container, false);
        IncubatorsDao a=  AppDatabase.getDatabase(getContext()).uncubatorsDao();
        final ListView listincubators = rootview.findViewById(R.id.listincubators);
        final TextView error = rootview.findViewById(R.id.error);
        error.setVisibility(View.VISIBLE);
        listincubators.setVisibility(View.GONE);

        // a.fetchReadingBetweenDate(String from, String to);
        a.fetchAllIncubators().observeForever(new Observer<List<Incubatore>>() {
            @Override
            public void onChanged(@Nullable final List<Incubatore> list) {

                AdapterIncubatorList adapter = new AdapterIncubatorList(getActivity(), (ArrayList<Incubatore>) list,IncubatorlistFragment.this);

                ((MainActivity)getActivity()).toolbartitle.setText("Select Incubator");

                 if(list.size()>0)
                {
                    error.setVisibility(View.GONE);
                    listincubators.setVisibility(View.VISIBLE);
                }

                listincubators.setAdapter(adapter);

                listincubators.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Fragment fragment =  Reding_fragment.newInstance( /*list.get(position)*/);;
                        if(MainActivity.button==2) {
                            fragment =  TrendFragment.newInstance( list.get(position));
                        }
                        else
                        {

                            fragment =  Reding_fragment.newInstance( /*list.get(position)*/);
                        }
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                        fragmentTransaction.replace(R.id.fragment, fragment);
                        fragmentTransaction.commit();
                    }
                });

            }
        });
        //final ArrayList<Incubatore> list= (ArrayList) Query.createQuery(new DBHelper(getContext())).load(Incubatore.class);

        return rootview;
    }
}
