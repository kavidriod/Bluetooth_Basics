package com.apps.bluetooth_basics;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    Button turnonButton,turnoffButton,makediscoverableButton,getPairedDeviceButton,checkdeviceSupportButton,startDiscoveryButton,cancelDiscoveryButton;
BluetoothAdapter bluetoothAdapter;
    public final static int REQUEST_BLUETOOTH = 1;
    public final static int MAKE_DISCOVERABLE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        turnonButton = (Button) findViewById(R.id.turnonButton);
        turnoffButton = (Button) findViewById(R.id.turnoffButton);
        makediscoverableButton = (Button) findViewById(R.id.makediscoverableButton);
        getPairedDeviceButton = (Button) findViewById(R.id.getPairedDeviceButton);
        checkdeviceSupportButton = (Button) findViewById(R.id.checkdeviceSupportButton);
        startDiscoveryButton = (Button) findViewById(R.id.startDiscoveryButton);
        cancelDiscoveryButton = (Button) findViewById(R.id.cancelDiscoveryButton);

        turnonButton.setOnClickListener(this);
        turnoffButton.setOnClickListener(this);
        makediscoverableButton.setOnClickListener(this);
        getPairedDeviceButton.setOnClickListener(this);
        checkdeviceSupportButton.setOnClickListener(this);
        startDiscoveryButton.setOnClickListener(this);
        cancelDiscoveryButton.setOnClickListener(this);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.checkdeviceSupportButton:
                if (bluetoothAdapter == null)
                    showToast("Device  is not Supported");
                    else
                showToast("Device  is  Supported");
                break;
            case R.id.turnonButton:
                if (!bluetoothAdapter.isEnabled()){

                    Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBTIntent,REQUEST_BLUETOOTH);

                }else {
                    showToast("Already Enabled");
                }
                break;
            case R.id.turnoffButton:
                if (bluetoothAdapter.isEnabled()) {
                    showToast("Turn off Bluetooth");
                    bluetoothAdapter.disable();
                }else{
                    showToast("Bluetooth is not Enabled");
                }

                break;
            case R.id.makediscoverableButton:

                if (bluetoothAdapter.isEnabled()) {
                    showToast("Making your device discoverable");

                    Intent discoverIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    discoverIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);
                    startActivity(discoverIntent);
        }else{
            showToast("Bluetooth is not Enabled");
        }

                break;
            case R.id.getPairedDeviceButton:
                if (bluetoothAdapter.isEnabled()) {
                    Set<BluetoothDevice> bluetoothDevices = bluetoothAdapter.getBondedDevices();
                    ArrayList<PairedDevices> pairedDevices = new ArrayList<>();
                    if (bluetoothDevices != null && bluetoothDevices.size() > 0){
                        for (BluetoothDevice bluetoothDevice:bluetoothDevices){
                            PairedDevices eachPairedDevices = new PairedDevices(bluetoothDevice.getName(),bluetoothDevice.getAddress());
                            pairedDevices.add(eachPairedDevices);
                        }

                        Intent in = new Intent(getApplicationContext(),PairActivity.class);
                        in.putExtra("PAIREDDEVICES",pairedDevices);
                        startActivity(in);
                    }else {
                        showToast("No paired devices found");
                    }
                }else{
                    showToast("Bluetooth is not Enabled");
                }


                break;
            case R.id.startDiscoveryButton:
                if (bluetoothAdapter.isEnabled()) {
                    Log.i(TAG,"doDiscovery If Discovering is In Progress,Cancel Discovery and then Start");
                    if (bluetoothAdapter.isDiscovering()){
                        bluetoothAdapter.cancelDiscovery();
                    }

                    showToast("By Default Android will do scan about 12 seconds");
                    bluetoothAdapter.startDiscovery();
                }else{
                    showToast("Bluetooth is not Enabled");
                }

                break;

            case R.id.cancelDiscoveryButton:
                if (bluetoothAdapter.isEnabled()) {
                    if (bluetoothAdapter.isDiscovering()){
                        showToast("About to cancel Discovery");
                        bluetoothAdapter.cancelDiscovery();
                    }
                }else{
                    showToast("Bluetooth is not Enabled");
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_BLUETOOTH:
                if (resultCode == RESULT_OK){
                    showToast("BT Enabled");
                }else {
                    showToast("BT is not Enabled");
                }
                break;
        }
    }

    private void showToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}
