package com.group4.readingapp.scripturereadingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class ScheduleViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String filename = intent.getStringExtra("filename");
        setContentView(R.layout.activity_schedule_viewer);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.testingl);
        CalcSched calc = new CalcSched(filename, this, layout);
        calc.execute();
    }
}
