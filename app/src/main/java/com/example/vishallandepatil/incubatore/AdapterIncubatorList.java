package com.example.vishallandepatil.incubatore;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterIncubatorList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;

    public AdapterIncubatorList(Activity context, String[] maintitle) {
        super(context, R.layout.row_incubator_list, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.row_incubator_list, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.incubator_name);

        titleText.setText(maintitle[position]);

        return rowView;

    };
}
