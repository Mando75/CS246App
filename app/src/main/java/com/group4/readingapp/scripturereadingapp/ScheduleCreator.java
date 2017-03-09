package com.group4.readingapp.scripturereadingapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleCreator extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final Context context = this;
    protected Spinner startBook;
    protected Spinner startChap;
    protected Spinner endBook;
    protected Spinner endChap;
    protected EditText name;
    protected DatePicker startDate;
    protected DatePicker endDate;
    protected TimePicker readTime;
    protected Button createButton;
    protected ScrollView view;
    private boolean validLocations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_creator);
        setDatePickers();
        createButton = (Button) findViewById(R.id.createButton);
        Log.d("addChap", "Adding item selected listener");
        startBook = (Spinner) findViewById(R.id.schedBooksStart);
        startChap = (Spinner) findViewById(R.id.schedChaptersStart);
        endBook = (Spinner) findViewById(R.id.schedBooksEnd);
        endChap = (Spinner) findViewById(R.id.schedChaptersEnd);
        startBook.setOnItemSelectedListener(this);
        endBook.setOnItemSelectedListener(this);
        name = (EditText) findViewById(R.id.schedNameInput);
        startDate = (DatePicker) findViewById(R.id.startDatePicker);
        endDate = (DatePicker) findViewById((R.id.endDatePicker));
        view = (ScrollView) findViewById(R.id.scrollView1);
        view.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
        validLocations = true;
        endBook.setSelection(14);
        endChap.setSelection(9);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        List<String> startChapter = new ArrayList<String>();
        List<String> endChapter = new ArrayList<String>();
        String startingBook = startBook.getSelectedItem().toString();
        String endingBook = endBook.getSelectedItem().toString();
        startChap.setAdapter(null);
        endChap.setAdapter(null);
        startChapter = chapterMaker(startingBook);
        endChapter = chapterMaker(endingBook);
        ArrayAdapter<String> startAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, startChapter);
        startChap.setAdapter(startAdapter);
        ArrayAdapter<String> endAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, endChapter);
        endChap.setAdapter(endAdapter);
        if(endBook.getSelectedItemPosition() < startBook.getSelectedItemPosition()) {
            validLocations = false;
            Toast toast = Toast.makeText(context, "Please pick a book after your starting book...", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parentView) {
        // your code here
    }

    private void setDatePickers() {
        DatePicker startDate = (DatePicker) findViewById(R.id.startDatePicker);
        startDate.setMinDate(System.currentTimeMillis() - 1000);
        DatePicker endDate = (DatePicker) findViewById(R.id.endDatePicker);
        // min end date is 1 week
        endDate.setMinDate(System.currentTimeMillis() + 604800000);
    }

    private List<String> chapterMaker(String starter) {
        List<String> startChapter = new ArrayList<String>();
        switch (starter) {
            case "1 Nephi":
                for (int i = 0; i < 22; i++) {
                    startChapter.add("Chapter " + (i + 1));
                }
                break;
            case "2 Nephi":
                for (int i = 0; i < 33; i++) {
                    startChapter.add("Chapter " + (i + 1));
                }
                break;
            case "Jacob":
                for (int i = 0; i < 7; i++) {
                    startChapter.add("Chapter " + (i + 1));
                }
                break;
            case "Enos":
                startChapter.add("Chapter 1");
                break;
            case "Jarom":
                startChapter.add("Chapter 1");
                break;
            case "Omni":
                startChapter.add("Chapter 1");
                break;
            case "Words of Mormon":
                startChapter.add("Chapter 1");
                break;
            case "Mosiah":
                for (int i = 0; i < 29; i++) {
                    startChapter.add("Chapter " + (i + 1));
                }
                break;
            case "Alma":
                for (int i = 0; i < 63; i++) {
                    startChapter.add("Chapter " + (i + 1));
                }
                break;
            case "Helaman":
                for (int i = 0; i < 16; i++) {
                    startChapter.add("Chapter " + (i + 1));
                }
                break;
            case "3 Nephi":
                for (int i = 0; i < 30; i++) {
                    startChapter.add("Chapter " + (i + 1));
                }
                break;
            case "4 Nephi":
                startChapter.add("Chapter 1");
                break;
            case "Mormon":
                for (int i = 0; i < 9; i++) {
                    startChapter.add("Chapter " + (i + 1));
                }
                break;
            case "Ether":
                for (int i = 0; i < 15; i++) {
                    startChapter.add("Chapter " + (i + 1));
                }
                break;
            case "Moroni":
                for (int i = 0; i < 10; i++) {
                    startChapter.add("Chapter " + (i + 1));
                }
                break;
            default:
                break;
        }
        return startChapter;
    }

    public void createSchedule(View view) {
        if (name.getText().toString().equals("")) {
            validLocations = false;
        }
        if (validLocations) {
            List<String> schedInfo = new ArrayList<>();
            schedInfo.add(name.getText().toString());
            schedInfo.add(startDate.getYear() + "-" + startDate.getMonth() + "-" + startDate.getDayOfMonth());
            schedInfo.add(endDate.getYear() + "-" + endDate.getMonth() + "-" + endDate.getDayOfMonth());
//        schedInfo.add(readTime.getCurrentHour() + ":" + readTime.getCurrentMinute());
            schedInfo.add("12:35");
            schedInfo.add(startBook.getSelectedItem().toString());
            schedInfo.add(startChap.getSelectedItem().toString());
            schedInfo.add("20");
            schedInfo.add(endBook.getSelectedItem().toString());
            schedInfo.add(endBook.getSelectedItem().toString());
            schedInfo.add("30");
            Log.d("Schedule Create", "Launching async task...");
            new CreateSchedule(schedInfo, context).execute();
            scheduleNotifications();
        } else {
            Toast toast = Toast.makeText(context, "Something seems to be wrong... Please check the info you provided.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void scheduleNotifications(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 14);

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 100, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);
    }
}

