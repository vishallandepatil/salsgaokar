package com.example.vishallandepatil.incubatore.trend;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.file.FileHelper;
import com.example.vishallandepatil.incubatore.login.DBHelper;
import com.example.vishallandepatil.incubatore.reading.database.ReadingTable;
import com.example.vishallandepatil.incubatore.setting.database.Incubatore;
import com.example.vishallandepatil.incubatore.trend.adapter.ReadingListAdapter;
import com.github.mikephil.charting.charts.LineChart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import landepatil.vishal.sqlitebuilder.Query;
import landepatil.vishal.sqlitebuilder.clause.Group;
import landepatil.vishal.sqlitebuilder.clause.Restriction;



public class TrendFragment extends Fragment {
    Button btngraph, btncalendar;
    LinearLayout graphview, calendarview;
    Incubatore incubatore;
    ArrayList<String> months = new ArrayList<String>();;
    ArrayList<ReadingTable> cursor;
    final ArrayList<String> year = new ArrayList<String>();

    Spinner yearlist,monthlist,yearlistgraf,monthlistgraf;
    int yearselected=0;
    int monthselected=0;
    Button exporttoexcel;
    public TrendFragment() {
        // Required empty public constructor
    }

    public static TrendFragment newInstance(Incubatore incubatore) {
        TrendFragment fragment = new TrendFragment();
        Bundle bundle= new Bundle();
        bundle.putParcelable("Incubatore",incubatore);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            incubatore= getArguments().getParcelable("Incubatore");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_trend, container, false);
        btngraph = (Button) rootview.findViewById(R.id.btngraph);
        exporttoexcel = (Button) rootview.findViewById(R.id.exporttoexcel);
        btncalendar = (Button) rootview.findViewById(R.id.btncalendar);
        graphview = (LinearLayout) rootview.findViewById(R.id.graphview);
        calendarview = (LinearLayout) rootview.findViewById(R.id.calendarview);
        yearlist = (Spinner) rootview.findViewById(R.id.yearlist);
        yearlistgraf = (Spinner) rootview.findViewById(R.id.yearlistgraf);
        monthlistgraf = (Spinner) rootview.findViewById(R.id.monthlistgraf);
        monthlist = (Spinner) rootview.findViewById(R.id.monthlist);
       final RecyclerView recyclerView = (RecyclerView) rootview.findViewById(R.id.readinglist);
//

//
    Group group=new Group();
     group.setGroup("year");
        Cursor cursor=Query.createQuery(new DBHelper(getContext())).setRestriction(new Restriction().addEquals("IncubatoreId", incubatore.getId())).setGroupBy(group).loadCursor(ReadingTable.class);

        year.add("Select Year");
      if (cursor.moveToFirst()) {
           do {
             String str=  cursor.getString(cursor.getColumnIndex("year"));
               if(str!=null) {
                   year.add(str);
               }
                // get  the  data into array,or class variable
            } while (cursor.moveToNext());
        }


//      final ArrayList<String> ar = new ArrayList<String>();
////
//        ar.add("Select Year");
//        ar.add("2018");ar.add("2019");ar.add("2020");ar.add("2021");
//       ar.add("2022");ar.add("2023");ar.add("2024");ar.add("2025");
//       ar.add("2026");ar.add("2027");ar.add("2028");ar.add("2029");



