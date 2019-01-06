package com.example.vishallandepatil.incubatore.reading;


import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.vishallandepatil.incubatore.home.IncubatorlistFragment;
import com.example.vishallandepatil.incubatore.home.MainActivity;
import com.example.vishallandepatil.incubatore.home.adpators.AdapterIncubatorList;
import com.example.vishallandepatil.incubatore.login.AppDatabase;

import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.reading.database.ReadingTable;
import com.example.vishallandepatil.incubatore.reading.database.ReadingTableDao;
import com.example.vishallandepatil.incubatore.setting.NameEditDilog;
import com.example.vishallandepatil.incubatore.setting.SettingFragment;
import com.example.vishallandepatil.incubatore.setting.database.Incubatore;
import com.example.vishallandepatil.incubatore.setting.database.IncubatorsDao;
import com.example.vishallandepatil.incubatore.trend.TrendFragment;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;



import static android.content.ContentValues.TAG;


public class Reding_fragment extends Fragment {
    ImageView bluetothenable;
    private BluetoothAdapter myBluetooth = null;
    private Set pairedDevices;
    Button btn,btnOff,btnOn;
    ListView devicelist;
    String info;
    TextView lable,lableco,lableco2,name;
    String address;
    LinearLayout readingLayout;
    private ProgressDialog progress;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    boolean stopWorker = false;
    int  readBufferPosition = 0;
    byte[]  readBuffer = new byte[1024];
    private Incubatore incubatore;
    ListView listincubators;
    View reading;
    public static Reding_fragment newInstance() {
        Reding_fragment fragment = new Reding_fragment();
        Bundle bundle= new Bundle();
     //   bundle.putParcelable("Incubatore",incubatore);
        fragment.setArguments(bundle);
        return fragment;
    }
    public Reding_fragment() {

    }
    TextView status;
    private final BroadcastReceiver bStateReceiver = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1) == BluetoothAdapter.STATE_OFF) {
                    // Bluetooth is disconnected, do handling here
                    status.setText("Off");
                    getActivity().onBackPressed();
//                    Toast.makeText(getContext(),"hhd",Toast.LENGTH_SHORT).show();
                }
                if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1) == BluetoothAdapter.STATE_ON) {
                    // Bluetooth is disconnected, do handling here
                 //   getActivity().onBackPressed();
                    status.setText("ON");
                    //  Toast.makeText(getContext(),"hhggd",Toast.LENGTH_SHORT).show();

                }
            }
          if ("android.bluetooth.device.action.ACL_DISCONNECTED".equals(action)) {
                    // Bluetooth is disconnected, do handling here
  //                  status.setText("DISCONNECTED");
                  //  getActivity().onBackPressed();
//                    Toast.makeText(getContext(),"hhd",Toast.LENGTH_SHORT).show();
              }


            //BluetoothAdapter.STATE_DISCONNECTED


        }

    };



    public Incubatore getIncubatore() {
            return  incubatore;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          ///  incubatore= getArguments().getParcelable("Incubatore");

        }
        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);

        filter1.addAction(BluetoothDevice.ACTION_FOUND);
        filter1.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter1.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);


        getActivity().registerReceiver(bStateReceiver, filter1);
    }
    private Date getDateTime() {

        Date date = new Date();
        return date;
    }
    private String getYear() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                2014-10-07 02:34:56
                "yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    private String getMonth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                2014-10-07 02:34:56
                "MMM", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    private String getDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                2014-10-07 02:34:56
                "dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Disconnect();
        getActivity().unregisterReceiver(bStateReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reding_fragment, container, false);
        listincubators= view.findViewById(R.id.listincubators);
        reading= view.findViewById(R.id.reading);
        bluetothenable = view.findViewById(R.id.bluetothenable);
        status = (TextView) view.findViewById(R.id.status);
        btn = view.findViewById(R.id.btn);
        btnOn = view.findViewById(R.id.btnon);
        btnOff = view.findViewById(R.id.btnoff);
        lable = view.findViewById(R.id.lable);
        lableco = view.findViewById(R.id.lableco);
        lableco2 = view.findViewById(R.id.lableco2);
        readingLayout = view.findViewById(R.id.readingLayout);
        name = view.findViewById(R.id.name);
        name.setText("");
        devicelist = (ListView) view.findViewById(R.id.listView);


