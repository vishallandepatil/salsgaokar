package com.example.vishallandepatil.incubatore.home.adpators;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.setting.database.Incubatore;

import java.util.ArrayList;

public class AdapterIncubatorList extends ArrayAdapter<Incubatore> {

    private  Activity context;
    private  ArrayList<Incubatore> maintitle;
    private int fragmenttype;

    public AdapterIncubatorList(Activity context, ArrayList<Incubatore> maintitle,int fragmenttype) {
        super(context, R.layout.row_incubator_list, maintitle);
        this.context=context;
        this.maintitle=maintitle;
        this.fragmenttype=fragmenttype;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.row_incubator_list, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.incubator_name);
        ImageView img=rowView.findViewById(R.id.icon);
        titleText.setText(maintitle.get(position).getName());
        if(fragmenttype==1)
        {
            img.setImageDrawable(context.getResources().getDrawable(R.drawable.pencil));
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        return rowView;

    };
}
