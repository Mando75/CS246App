package com.group4.readingapp.scripturereadingapp;

import android.content.Context;
import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by starw on 3/6/2017.
 */

class CreateSchedule extends AsyncTask<Void, Void, Void> {

    protected Schedule schedule;
    private List<String> infoList;
    Context context;
    static final String TAG = "Create Schedule Debug";

    public CreateSchedule(List<String> scheduleInfo, Context theContext) {
        infoList = scheduleInfo;
        schedule = new Schedule(infoList);
        context = theContext;
        Log.d(TAG, "Successfully constructed");
    }


    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, " About to launch doInBackgroud");
        schedule.saveToFile(context, infoList.get(schedule.NAME)+ ".json");
        Log.d(TAG, " finished doinbackground");
        return null;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "Launching Pre execute");
        Toast toast = Toast.makeText(context, "Creating file...", Toast.LENGTH_SHORT);
        toast.show();
        Log.d(TAG, "Finished preexecute");
    }


    protected void onPostExecute(Void... params) {
        Log.d(TAG, " About to launch postExecute");
        Toast toast = Toast.makeText(context, "FileSaved...", Toast.LENGTH_SHORT);
        toast.show();
        Log.d(TAG, " Finished post");
    }



}
