package com.example.vishallandepatil.incubatore.trend;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.file.FileHelper;
import com.example.vishallandepatil.incubatore.file.SingleMediaScanner;
import com.example.vishallandepatil.incubatore.home.MainActivity;

import com.example.vishallandepatil.incubatore.login.AppDatabase;
import com.example.vishallandepatil.incubatore.reading.database.ReadingTable;
import com.example.vishallandepatil.incubatore.reading.database.ReadingTableDao;
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
import java.util.Date;
import java.util.List;


public class TrendFragment extends Fragment {
    public static int datetype = 0;
    Button btngraph, btncalendar;
    LineChart charto2;
    LineChart chartco2;
    LinearLayout graphview, calendarview;
    Incubatore incubatore;
    ArrayList<String> months = new ArrayList<String>();
    ;

    final ArrayList<String> year = new ArrayList<String>();
    RecyclerView recyclerView;
    Date startdate, endate;
    Spinner yearlist, monthlist, yearlistgraf, monthlistgraf;
    int yearselected = 0;
    int monthselected = 0;
    Button exporttoexcel;
    EditText fromdate, todate, todatetable, fromdatetable;

    public TrendFragment() {
        // Required empty public constructor
    }

    public static TrendFragment newInstance(Incubatore incubatore) {
        TrendFragment fragment = new TrendFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Incubatore", incubatore);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            incubatore = getArguments().getParcelable("Incubatore");
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            if (datetype == 1) {
                fromdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                fromdatetable.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                try {
                    startdate = (Date) new SimpleDateFormat("yyyy-mm-DD").parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    startdate.setMonth(monthOfYear);
                    loadGraf();
                    loadTable();

                } catch (Exception e) {

                }
            } else if (datetype == 2) {
                todate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                todatetable.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                try {
                    endate = (Date) new SimpleDateFormat("yyyy-mm-DD").parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    endate.setMonth(monthOfYear);
                    loadGraf();
                    loadTable();

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }


        }

    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Calendar myCalendar = Calendar.getInstance();
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
        fromdate = (EditText) rootview.findViewById(R.id.fromdate);
        todatetable = (EditText) rootview.findViewById(R.id.todatetable);
        fromdatetable = (EditText) rootview.findViewById(R.id.fromdatetable);
        todate = (EditText) rootview.findViewById(R.id.todate);

        recyclerView = (RecyclerView) rootview.findViewById(R.id.readinglist);
//
        ((MainActivity) getActivity()).toolbartitle.setText(incubatore.getName());

        Drawable img = getContext().getResources().getDrawable(R.drawable.ic_bar_chart_active);
        btngraph.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        btngraph.setTextColor(getResources().getColor(R.color.colorPrimary));
        chartco2 = (LineChart) rootview.findViewById(R.id.chartco2);
        charto2 = (LineChart) rootview.findViewById(R.id.charto2);

        btngraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphview.setVisibility(view.VISIBLE);
                Drawable imggraph = getContext().getResources().getDrawable(R.drawable.ic_bar_chart_active);
                btngraph.setCompoundDrawablesWithIntrinsicBounds(imggraph, null, null, null);
                btngraph.setTextColor(getResources().getColor(R.color.colorPrimary));
                calendarview.setVisibility(view.GONE);
                Drawable imgcalendar = getContext().getResources().getDrawable(R.drawable.ic_calendar);
                btncalendar.setCompoundDrawablesWithIntrinsicBounds(imgcalendar, null, null, null);
                btncalendar.setTextColor(getResources().getColor(R.color.darkgrey));
            }
        });

        btncalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphview.setVisibility(view.GONE);
                Drawable imggraph = getContext().getResources().getDrawable(R.drawable.ic_bar_chart);
                btngraph.setCompoundDrawablesWithIntrinsicBounds(imggraph, null, null, null);
                btngraph.setTextColor(getResources().getColor(R.color.darkgrey));
                calendarview.setVisibility(view.VISIBLE);
                Drawable imgcalendar = getContext().getResources().getDrawable(R.drawable.ic_calendar_active);
                btncalendar.setCompoundDrawablesWithIntrinsicBounds(imgcalendar, null, null, null);
                btncalendar.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });


        fromdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                datetype = 1;
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        todate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                datetype = 2;
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        fromdatetable.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                datetype = 1;
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        todatetable.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                datetype = 2;
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        monthlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*Group group=new Group();
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
                monthselected=position;*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        yearlistgraf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                months = new ArrayList<String>();
               /* if(position==0)
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
                else {*/
                   /* String years= year.get(position);
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



                yearselected=position;*/


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        monthlistgraf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {


                   /* Restriction restriction = new Restriction().addEquals("IncubatoreId", incubatore.getId()).addEquals("year", year.get(yearselected)).addEquals("month", months.get(position));
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

                    ArrayList<LineDataSet> linesCo2 = new ArrayList<LineDataSet>();
                    LineDataSet lDataSet2 = new LineDataSet(entriesco2, "Co2");
                    lDataSet2.setColor(Color.RED);
                    lDataSet2.setCircleColor(Color.RED);
                    linesCo2.add(lDataSet2);

                    charto2.setData(new LineData(xAxis, lines));
                    charto2.setTouchEnabled(true);
                    charto2.invalidate();
                    charto2.setDragEnabled(false);

                    chartco2.setData(new LineData(xAxis, linesCo2));
                    chartco2.setTouchEnabled(true);
                    chartco2.invalidate();
                    chartco2.setDragEnabled(false);*/
                } else {
                    ArrayList<Entry> entriesco2 = new ArrayList<>();
                    ArrayList<Entry> entrieso2 = new ArrayList<>();
                    String[] xAxis = new String[0];
                    ArrayList<LineDataSet> lines = new ArrayList<LineDataSet>();
                    LineDataSet lDataSet1 = new LineDataSet(entrieso2, "O2");
                    lDataSet1.setColor(Color.BLUE);
                    lDataSet1.setCircleColor(Color.BLUE);
                    lines.add(lDataSet1);

                    ArrayList<LineDataSet> linesco2 = new ArrayList<LineDataSet>();
                    LineDataSet lDataSet2 = new LineDataSet(entriesco2, "Co2");
                    lDataSet2.setColor(Color.RED);
                    lDataSet2.setCircleColor(Color.RED);
                    lines.add(lDataSet2);
                    chartco2.setData(new LineData(xAxis, lines));
                    chartco2.invalidate();

                    linesco2.add(lDataSet2);
                    charto2.setData(new LineData(xAxis, lines));
                    charto2.invalidate();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        yearlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  months = new ArrayList<String>();

             /*   if(position==0)
                {
                    months.add("Select Month");
                    ArrayAdapter monthadpater = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, months);
                    monthadpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    monthlist.setAdapter(monthadpater);

                }
               else {*/
                   /*String years= year.get(position);
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



                yearselected=position;*/

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
    private void loadGraf() {


        if ((startdate != null && endate != null)) {

            ReadingTableDao a = AppDatabase.getDatabase(getContext()).readingTableDao();
            // a.fetchReadingBetweenDate(String from, String to);

            a.fetchReading(incubatore.getId()).observeForever(new Observer<List<ReadingTable>>() {
                @Override
                public void onChanged(@Nullable List<ReadingTable> data) {


                    ArrayList<Entry> entriesco2 = new ArrayList<>();
                    ArrayList<Entry> entrieso2 = new ArrayList<>();
                    String[] xAxis = new String[data.size()];

                    for (int i = 0; data.size() > i; i++) {

                        try {
                            Date date = data.get(i).getDateTime();
                            if (date.after(startdate) && date.before(endate)) {


                                Float O2 = Float.valueOf(data.get(i).getO2reaading().replace("%", "").trim());
                                Float cO2 = Float.valueOf(data.get(i).getCoreading().replace("%", "").trim());

                                entrieso2.add(new Entry(O2, i));
                                entriesco2.add(new Entry(cO2, i));
                                xAxis[i] = data.get(i).getDateTime().toString();
                            }
                        } catch (Exception e) {

                        }
                    }

                    try {

                        ArrayList<LineDataSet> lines = new ArrayList<LineDataSet>();
                        LineDataSet lDataSet1 = new LineDataSet(entrieso2, "O2");
                        lDataSet1.setColor(Color.BLUE);
                        lDataSet1.setCircleColor(Color.BLUE);
                        lines.add(lDataSet1);

                        ArrayList<LineDataSet> linesCo2 = new ArrayList<LineDataSet>();
                        LineDataSet lDataSet2 = new LineDataSet(entriesco2, "Co2");
                        lDataSet2.setColor(Color.RED);
                        lDataSet2.setCircleColor(Color.RED);
                        linesCo2.add(lDataSet2);

                        charto2.setData(new LineData(xAxis, lines));
                        charto2.setTouchEnabled(true);
                        charto2.invalidate();
                        charto2.setDragEnabled(true);

                        chartco2.setData(new LineData(xAxis, linesCo2));
                        chartco2.setTouchEnabled(true);
                        chartco2.invalidate();
                        chartco2.setDragEnabled(true);
                    } catch (Exception e) {

                    }
                }
            });


        } else {
            ArrayList<Entry> entriesco2 = new ArrayList<>();
            ArrayList<Entry> entrieso2 = new ArrayList<>();
            String[] xAxis = new String[0];
            ArrayList<LineDataSet> lines = new ArrayList<LineDataSet>();
            LineDataSet lDataSet1 = new LineDataSet(entrieso2, "O2");
            lDataSet1.setColor(Color.BLUE);
            lDataSet1.setCircleColor(Color.BLUE);
            lines.add(lDataSet1);

            ArrayList<LineDataSet> linesco2 = new ArrayList<LineDataSet>();
            LineDataSet lDataSet2 = new LineDataSet(entriesco2, "Co2");
            lDataSet2.setColor(Color.RED);
            lDataSet2.setCircleColor(Color.RED);
            lines.add(lDataSet2);
            chartco2.setData(new LineData(xAxis, lines));
            chartco2.invalidate();

            linesco2.add(lDataSet2);
            charto2.setData(new LineData(xAxis, lines));
            charto2.invalidate();
        }

    }
    private void loadTable() {
        if ((startdate != null && endate != null)) {
            ReadingTableDao a = AppDatabase.getDatabase(getContext()).readingTableDao();
            a.fetchReading(incubatore.getId()).observeForever(new Observer<List<ReadingTable>>() {
                @Override
                public void onChanged(@Nullable List<ReadingTable> data) {
                    ArrayList<ReadingTable> cursor = new ArrayList<>();
                    for (int i = 0; data.size() > i; i++) {

                        try {
                            Date date = data.get(i).getDateTime();
                            if (date.after(startdate) && date.before(endate)) {
                                cursor.add(data.get(i));
                            }
                        } catch (Exception e) {

                        }
                    }

                    try {
                        ReadingListAdapter mAdapter = new ReadingListAdapter(cursor);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);
                        if(cursor.size()>0) {
                            exporttoexcel.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            exporttoexcel.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                        exporttoexcel.setVisibility(View.GONE);
                    }
                }
            });


        } else {
            exporttoexcel.setVisibility(View.GONE);
        }
    }
    private void exportTheDB() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Exporting database...");
        dialog.show();
        ReadingTableDao a = AppDatabase.getDatabase(getContext()).readingTableDao();
        // a.fetchReadingBetweenDate(String from, String to);

        a.fetchReading(incubatore.getId()).observeForever(new Observer<List<ReadingTable>>() {
            @Override
            public void onChanged(@Nullable List<ReadingTable> data) {
                if (data != null) {
                    try {
                        File myFile;
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                        String TimeStampDB = sdf.format(cal.getTime());
                        //  myFile = new File(extStorageDirectory+"/Export_"+TimeStampDB+".csv");
                        myFile = FileHelper.creatfile("Backup", "Export_" + TimeStampDB, ".csv");
                        final FileOutputStream fOut = new FileOutputStream(myFile);
                        final OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                        myOutWriter.append("ID;TimeStamp;year;O2;CO2;IncubatorID;MONTH");

                        myOutWriter.append("\n");
                        for (ReadingTable entry : data) {

                            if (entry.getDateTime().before(endate) && entry.getDateTime().after(startdate)) {

                                myOutWriter.append(entry.getId() + ";" + entry.getDateTime() + entry.getYear() + ";" + ";" + entry.getCoreading() + ";" + entry.getO2reaading() + ";" + entry.getIncubatoreId() + ";" + entry.getMonth());
                                myOutWriter.append("\n");
                            }
                            Toast.makeText(getContext(), "Exported Successfully", Toast.LENGTH_SHORT).show();
                       }
                        myOutWriter.close();
                        fOut.close();

                        new SingleMediaScanner(getActivity(), myFile);
                    } catch (IOException e) {
                        Log.e(getClass().getSimpleName(), "Could not create or Open the database");

                        Toast.makeText(getContext(), "Error While Export", Toast.LENGTH_SHORT).show();
                    }

                }
                dialog.dismiss();

            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
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
                    Toast.makeText(getContext(), "Permission Denied by you!", Toast.LENGTH_SHORT).show();

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

}
