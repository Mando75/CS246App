package com.group4.readingapp.scripturereadingapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.content.Context;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jonathon on 3/2/2017.
 */

public class ScheduleNotificaitons {
    DatePicker startDate;
    DatePicker endDate;
    TimePicker time;
    Context mContext;
    private final String TAG = "startNotifications";

    ScheduleNotificaitons(DatePicker sd, DatePicker ed, TimePicker t){
        startDate = sd;
        endDate = ed;
        time = t;
    }

    public void startNotifications(){
        mContext.getApplicationContext();

        Calendar c = Calendar.getInstance();
        Calendar c1 = Calendar.getInstance();

        int year   = startDate.getYear();
        int month = startDate.getMonth();
        int day    = startDate.getDayOfMonth();

        int endYear  = endDate.getYear();
        int endMonth = endDate.getMonth();
        int endDay   = endDate.getDayOfMonth();

        c1.set(year, month, day);

        Date minDate = c1.getTime();

        Calendar c2 = Calendar.getInstance();

        c2.set(endYear, endMonth, endDay);

        Date endDate = c2.getTime();

        Date current = c.getTime();

        Log.d(TAG, "starting notification creation");
        // create a notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.drawable.ic_android_black_24dp)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mContext, ResultActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ResultActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        int mId = 1;


        Log.d(TAG, "finished creating notification")
        while (minDate.getTime() <= current.getTime() && endDate.getTime() >= current.getTime()){
            mNotificationManager.notify(mId, mBuilder.build());
        }
        
    }
    
}
