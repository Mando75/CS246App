package com.group4.readingapp.scripturereadingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.Format;
import java.text.ParseException;
import java.util.Date;

public class ScheduleViewer extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String filename = intent.getStringExtra("filename");
        setContentView(R.layout.activity_schedule_viewer);

        // put the schedule information on the header card
        Schedule schedule = new Schedule();
        schedule.loadFromFile(this, filename);

        // set the schedule name
        TextView schedCard = (TextView) findViewById(R.id.schedCardName);
        String schedName = filename.replace(".json", "");
        schedCard.setText(schedName.replace("_", " "));

        // set the reading set
        schedCard = (TextView) findViewById(R.id.schedCardReadings);
        String text = schedule.getStartPos().get("book").getAsString() + " " + schedule.getStartPos().get("chapter").getAsString();
        String text2 = schedule.getEndPos().get("book").getAsString() + " " + schedule.getEndPos().get("chapter").getAsString();
        schedCard.setText(text + " - " + text2);

        // set date range
        Format formatter = new java.text.SimpleDateFormat("dd MMMM yyyy");
        schedCard = (TextView) findViewById(R.id.schedCardDates);
        text = "error";
        text2 = "error";
        Date start = schedule.getStartDate();
        Date end = schedule.getEndDate();
        text = formatter.format(start);
        text2 = formatter.format(end);
        schedCard.setText(text + " - " + text2);



        RelativeLayout layout = (RelativeLayout) findViewById(R.id.testingl);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        final WindowManager windowManager = (WindowManager) ScheduleViewer.this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        CalcSched calc = new CalcSched(filename, this, layout, displayMetrics,ScheduleViewer.this);
        calc.execute();
    }
    public void completed(Boolean finished){
        if (!finished){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        else{
            finish();
        }

    }
}
