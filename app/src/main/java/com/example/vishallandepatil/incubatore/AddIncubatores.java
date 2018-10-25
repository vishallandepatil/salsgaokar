package com.example.vishallandepatil.incubatore;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class AddIncubatores extends Fragment {





    public AddIncubatores() {
        // Required empty public constructor
    }


    public static AddIncubatores newInstance(String param1, String param2) {
        AddIncubatores fragment = new AddIncubatores();
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_incubatores, container, false);

        return view;
    }




}
