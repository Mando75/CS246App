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
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import static java.lang.Integer.parseInt;
import static java.lang.StrictMath.abs;

/**
 * @author Bryan Muller, Jonathon Fidiam, Loren Miller
 * @version 1.0
 *
 * This Class builds the activity that allows users to create schedules
 * <p>
 *     The user is presented with various inputs that allow them to pick
 *     a name for the schedule, a start and end date, and starting and ending
 *     locations.
 *     These items are then parsed and sent to a CreateSchedule AsnycTask to generate
 *     a Schedule object, and save it to a file. This activity will then relaunch the main activity.
 * </p>
 */

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
    public static final String TAG = ScheduleCreator.class.getSimpleName();

    protected NavigableMap<Integer, String> chapToBook;
    protected Map<String, Integer> bookToRef;

    /**
     *
     * @param savedInstanceState
     * Basic set up for the activity
     * <p>onCreate sets all the variables to the items in the layout.
     * It will also set several onClick and onSelect listeners to update
     * the chapter spinners based on the values entered in the book selectors.</p>
     */
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
        endBook.setSelection(14);
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
    }

    /**
     * setting listeners to update chapter spinners
     * <p>Sets a listener to populate the chapter spinners with the proper amount
     * of chapters when a book is selected. Also includes brief error checking to make sure it
     * is a valid entry. </p>
     * @param parent
     * @param view
     * @param pos
     * @param id
     *
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        validLocations = true;
        List<String> startChapter = new ArrayList<String>();
        List<String> endChapter = new ArrayList<String>();
        String startingBook = startBook.getSelectedItem().toString();
        String endingBook = endBook.getSelectedItem().toString();
        startChap.setAdapter(null);
        endChap.setAdapter(null);
        startChapter = chapterMaker(startingBook);
        endChapter = endChapterMaker(endingBook);
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

    /**
     * A simple function to set the Date pickers to appropriate values
     */
    private void setDatePickers() {
        DatePicker startDate = (DatePicker) findViewById(R.id.startDatePicker);
        startDate.setMinDate(System.currentTimeMillis() - 1000);
        DatePicker endDate = (DatePicker) findViewById(R.id.endDatePicker);
        // min end date is 1 week
        endDate.setMinDate(System.currentTimeMillis() + 604800000);
    }

    /**
     * builds a string array used to populate the chapter spinners
     * based on which book the user selected.
     * @param starter
     * @return startChapter
     *
     *
     */
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

    private List<String> endChapterMaker(String starter) {
        List<String> endChapter = new ArrayList<String>();
        switch (starter) {
            case "1 Nephi":
                for (int i = 22; i > 0; i--) {
                    endChapter.add("Chapter " + (i));
                }
                break;
            case "2 Nephi":
                for (int i = 33; i > 0; i--) {
                    endChapter.add("Chapter " + (i));
                }
                break;
            case "Jacob":
                for (int i = 7; i > 0; i--) {
                    endChapter.add("Chapter " + (i));
                }
                break;
            case "Enos":
                endChapter.add("Chapter 1");
                break;
            case "Jarom":
                endChapter.add("Chapter 1");
                break;
            case "Omni":
                endChapter.add("Chapter 1");
                break;
            case "Words of Mormon":
                endChapter.add("Chapter 1");
                break;
            case "Mosiah":
                for (int i = 29; i > 0; i--) {
                    endChapter.add("Chapter " + (i));
                }
                break;
            case "Alma":
                for (int i = 63; i > 0; i--) {
                    endChapter.add("Chapter " + (i));
                }
                break;
            case "Helaman":
                for (int i = 16; i > 0; i--) {
                    endChapter.add("Chapter " + (i));
                }
                break;
            case "3 Nephi":
                for (int i = 30; i > 0; i--) {
                    endChapter.add("Chapter " + (i));
                }
                break;
            case "4 Nephi":
                endChapter.add("Chapter 1");
                break;
            case "Mormon":
                for (int i = 9; i > 0; i--) {
                    endChapter.add("Chapter " + (i));
                }
                break;
            case "Ether":
                for (int i = 15; i > 0; i--) {
                    endChapter.add("Chapter " + (i));
                }
                break;
            case "Moroni":
                for (int i = 10; i > 0; i--) {
                    endChapter.add("Chapter " + (i));
                }
                break;
            default:
                break;
        }
        return endChapter;
    }

    /**
     * Gathers the data from the various inputs, and uses them to create a List that can be
     * passed to the CreateSchdule object. It will additionally set the notifications.
     * @see CreateSchedule
     * @see NotificationReceiver
     * @param view
     *
     */
    public void createSchedule(View view) {

        setUpBookChapMaps();
        if (!name.getText().toString().equals("")) {

            if (validLocations) {
                List<String> schedInfo = new ArrayList<>(12);
                for (int i = 0; i < 12; i++) {
                    schedInfo.add("");
                }
                schedInfo.add(Schedule.NAME, name.getText().toString());
                schedInfo.add(Schedule.START_DATE, startDate.getYear() + "-" + (startDate.getMonth() + 1) + "-" + startDate.getDayOfMonth());

                schedInfo.add(Schedule.END_DATE, endDate.getYear() + "-" + (endDate.getMonth() + 1) + "-" + endDate.getDayOfMonth());
                Log.d(TAG, "createSchedule: start date = " + schedInfo.get(Schedule.START_DATE));
                Log.d(TAG, "createSchedule: end date = " + schedInfo.get(Schedule.END_DATE));
//        schedInfo.add(readTime.getCurrentHour() + ":" + readTime.getCurrentMinute());
                schedInfo.add(Schedule.READ_TIME,"12:35");
                schedInfo.add(Schedule.START_BOOK, startBook.getSelectedItem().toString());
                schedInfo.add(Schedule.START_CHAPTER, startChap.getSelectedItem().toString().replace("Chapter ", ""));
                schedInfo.add(Schedule.END_BOOK, endBook.getSelectedItem().toString());
                schedInfo.add(Schedule.END_CHAPTER, endChap.getSelectedItem().toString().replace("Chapter ", ""));
                Log.d(TAG, "createSchedule: " + startBook.getSelectedItem().toString());
                int startID = getBookToRef(startBook.getSelectedItem().toString(), Integer.parseInt(startChap.getSelectedItem().toString().replace("Chapter ", "")));
                Log.d(TAG, "createSchedule: " + startID);
                schedInfo.add(Schedule.CHAPTER_ID, Integer.toString(startID));
                int endID = getBookToRef(endBook.getSelectedItem().toString(), Integer.parseInt(endChap.getSelectedItem().toString().replace("Chapter ", "")));
                schedInfo.add(Schedule.END_CHAPTER_ID, Integer.toString(endID));
                Log.d("Schedule Create", "Launching async task...");
                new CreateSchedule(schedInfo, context).execute();
                finish();
            } else {
                Toast toast = Toast.makeText(context, "Something seems to be wrong... Please check the info you provided.", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(context, "Something seems to be wrong... Please check the name you entered", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void setUpBookChapMaps(){
    // build the chapToBook map

        chapToBook = new TreeMap<Integer, String>();
        bookToRef = new TreeMap<String, Integer>();

        chapToBook.put(1, "1 Nephi");
        chapToBook.put(23, "2 Nephi");
        chapToBook.put(56, "Jacob");
        chapToBook.put(64, "Enos");
        chapToBook.put(65, "Jarom");
        chapToBook.put(66, "Omni");
        chapToBook.put(67, "Words of Mormon");
        chapToBook.put(68, "Mosiah");
        chapToBook.put(97, "Alma");
        chapToBook.put(160, "Helaman");
        chapToBook.put(176, "3 Nephi");
        chapToBook.put(206, "4 Nephi");
        chapToBook.put(207, "Mormon");
        chapToBook.put(216, "Ether");
        chapToBook.put(231, "Moroni");
        chapToBook.put(241, "Invalid Chapter");


    // build the bookToRef map
        bookToRef.put("1 Nephi", 0);
        bookToRef.put("2 Nephi", 22);
        bookToRef.put("Jacob", 55);
        bookToRef.put("Enos", 63);
        bookToRef.put("Jarom", 64);
        bookToRef.put("Omni", 65);
        bookToRef.put("Words of Mormon", 66);
        bookToRef.put("Mosiah", 67);
        bookToRef.put("Alma", 96);
        bookToRef.put("Helaman", 159);
        bookToRef.put("3 Nephi", 175);
        bookToRef.put("4 Nephi", 205);
        bookToRef.put("Mormon", 206);
        bookToRef.put("Ether", 215);
        bookToRef.put("Moroni", 230);
        bookToRef.put("Invalid Chapter", null);}

    public int getBookToRef (String book, int chapter) {
        return chapter + bookToRef.get(book);
    }

    public String getChaptoBook(int chapter) {
        return chapToBook.floorEntry(chapter).getValue();
    }
}

