package com.group4.readingapp.scripturereadingapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ScrollView;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by starw on 3/15/2017.
 * @author Bryan Muller
 * @version 0.5
 */

public class CalcSched extends AsyncTask<Void, Void, Void> {

    Schedule schedule;
    Context context;
    ScrollView scrollView;
    NavigableMap<Integer, String> chapToBook;
    Map<String, Integer> bookToRef;

    public CalcSched(Schedule sched, Context theContext, ScrollView scroll) {
        schedule = sched;
        context = theContext;
        scrollView = scroll;
        chapToBook = new TreeMap<Integer, String>();
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

        bookToRef = new TreeMap<String, Integer>();
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
    }

    @Override
    protected  void onPreExecute() {

    }

    @Override
    protected Void doInBackground(Void... params) {

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... params) {

    }

    @Override
    protected void onPostExecute(Void result) {

    }
}
