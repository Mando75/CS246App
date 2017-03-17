package com.group4.readingapp.scripturereadingapp;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.StringDef;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

public class CalcSched extends AsyncTask<Void, DailyReading, Void> {
    private static String TAG = "Calc Sched Async";
    private Schedule schedule;
    private Context context;
    protected RelativeLayout view;
    protected NavigableMap<Integer, String> chapToBook;
    protected Map<String, Integer> bookToRef;
    private DailyReading[] readings;
    private Date startingDate;
    private Date endingDate;
    private int numDays;
    private int chapsToRead;
    private int chapsPerDay;
    private DailyReading reading;

    public CalcSched(String filename, Context theContext, RelativeLayout layout) {
        schedule = new Schedule();
        schedule.loadFromFile(theContext, filename);
        context = theContext;
        view = layout;
        reading = new DailyReading();
        chapToBook = new TreeMap<Integer, String>();
        bookToRef = new TreeMap<String, Integer>();
        startingDate = schedule.getStartDate();
        endingDate = schedule.getEndDate();
        long milli = endingDate.getTime() - startingDate.getTime();
        numDays = (int) TimeUnit.DAYS.convert(milli, TimeUnit.MILLISECONDS);
        chapsToRead = schedule.getEndPos().get("chapId").getAsInt() - schedule.getCurrentPos().get("chapId").getAsInt();
        chapsPerDay = chapsToRead / numDays;
        readings = null;
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
    protected  void onPreExecute() {

    }

    @Override
    protected Void doInBackground(Void... params) {

        Log.d(TAG, "Launching doInBackground");
        List<String> info = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            info.add("");
        }
        int readPos = schedule.getCurrentPos().get("chapId").getAsInt();
        readings = new DailyReading[numDays];
        int startChap = schedule.getCurrentPos().get("chapId").getAsInt();
        int endChap = startChap + chapsPerDay;
        String startBook = getChaptoBook(startChap);
        String endBook = getChaptoBook(endChap);
        Log.d(TAG, schedule.getEndPos().get("book").getAsString());
        Log.d(TAG, schedule.getEndPos().get("chapter").getAsString());
        for (int i = 0; i < numDays; i++) {
            info.set(DailyReading.START_CHAP, Integer.toString(startChap));
            info.set(DailyReading.END_CHAP, Integer.toString((endChap)));
            info.set(DailyReading.START_BOOK, startBook);
            info.set(DailyReading.END_BOOK, endBook);
            info.set(DailyReading.START_CHAP_REF, "Chapter " + Integer.toString(getBookToRef(startBook, startChap)));
            info.set(DailyReading.END_CHAP_REF, "Chapter " + Integer.toString(getBookToRef(endBook, endChap)));
            Log.d(TAG, info.get(DailyReading.START_BOOK));
            Log.d(TAG, info.get(DailyReading.START_CHAP));
            Log.d(TAG, info.get(DailyReading.END_BOOK));
            Log.d(TAG, info.get(DailyReading.END_CHAP));
            readings[i] = new DailyReading(info);
            publishProgress(readings[i]);
            startChap = endChap;
            endChap = startChap + chapsPerDay;
            startBook = getChaptoBook(startChap);
            endBook = getChaptoBook(endChap);
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(DailyReading... read) {
        DailyReading dailyReading = read[0];
        Toast toast = Toast.makeText(context, "Published progress!", Toast.LENGTH_SHORT);
        toast.show();
        CardView card = new CardView(context);
        card.setCardElevation(15);
        card.setContentPadding(10,10,10,10);
        card.setId(View.generateViewId());
        view.addView(card);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)card.getLayoutParams();
        params.width = CardView.LayoutParams.MATCH_PARENT;
        params.height = CardView.LayoutParams.WRAP_CONTENT;
        params.setMargins(1,5,1,15);
        card.setLayoutParams(params);
        RelativeLayout innerLayout = new RelativeLayout(context);
        card.addView(innerLayout);
        FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams)innerLayout.getLayoutParams();
        params2.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params2.height = FrameLayout.LayoutParams.MATCH_PARENT;
        innerLayout.setLayoutParams(params2);

        TextView textView1 = new TextView(context);
        textView1.setText(dailyReading.getStartBook() + " " + dailyReading.getStartChapRef());
        textView1.setId(View.generateViewId());
        textView1.setTextSize(25);
        textView1.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        innerLayout.addView(textView1);
        RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams)textView1.getLayoutParams();
        params3.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        params3.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        textView1.setLayoutParams(params3);
    }

    @Override
    protected void onPostExecute(Void result) {

    }

    public String getChaptoBook(int chapter) {
        return chapToBook.floorEntry(chapter).getValue();
    }

    public int getBookToRef (String book, int chapter) {
        return chapter - bookToRef.get(book);
    }
}