//        Date date=getDateTime();
//        for(int i=1;i<=30;i++)
//        {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            calendar.add(Calendar.DATE, -i);
//            Date yesterday = calendar.getTime();
//            ReadingTable reading =   new ReadingTable();
//            reading.setCoreading("2%");
//            reading.setO2reaading("5%");
//            reading.setDateTime(yesterday);
//            reading.setMonth(getMonth());
//            reading.setYear(getYear());
//            reading.setDay(getDay());
//            reading.setIncubatoreId(1);
//            new InsertReading(reading,getActivity(),btn,lable).execute();
//        }





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn.getText().toString().equalsIgnoreCase(getResources().getString(R.string.storereading))) {

                    ReadingTable reading =   new ReadingTable();
                    reading.setCoreading(lableco.getText().toString());
                    reading.setO2reaading(lableco2.getText().toString());
                    reading.setDateTime(getDateTime());
                    reading.setMonth(getMonth());
                    reading.setYear(getYear());
                    reading.setDay(getDay());
                    reading.setIncubatoreId(incubatore.getId());
                    new InsertReading(reading,getActivity(),btn,lable).execute();

                }
                else
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    if (myBluetooth == null) {
                        //Show a mensag. that thedevice has no bluetooth adapter
                        Toast.makeText(getContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
                        //finish apk

                    } else {
                        if (myBluetooth.isEnabled()) {


                            pairedDevicesList(); //method that will be called
                        } else {
                            //Ask to the user turn the bluetooth on
                            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(turnBTon, 1);
                        }
                    }
                }
            }
        });

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOnLed();      //method to turn on
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffLed();   //method to turn off
            }
        });
     /*   btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect(); //close connection
            }
        });*/


        return view;

    }

    private void pairedDevicesList() {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size() > 0) {

            for (Object bt : pairedDevices) {
                list.add(((BluetoothDevice) bt).getName() + "\n" + ((BluetoothDevice) bt).getAddress()); //Get the device's name and the address
            }
        } else {
            Toast.makeText(getContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener);
        devicelist.setVisibility(View.VISIBLE);
        lable.setText(getResources().getString(R.string.selecthbluetooth));
        //Method called when the device from the list is clicked

    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int arg2, long arg3) {
            // Get the device MAC address, the last 17 chars in the View
           info = ((TextView) v).getText().toString();
            address = info.substring(info.length() - 17);
           // Make an intent to start next activity.
            new ConnectBT().execute();




        }
    };

    private   class InsertReading extends AsyncTask<Void, Void, Long> {
        ReadingTable readingTable;
        Activity context;
        Button btn;
        TextView label;

        public InsertReading(ReadingTable readingTable,Activity context,Button btn,TextView label) {
            this.readingTable=readingTable;
            this.context=context;
            this.btn=btn;
            this.label=label;


        }

        @Override
        protected Long doInBackground(Void... voids) {


            Long a= AppDatabase.getDatabase(context).readingTableDao().insertReading(readingTable);
            return a;
        }
        @Override
        protected void onPostExecute(Long agentsCount) {


            if (agentsCount > 0) {
                label.setText("Reading Store Sucessfully...");
              //  btn.setText("");
                Toast.makeText(context,"Reading Store Successfully...",Toast.LENGTH_LONG).show();


                final UserActionDilog cdd =new UserActionDilog(context);
                cdd.show();
                View.OnClickListener onClickListener=new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        switch (v.getId()) {
                            case R.id.btn_yes:
                                cdd.dismiss();
                                lableco2.setText("");
                                lableco.setText("");
                                msg("Bluetooth is Connected. Please Wait for Collecting Data");

                                showIncubators();
                                break;
                            case R.id.btn_no:
                                cdd.dismiss();
                                context.onBackPressed();
                                break;
                            default:
                                break;
                        }

                    }
                };
                cdd.setOnClickListener( onClickListener);

            } else {
                Toast.makeText(context, "Reading not Store Properly...", Toast.LENGTH_LONG).show();

            }
        }

    }


    private void Disconnect() {
        if (btSocket != null) //If the btSocket is busy
        {
            try {
                btSocket.close(); //close connection
                msg("You Are Dissconnected");
            } catch (IOException e) {
                msg("You Are Dissconnected");

            }
        }
    }
    private void turnOffLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write('F');
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    private void turnOnLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write('O');
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    void beginListenForData()
    {
        final Handler handler = new Handler();
        final byte delimiter = 59; //This is the ASCII code for a newline character


        Thread workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
//                        if()
//                        {
//                            getActivity().onBackPressed();
//                        }
                        int bytesAvailable = btSocket.getInputStream().available();

                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            btSocket.getInputStream().read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                            try {
                                                lable.setText("Reading Completed");
                                                btn.setText(getResources().getString(R.string.storereading));
                                                readingLayout.setVisibility(View.VISIBLE);
                                                btn.setVisibility(View.VISIBLE);
                                                lableco2.setText(data.split(",")[0]);
                                                lableco.setText(data.split(",")[1]);


                                            }
                                            catch (Exception e)
                                            {

                                            }
                                        }
                                    });
                                }
                                else
                                {try{
                                    readBuffer[readBufferPosition++] = b;
                                }
                                catch (Exception e)
                                {

                                }
                                }

                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();

    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(getContext(), "Connecting...", "Please wait!!!");
            lable.setText(getResources().getString(R.string.connectingbluetooth));
            status.setText("Connecting");
