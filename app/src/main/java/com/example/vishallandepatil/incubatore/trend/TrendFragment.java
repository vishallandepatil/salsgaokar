package com.example.vishallandepatil.incubatore.trend;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.vishallandepatil.incubatore.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import static android.view.View.GONE;


public class TrendFragment extends Fragment {
    Button btngraph, btncalendar;
    LinearLayout graphview, calendarview;


    public TrendFragment() {
        // Required empty public constructor
    }

    public static TrendFragment newInstance(String param1, String param2) {
        TrendFragment fragment = new TrendFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_trend, container, false);
        btngraph = (Button) rootview.findViewById(R.id.btngraph);
        btncalendar = (Button) rootview.findViewById(R.id.btncalendar);
        graphview = (LinearLayout) rootview.findViewById(R.id.graphview);
        calendarview = (LinearLayout) rootview.findViewById(R.id.calendarview);

        Drawable img = getContext().getResources().getDrawable( R.drawable.ic_bar_chart_active );
        btngraph.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null);
        btngraph.setTextColor(getResources().getColor(R.color.colorPrimary));
        LineChart chart = (LineChart) rootview.findViewById(R.id.chart);
        ArrayList<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(4f, 0));
        entries1.add(new Entry(8f, 1));
        entries1.add(new Entry(6f, 2));
        entries1.add(new Entry(2f, 3));
        entries1.add(new Entry(18f, 4));
        entries1.add(new Entry(9f, 5));

        ArrayList<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(3f, 0));
        entries2.add(new Entry(4f, 5));
        entries2.add(new Entry(5f, 4));
        entries2.add(new Entry(6f, 5));
        entries2.add(new Entry(7f, 6));
        entries2.add(new Entry(8f, 7));

        String[] xAxis = new String[] {"Jan", "Feb", "March", "Aprl", "May", "June", "July", "Aug", "Sep"};

        ArrayList<LineDataSet> lines = new ArrayList<LineDataSet> ();
        LineDataSet lDataSet1 = new LineDataSet(entries1, "O2");
        lDataSet1.setColor(Color.BLUE);
        lDataSet1.setCircleColor(Color.BLUE);
        lines.add(lDataSet1);

        LineDataSet lDataSet2 = new LineDataSet(entries2, "Co2");
        lDataSet2.setColor(Color.RED);
        lDataSet2.setCircleColor(Color.RED);
        lines.add(lDataSet2);

        chart.setData(new LineData(xAxis, lines));

        btngraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphview.setVisibility(view.VISIBLE);
                Drawable imggraph = getContext().getResources().getDrawable( R.drawable.ic_bar_chart_active );
                btngraph.setCompoundDrawablesWithIntrinsicBounds( imggraph, null, null, null);
                btngraph.setTextColor(getResources().getColor(R.color.colorPrimary));
                calendarview.setVisibility(view.GONE);
                Drawable imgcalendar = getContext().getResources().getDrawable( R.drawable.ic_calendar );
                btncalendar.setCompoundDrawablesWithIntrinsicBounds( imgcalendar, null, null, null);
                btncalendar.setTextColor(getResources().getColor(R.color.darkgrey));
            }
        });

        btncalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphview.setVisibility(view.GONE);
                Drawable imggraph = getContext().getResources().getDrawable( R.drawable.ic_bar_chart );
                btngraph.setCompoundDrawablesWithIntrinsicBounds( imggraph, null, null, null);
                btngraph.setTextColor(getResources().getColor(R.color.darkgrey));
                calendarview.setVisibility(view.VISIBLE);
                Drawable imgcalendar = getContext().getResources().getDrawable( R.drawable.ic_calendar_active );
                btncalendar.setCompoundDrawablesWithIntrinsicBounds( imgcalendar, null, null, null);
                btncalendar.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        return rootview;
    }
}
