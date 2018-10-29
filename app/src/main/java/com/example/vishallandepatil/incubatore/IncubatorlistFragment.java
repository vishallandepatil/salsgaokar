package com.example.vishallandepatil.incubatore;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class IncubatorlistFragment extends Fragment {

    ListView listincubators;
    String[] maintitle ={
            "Incubator 1","Incubator 2",
            "Incubator 3","Incubator 4",
            "Incubator 5",
    };

    public IncubatorlistFragment() {
        // Required empty public constructor
    }

    public static IncubatorlistFragment newInstance(String param1, String param2) {
        IncubatorlistFragment fragment = new IncubatorlistFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_incubatorlist, container, false);
        AdapterIncubatorList adapter = new AdapterIncubatorList(this.getActivity(), maintitle);
        ListView listincubators = rootview.findViewById(R.id.listincubators);
        listincubators.setAdapter(adapter);

        listincubators.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                Reding_fragment fragment = new Reding_fragment();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });
        return rootview;
    }
}
