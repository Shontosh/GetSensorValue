package com.example.getsensorvalue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    ArrayList<String> time_value;
    ArrayList<String>   sensor_value;
    Context context;
    private LayoutInflater inflater;
    public CustomAdapter(Context context, ArrayList<String> time_value, ArrayList<String> sensor_value) {

        this.context = context;
        this.time_value = time_value;
        this.sensor_value = sensor_value;
    }

    @Override
    public int getCount() {
        return sensor_value.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.sample_view,null);

        }
        TextView timwTextView=convertView.findViewById(R.id.txt_time_id);
        TextView sensorTextView=convertView.findViewById(R.id.text_value_id);
        timwTextView.setText(time_value.get(position));
        sensorTextView.setText(sensor_value.get(position));

        return convertView;
    }
}
