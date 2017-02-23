package com.group4.readingapp.scripturereadingapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ScheduleCreator extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final Context context = this;
    protected Spinner startBook;
    protected Spinner startChap;
    protected Spinner endBook;
    protected Spinner endChap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_creator);
        setDatePickers();
        Log.d("addChap", "Adding itemselected listener");
        startBook = (Spinner) findViewById(R.id.schedBooksStart);
        startChap = (Spinner) findViewById(R.id.schedChaptersStart);
        endBook = (Spinner) findViewById(R.id.schedBooksEnd);
        endChap = (Spinner) findViewById(R.id.schedChaptersEnd);
        startBook.setOnItemSelectedListener(this);
        endBook.setOnItemSelectedListener(this);

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
        Log.d("hello", "hellos?");
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
}