//show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
//                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    btSocket = createBluetoothSocket(dispositivo);

                    //create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here

                try {
                    btSocket.close();


                } catch (IOException e2) {


                    //errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
                }

            }
            return null;
        }

        private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
            if (Build.VERSION.SDK_INT >= 10) {
                try {
                    final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class});
                    return (BluetoothSocket) m.invoke(device, myUUID);
                } catch (Exception e) {
                    Log.e(TAG, "Could not create Insecure RFComm Connection", e);
                }
            }
            return device.createRfcommSocketToServiceRecord(myUUID);
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                status.setText("Disconnected");
                status.setBackgroundColor(getResources().getColor(R.color.red));


                // finish();
            } else {

              //fdgdf


                msg("Bluetooth is Connected. Please Wait for Collecting Data");

                status.setText("Connected");
                status.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                isBtConnected = true;
                devicelist.setVisibility(View.GONE);
                btn.setVisibility(View.GONE);


                showIncubators();







            }
            progress.dismiss();
        }

        private void msg(String s) {
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            lable.setText(s);


        }



    }

    void showIncubators()
    {
        try {
           // btSocket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        devicelist.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);
        IncubatorsDao a=  AppDatabase.getDatabase(getContext()).uncubatorsDao();
        reading.setVisibility(View.GONE);
        a.fetchAllIncubators().observeForever(new Observer<List<Incubatore>>() {
            @Override
            public void onChanged(@Nullable final List<Incubatore> list) {

                final AdapterIncubatorList adapter = new AdapterIncubatorList(getActivity(), (ArrayList<Incubatore>) list,Reding_fragment.this);
                if(list.size()>0)
                {
                    listincubators.setVisibility(View.VISIBLE);
                }

                listincubators.setAdapter(adapter);

                listincubators.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                        listincubators.setVisibility(View.GONE);
                        reading.setVisibility(View.VISIBLE);
                        incubatore=adapter.getItem(position);
                        name.setText(incubatore.getName());
                        readingLayout.setVisibility(View.GONE);

                        beginListenForData();

                       /* devicelist.setVisibility(View.GONE);
                        lable.setText("Reading Completed");
                        btn.setText(getResources().getString(R.string.storereading));
                        readingLayout.setVisibility(View.VISIBLE);
                        btn.setVisibility(View.VISIBLE);
                        lableco2.setText("25 %");
                        lableco.setText("35 %");*/

                    }
                });

            }
        });

    }
    private void msg(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
        lable.setText(s);

    }

}
