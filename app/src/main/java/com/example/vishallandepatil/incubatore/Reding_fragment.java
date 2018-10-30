package com.example.vishallandepatil.incubatore;


import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import landepatil.vishal.sqlitebuilder.Query;

import static android.content.ContentValues.TAG;


public class Reding_fragment extends Fragment {
    ImageView bluetothenable;
    private BluetoothAdapter myBluetooth = null;
    private Set pairedDevices;
    Button btn,btnOff,btnOn;
    ListView devicelist;
    String info;
    TextView lable,lableco,lableco2;
    String address;
    LinearLayout readingLayout;

    private ProgressDialog progress;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    boolean stopWorker = false;
    int  readBufferPosition = 0;
    byte[]  readBuffer = new byte[1024];
    // Make an int
    public Reding_fragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reding_fragment, container, false);
        bluetothenable = view.findViewById(R.id.bluetothenable);
        btn = view.findViewById(R.id.btn);
        btnOn = view.findViewById(R.id.btnon);
        btnOff = view.findViewById(R.id.btnoff);
        lable = view.findViewById(R.id.lable);
        lableco = view.findViewById(R.id.lableco);
        lableco2 = view.findViewById(R.id.lableco2);
        readingLayout = view.findViewById(R.id.readingLayout);


        devicelist = (ListView) view.findViewById(R.id.listView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn.getText().toString().equalsIgnoreCase(getResources().getString(R.string.storereading))) {

                    ReadingTable reading =   new ReadingTable();
                    reading.setCoreading(lableco.getText().toString());
                    reading.setO2reaading(lableco2.getText().toString());
                    reading.setDateTime(getDateTime());
                    if(Query.createQuery(new DBHelper(getContext())).insert(reading))
                    {
                        lable.setText("Reading Store Sucessfully...");
                        btn.setText("");




                    }


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
           // info = ((TextView) v).getText().toString();
           // address = info.substring(info.length() - 17);
            // Make an intent to start next activity.
           // new ConnectBT().execute();

devicelist.setVisibility(View.GONE);
            lable.setText("Reading Completed");
            btn.setText(getResources().getString(R.string.storereading));
            readingLayout.setVisibility(View.VISIBLE);
            btn.setVisibility(View.VISIBLE);
            lableco2.setText("25 %");
            lableco.setText("35 %");


        }
    };

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
                                {
                                    readBuffer[readBufferPosition++] = b;
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
                // finish();
            } else {
                msg("Bluetooth is Connected. Please Wait for Collecting Data");

                isBtConnected = true;
                devicelist.setVisibility(View.GONE);
                btn.setVisibility(View.GONE);
                beginListenForData();
                try {
                    OutputStream tmpOut = btSocket.getOutputStream();
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred when creating output stream", e);
                }
            }
            progress.dismiss();
        }

        private void msg(String s) {
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            lable.setText(s);

        }



    }
    private void msg(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
        lable.setText(s);

    }

}
