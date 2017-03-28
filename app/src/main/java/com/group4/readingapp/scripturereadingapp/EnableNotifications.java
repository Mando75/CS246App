package com.group4.readingapp.scripturereadingapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class EnableNotifications extends AppCompatActivity {

    private TimePicker timePicker;
    private BroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        timePicker = (TimePicker) findViewById(R.id.timer);
        timePicker.setIs24HourView(true);
        receiver = new NotificationReceiver();
    }



    public void enableNotify(View v){

        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        c.set(Calendar.MINUTE, timePicker.getCurrentMinute());

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 100, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);


        Toast t = Toast.makeText(getApplicationContext(),"Notifications enabled!", Toast.LENGTH_SHORT);
        t.show();
    }

    public void disableNotify(View v){
        unregisterReceiver(receiver);

        Toast t = Toast.makeText(getApplicationContext(), "Notifications disabled!", Toast.LENGTH_SHORT);
        t.show();
    }

}
