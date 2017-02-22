package com.group4.readingapp.scripturereadingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;

public class ScheduleCreator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_creator);
        DatePicker startDate = (DatePicker) findViewById(R.id.startDatePicker);
        startDate.setMinDate(System.currentTimeMillis() - 1000);
        DatePicker endDate = (DatePicker) findViewById(R.id.endDatePicker);
        // min end date is 1 week
        endDate.setMinDate(System.currentTimeMillis() + 604800000);
    }
}
