package com.group4.readingapp.scripturereadingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class ScheduleViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String filename = intent.getStringExtra("filename");
        setContentView(R.layout.activity_schedule_viewer);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.testingl);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        final WindowManager windowManager = (WindowManager) ScheduleViewer.this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        CalcSched calc = new CalcSched(filename, this, layout, displayMetrics);
        calc.execute();
    }
}
