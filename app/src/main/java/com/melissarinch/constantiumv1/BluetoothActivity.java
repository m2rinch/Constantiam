package com.melissarinch.constantiumv1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

public class BluetoothActivity extends AppCompatActivity {
    final String DEVICE_ADDRESS="20:18:06:28:37:72";
    final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    BluetoothDevice device;
    BluetoothSocket socket;
    OutputStream outputStream;
    InputStream inputStream;
    Button startBlueButton, stopBlueButton;
    TextView textView;
    boolean deviceConnected=false;
    Thread thread;
    byte buffer[];
    int bufferPosition;
    boolean stopThread;
    String blueData = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        startBlueButton = (Button) findViewById(R.id.startBlueBtn);
        stopBlueButton = (Button) findViewById(R.id.stopBlueBtn);
        textView = (TextView) findViewById(R.id.blueText);
    }

    public boolean BTinit()
    {
        boolean found=false;
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
        }
        else {
            // if bluetooth adapter is not null
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableAdapter, 0);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
            if (bondedDevices.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Pair the Device first", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Device is paired.", Toast.LENGTH_SHORT).show();
                for (BluetoothDevice iterator : bondedDevices) {
                    if (iterator.getAddress().equals(DEVICE_ADDRESS)) {
                        device = iterator;
                        found = true;
                        Toast.makeText(getApplicationContext(), "HC-06 found", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
            return found;
        }
        return false;
    }

    public boolean BTconnect()
    {
        boolean connected=true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected=false;
        }
        if(connected)
        {
            try {
                outputStream=socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream=socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return connected;
    }

    void beginListenForData()
    {
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];
        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread)
                {
                    try
                    {
                        int byteCount = inputStream.available();
                        if(byteCount > 0)
                        {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            final String string=new String(rawBytes,"UTF-8");
                            handler.post(new Runnable() {
                                public void run()
                                {
                                    //textView.append(string);
                                    blueData = blueData + string;
                                }
                            });

                        }
                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
                    }
                }
            }
        });

        thread.start();
    }

    public void onClickStartBlue() {

        // initialize bluetooth
        if(BTinit())
        {
            if(BTconnect())
            {
                deviceConnected=true;
                //beginListenForData();
                textView.append("\nConnection Opened!\n");
                // send start flag to Arduino to begin sending data
                String string = ("1");
                try {
                    outputStream.write(string.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                textView.append("Sent Start Command");
                beginListenForData();
            }
        }
    }

    public void onClickStopBlue() throws IOException {
        if(BTinit()) {
            stopThread = true;
            // disable start flag, 5 is an arbitrary number (0 default TX line)
            String string = ("5");
            try {
                outputStream.write(string.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream.close();
            inputStream.close();
            socket.close();
            deviceConnected = false;
            textView.setText("");
            textView.append(blueData);
            textView.append("\nConnection Closed!\n");
        }
    }

}
