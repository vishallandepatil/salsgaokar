package com.example.vishallandepatil.incubatore.home.adpators;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.setting.database.Incubatore;

import java.util.ArrayList;

public class AdapterIncubatorList extends ArrayAdapter<Incubatore> {

    private  Activity context;
    private  ArrayList<Incubatore> maintitle;


    public AdapterIncubatorList(Activity context, ArrayList<Incubatore> maintitle) {
        super(context, R.layout.row_incubator_list, maintitle);
        this.context=context;
        this.maintitle=maintitle;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.row_incubator_list, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.incubator_name);
        titleText.setText(maintitle.get(position).getName());

        return rowView;

    };
}
