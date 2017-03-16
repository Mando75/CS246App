package com.group4.readingapp.scripturereadingapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by starw on 3/15/2017.
 * @author Bryan Muller
 * @version 0.5
 */

public class CalcSched extends AsyncTask<Void, Void, Void> {

    private Schedule schedule;
    private Context context;
    protected ScrollView scrollView;
    protected NavigableMap<Integer, String> chapToBook;
    protected Map<String, Integer> bookToRef;
    private DailyReading[] readings;
    private Date startingDate;
    private Date endingDate;
    private int numDays;
    private int chapsToRead;
    private int chapsPerDay;

    public CalcSched(String filename, Context theContext, ScrollView scroll) {
        schedule = new Schedule();
        schedule.loadFromFile(theContext, filename);
        context = theContext;
        scrollView = scroll;
//        chapToBook = new TreeMap<Integer, String>();
        bookToRef = new TreeMap<String, Integer>();
        startingDate = schedule.getStartDate();
        endingDate = schedule.getEndDate();
        long milli = endingDate.getTime() - startingDate.getTime();
        numDays = (int) TimeUnit.DAYS.convert(milli, TimeUnit.MILLISECONDS);
        chapsToRead = schedule.getEndPos().get("chapId").getAsInt() - schedule.getCurrentPos().get("chapId").getAsInt();
        chapsPerDay = chapsToRead / numDays;
        readings = null;
    }

    @Override
    protected  void onPreExecute() {
        // build the chapToBook map
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
        bookToRef.put("Invalid Chapter", null);

        // create an array of readings
        readings = new DailyReading[numDays];

    }

    @Override
    protected Void doInBackground(Void... params) {

        List<String> info = new ArrayList<>();
        info.add(DailyReading.START_CHAP, schedule.getCurrentPos().get("chapId").getAsString());
        info.add(DailyReading.START_BOOK, chapToBook.floorEntry(schedule.getCurrentPos().get("chapId").getAsInt()).getValue());
        info.add(DailyReading.END_CHAP, Integer.toString(schedule.getCurrentPos().get("chapId").getAsInt() + chapsPerDay));
        info.add(DailyReading.END_BOOK, chapToBook.floorEntry(schedule.getCurrentPos().get("chapId").getAsInt() + chapsPerDay).getValue());
        info.add(DailyReading.START_CHAP_REF, "Chapter " + Integer.toString(schedule.getCurrentPos().get("chapId").getAsInt() - bookToRef.get(info.get(DailyReading.START_BOOK))));
        info.add(DailyReading.END_CHAP_REF, "Chapter " + Integer.toString((schedule.getCurrentPos().get("chapId").getAsInt() + chapsPerDay) - bookToRef.get(info.get(DailyReading.END_BOOK))));
        DailyReading reading = new DailyReading(info);
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... params) {

    }

    @Override
    protected void onPostExecute(Void result) {

    }
}
