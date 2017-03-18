package com.group4.readingapp.scripturereadingapp;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class EnableNotifications extends AppCompatActivity {

    private BroadcastReceiver receiver = new NotificationReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    public void enableNotify(View v){
        IntentFilter intent = new IntentFilter("android.intent.action.NotificationReceiver.class");

        registerReceiver(receiver, intent);

        Toast.makeText(getApplicationContext(),"Notifications enabled!", Toast.LENGTH_SHORT);
    }

    public void disableNotify(View v){

        unregisterReceiver(receiver);
        Toast.makeText(getApplicationContext(), "Notifications disabled!", Toast.LENGTH_SHORT);
    }

}
