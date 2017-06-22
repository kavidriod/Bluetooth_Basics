package com.apps.bluetooth_basics;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PairActivity extends AppCompatActivity {

    ArrayList<PairedDevices> pairedDevices;
    DeviceListAdapter deviceListAdapter;
    private AbsListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair);

        listView = (AbsListView) findViewById(android.R.id.list);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle intent = getIntent().getExtras();
        pairedDevices = (ArrayList<PairedDevices>) intent.get("PAIREDDEVICES");
        deviceListAdapter = new DeviceListAdapter(getApplicationContext(),pairedDevices);
        listView.setAdapter(deviceListAdapter);

    }


    class DeviceListAdapter extends BaseAdapter{

        Context context;
        List<PairedDevices> pairedDevices;
        LayoutInflater layoutInflater;

        public  DeviceListAdapter(Context context,List<PairedDevices> pairedDevices){
            this.context = context;
            this.pairedDevices =  pairedDevices;
            layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return pairedDevices.size();
        }

        @Override
        public Object getItem(int position) {
            return pairedDevices.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;

            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.paired_items,null);
                viewHolder = new ViewHolder();
                viewHolder.deviceName = (TextView) convertView.findViewById(R.id.titleTextView);
                viewHolder.macAddress = (TextView) convertView.findViewById(R.id.macAddress);

                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            PairedDevices eachPairedDevices = pairedDevices.get(position);

            viewHolder.deviceName.setText(eachPairedDevices.getName());
            viewHolder.macAddress.setText(eachPairedDevices.getMacAddress());

            return convertView;
        }
    }


    private  class ViewHolder{
        TextView deviceName,macAddress;
    }
}
