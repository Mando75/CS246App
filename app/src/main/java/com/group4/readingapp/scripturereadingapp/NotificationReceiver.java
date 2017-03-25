package com.group4.readingapp.scripturereadingapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.CursorJoiner;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Jonathon on 3/8/2017.
 */

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent reading = new Intent(context, MainActivity.class);
        reading.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pi = PendingIntent.getActivity(context, 100, reading, PendingIntent.FLAG_UPDATE_CURRENT);

        // build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pi)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("Daily Reading")
                .setContentText("Daily Reading")
                .setAutoCancel(true);

        // send the notification
        nm.notify(100, builder.build());
    }
}
