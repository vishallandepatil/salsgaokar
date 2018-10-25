package com.example.vishallandepatil.incubatore;


import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static android.content.ContentValues.TAG;


public class Reding_fragment extends Fragment {
  ImageView bluetothenable;
    private BluetoothAdapter myBluetooth = null;
    private Set pairedDevices;
    ListView devicelist;
    String info ;
    String address;

    private ProgressDialog progress;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    // Make an int
    public Reding_fragment() {

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
        View view=inflater.inflate(R.layout.fragment_reding_fragment, container, false);
        bluetothenable=  view.findViewById(R.id.bluetothenable);

        devicelist = (ListView)view.findViewById(R.id.listView);

        bluetothenable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBluetooth = BluetoothAdapter.getDefaultAdapter();
                if(myBluetooth == null)
                {
                    //Show a mensag. that thedevice has no bluetooth adapter
                    Toast.makeText(getContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
                    //finish apk

                }
                else
                {
                    if (myBluetooth.isEnabled())
                    {



                        pairedDevicesList(); //method that will be called
                    }
                    else
                    {
                        //Ask to the user turn the bluetooth on
                        Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(turnBTon,1);
                    }
                }
            }
        });
        return view;

    }

    private void pairedDevicesList()
    {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size()>0)
        {
            for(Object bt : pairedDevices)
            {
                list.add(((BluetoothDevice)bt).getName() + "\n" + ((BluetoothDevice)bt).getAddress()); //Get the device's name and the address
            }
        }
        else
        {
            Toast.makeText(getContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked

    }
    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView av, View v, int arg2, long arg3)
        {
            // Get the device MAC address, the last 17 chars in the View
            info = ((TextView) v).getText().toString();
            address = info.substring(info.length() - 17);
            // Make an intent to start next activity.
            new ConnectBT().execute();

        }
    };

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(getContext(), "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
//                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    btSocket = createBluetoothSocket(dispositivo);


                    //create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
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
            if(Build.VERSION.SDK_INT >= 10){
                try {
                    final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
                    return (BluetoothSocket) m.invoke(device, myUUID);
                } catch (Exception e) {
                    Log.e(TAG, "Could not create Insecure RFComm Connection",e);
                }
            }
            return  device.createRfcommSocketToServiceRecord(myUUID);
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
               // finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
                try {
                    OutputStream tmpOut = btSocket.getOutputStream();
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred when creating output stream", e);
                }
            }
            progress.dismiss();
        }
        
        private void msg(String s)
        {
            Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
        }
        private void Disconnect()
        {
            if (btSocket!=null) //If the btSocket is busy
            {
                try
                {
                    btSocket.close(); //close connection
                }
                catch (IOException e)
                { msg("Error");}
            }
             }
    }


}
