package com.example.getsensorvalue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    private ListView listView;

    ArrayList<String> listItems,listValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        String[] sensorArray = (String[]) MainActivity.arrayListLight.toArray(new String[MainActivity.arrayListLight.size()]);


        String  title=getIntent().getStringExtra("title");
        listItems = getIntent().getStringArrayListExtra("time");
        listValue = getIntent().getStringArrayListExtra("value");

        this.setTitle(title);

        listView=findViewById(R.id.listviewid);
        CustomAdapter adapter=new CustomAdapter(this, listItems, listValue);
        listView.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    } @Override
    public void onBackPressed() {
            super.onBackPressed();

    }
}