        final ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,year);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearlist.setAdapter(aa);
        yearlistgraf.setAdapter(aa);
        //Setting the ArrayAdapter data on the Spinner







        Drawable img = getContext().getResources().getDrawable( R.drawable.ic_bar_chart_active );
        btngraph.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null);
        btngraph.setTextColor(getResources().getColor(R.color.colorPrimary));
        final LineChart chart = (LineChart) rootview.findViewById(R.id.chart);

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

        monthlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  Group group=new Group();
                if(position>0) {

                    Restriction restriction = new Restriction().addEquals("IncubatoreId", incubatore.getId()).addEquals("year", year.get(yearselected)).addEquals("month",months.get(position));
                    ArrayList<ReadingTable> cursor =(ArrayList) Query.createQuery(new DBHelper(getContext())).setRestriction(restriction).load(ReadingTable.class);


                    ReadingListAdapter mAdapter = new ReadingListAdapter(cursor);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                    exporttoexcel.setVisibility(View.VISIBLE);
                }
                else
                {

                    exporttoexcel.setVisibility(View.GONE);
                }
                monthselected=position;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }



        });
        yearlistgraf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                months = new ArrayList<String>();
                if(position==0)
                {
                    months.add("Select Month");
                    ArrayAdapter monthadpater = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, months);
                    monthadpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    monthlistgraf.setAdapter(monthadpater);
                    ArrayList<Entry> entriesco2 = new ArrayList<>();
                    ArrayList<Entry> entrieso2 = new ArrayList<>();
                    String[] xAxis = new String[0];
                    ArrayList<LineDataSet> lines = new ArrayList<LineDataSet>();
                    LineDataSet lDataSet1 = new LineDataSet(entrieso2, "O2");
                    lDataSet1.setColor(Color.BLUE);
                    lDataSet1.setCircleColor(Color.BLUE);
                    lines.add(lDataSet1);

                    LineDataSet lDataSet2 = new LineDataSet(entriesco2, "Co2");
                    lDataSet2.setColor(Color.RED);
                    lDataSet2.setCircleColor(Color.RED);
                    lines.add(lDataSet2);
                    chart.setData(new LineData(xAxis, lines));
                    chart.invalidate();


                }
                else {
                    String years= year.get(position);
                    months.add("Select Month");

                    Group group=new Group();
                    group.setGroup("month");
                    Restriction restriction=new Restriction().addEquals("year",years);
                    Cursor cursor=Query.createQuery(new DBHelper(getContext())).setGroupBy(group).setRestriction(restriction).loadCursor(ReadingTable.class);
                    if (cursor.moveToFirst()) {
                        do {
                            String str=  cursor.getString(cursor.getColumnIndex("month"));
                            if(str!=null) {
                                months.add(str);
                            }
                            // get  the  data into array,or class variable
                        } while (cursor.moveToNext());
                    }
                    ArrayAdapter monthsadapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,months);
                    monthsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    monthlistgraf.setAdapter(monthsadapter);


                }
                yearselected=position;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        monthlistgraf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {

                    Restriction restriction = new Restriction().addEquals("year", year.get(yearselected)).addEquals("month", months.get(position));
                    ArrayList<ReadingTable> cursor = (ArrayList) Query.createQuery(new DBHelper(getContext())).setRestriction(restriction).load(ReadingTable.class);
                    ArrayList<ReadingTable> data = (ArrayList) Query.createQuery(new DBHelper(getContext())).setRestriction(restriction).load(ReadingTable.class);
                    ArrayList<Entry> entriesco2 = new ArrayList<>();
                    ArrayList<Entry> entrieso2 = new ArrayList<>();
                    String[] xAxis = new String[data.size()];

                    for (int i = 0; data.size() > i; i++) {

                        Float O2 = Float.valueOf(data.get(i).getO2reaading().replace("%", "").trim());
                        Float cO2 = Float.valueOf(data.get(i).getCoreading().replace("%", "").trim());

                        entrieso2.add(new Entry(O2, i));
                        entriesco2.add(new Entry(cO2, i));
                        xAxis[i] = data.get(i).getDateTime();

                    }


                    ArrayList<LineDataSet> lines = new ArrayList<LineDataSet>();
                    LineDataSet lDataSet1 = new LineDataSet(entrieso2, "O2");
                    lDataSet1.setColor(Color.BLUE);
                    lDataSet1.setCircleColor(Color.BLUE);
                    lines.add(lDataSet1);

                    LineDataSet lDataSet2 = new LineDataSet(entriesco2, "Co2");
                    lDataSet2.setColor(Color.RED);
                    lDataSet2.setCircleColor(Color.RED);
                    lines.add(lDataSet2);

                    chart.setData(new LineData(xAxis, lines));
                    chart.setTouchEnabled(true);
                    chart.invalidate();
                    chart.setDragEnabled(true);
                }
                else
                {
                    ArrayList<Entry> entriesco2 = new ArrayList<>();
                    ArrayList<Entry> entrieso2 = new ArrayList<>();
                    String[] xAxis = new String[0];
                    ArrayList<LineDataSet> lines = new ArrayList<LineDataSet>();
                    LineDataSet lDataSet1 = new LineDataSet(entrieso2, "O2");
                    lDataSet1.setColor(Color.BLUE);
                    lDataSet1.setCircleColor(Color.BLUE);
                    lines.add(lDataSet1);

                    LineDataSet lDataSet2 = new LineDataSet(entriesco2, "Co2");
                    lDataSet2.setColor(Color.RED);
                    lDataSet2.setCircleColor(Color.RED);
                    lines.add(lDataSet2);
                    chart.setData(new LineData(xAxis, lines));
                    chart.invalidate();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        yearlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                months = new ArrayList<String>();;

                if(position==0)
                {
                    months.add("Select Month");
                    ArrayAdapter monthadpater = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, months);
                    monthadpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    monthlist.setAdapter(monthadpater);

                }
               else {
                   String years= year.get(position);
                    months.add("Select Month");

                    Group group=new Group();
                    group.setGroup("month");
                    Restriction restriction=new Restriction().addEquals("IncubatoreId", incubatore.getId()).addEquals("year",years);
                    Cursor cursor=Query.createQuery(new DBHelper(getContext())).setGroupBy(group).setRestriction(restriction).loadCursor(ReadingTable.class);
                    if (cursor.moveToFirst()) {
                        do {
                            String str=  cursor.getString(cursor.getColumnIndex("month"));
                            if(str!=null) {
                                months.add(str);
                            }
                            // get  the  data into array,or class variable
                        } while (cursor.moveToNext());
                    }
                    ArrayAdapter monthsadapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,months);
                    monthsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    monthlist.setAdapter(monthsadapter);


                }
                yearselected=position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        exporttoexcel.setVisibility(View.GONE);
        exporttoexcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                0);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                    exportTheDB();
                }





            }
        });
        return rootview;
    }
    private void exportTheDB()
    {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Exporting database...");
        dialog.show();

        File myFile;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String TimeStampDB = sdf.format(cal.getTime());

        try {

          //  myFile = new File(extStorageDirectory+"/Export_"+TimeStampDB+".csv");
            myFile =  FileHelper.creatfile("Backup","Export_"+TimeStampDB,".csv");
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append("ID;TimeSatmp;year;O2;CO2;IncubatoreID;MONTH");

            myOutWriter.append("\n");

            Restriction restriction = new Restriction().addEquals("IncubatoreId", incubatore.getId()).addEquals("year", year.get(yearselected)).addEquals("month",months.get(monthselected));
            ArrayList<ReadingTable> cursor =(ArrayList) Query.createQuery(new DBHelper(getContext())).setRestriction(restriction).load(ReadingTable.class);

            if (cursor != null)
            {
                for(ReadingTable entry:cursor)
                {

                    myOutWriter.append(entry.getId()+";"+entry.getDateTime()+entry.getYear()+";"+";"+entry.getCoreading()+";"+entry.getO2reaading()+";"+entry.getIncubatoreId()+";"+entry.getMonth());
                    myOutWriter.append("\n");
                }

                myOutWriter.close();
                fOut.close();
                Toast.makeText(getContext(),"Exported Successfully",Toast.LENGTH_SHORT).show();

            }
        } catch (IOException se)
        {
            Log.e(getClass().getSimpleName(),"Could not create or Open the database");

            Toast.makeText(getContext(),"Error While Export",Toast.LENGTH_SHORT).show();
        }

        finally {

           dialog.dismiss();

        }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    exportTheDB();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(),"Permission Denied by you!",Toast.LENGTH_SHORT).show();

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

}
