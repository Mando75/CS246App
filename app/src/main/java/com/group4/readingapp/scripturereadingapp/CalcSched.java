package com.group4.readingapp.scripturereadingapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by starw on 3/15/2017.
 * A background task that populates a view with daily reading cards
 * <p>A background task that takes a filename, a context and a layout.
 * It will load a schedule object from the filename given, and break that
 * file up into daily reading chunks. </p>
 * @author Bryan Muller
 * @version 0.5
 *
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
    private Date lastModifiedDate;
    private Date today;
    private int numDays;
    private int chapsToRead;
    private String filenameResave;
    private float chapsPerDay;
    private DailyReading reading;
    private DailyReading[] reads;
    private CardView[] card;
    private CheckBox[] checkBox;
    private Boolean finished;
    public Boolean restart = false;
    private DisplayMetrics displayMetrics;
    private ScheduleViewer theActivity;

    public CalcSched(){
        //default constructor for base calculations.
    }
    /**
     *  Default Constructor. The class MUST be passed the appropriate parameters.
     *  <p>This class uses a navigable and normal map to index chapter to book, and book to
     *  chapter relationships. Also contains an Array of DailyReadings </p>
     *  @see DailyReading
     * @param filename
     * @param theContext
     * @param layout
     */
    public CalcSched(String filename, Context theContext, RelativeLayout layout, DisplayMetrics displayMetrics, ScheduleViewer theActivity) {
        schedule = new Schedule();
        filenameResave = filename;
        this.displayMetrics = displayMetrics;
        schedule.loadFromFile(theContext, filename);
        finished = schedule.isFinished();
        Log.d(TAG, "CalcSched: " + finished);
        context = theContext;
        this.theActivity = theActivity;
        view = layout;
        reading = new DailyReading();
        chapToBook = new TreeMap<Integer, String>();
        bookToRef = new TreeMap<String, Integer>();
        startingDate = schedule.getStartDate();
        endingDate = schedule.getEndDate();
        lastModifiedDate = schedule.getLastModified();
        today = new Date();
        // find how many days are left to read.
        long milli = endingDate.getTime() - System.currentTimeMillis();
        numDays = (int) TimeUnit.DAYS.convert(milli, TimeUnit.MILLISECONDS);
        Log.d(TAG, "end: " + endingDate.getTime());
        Log.d(TAG, "today: " + System.currentTimeMillis());
        Log.d(TAG, "Number of Days: " + numDays);
        chapsToRead = schedule.getEndPos().get("chapId").getAsInt() - schedule.getCurrentPos().get("chapId").getAsInt();
        if(chapsToRead < 1){
            finished = true;
        }
        // find how many chapters must be read daily
        chapsPerDay = (float) chapsToRead / numDays;
        while(chapsPerDay < 1 && !finished){
            numDays -= 1;
            chapsPerDay = (float) chapsToRead / numDays;
        }

        // create an array of readings
        readings = new DailyReading[numDays];
    }

    /**
     * This function builds the maps needed for the referencing of books and chapters
     */
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
    }

    /**
     * runs through a loop to generate individual daily readings.
     * gathers all the information into a list, uses it to create a DailyReading object,
     * adds it to the array, and calls publish progress to update the ui when finished.
     * @param params
     * @return
     */
    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, "Launching doInBackground");
        List<String> info = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            info.add("");
        }

        /*
            In this section, we initialize the default values
        */
        float startChapF = schedule.getCurrentPos().get("chapId").getAsInt();
        int startChap = Math.round(startChapF);
        float endChapF = startChap + chapsPerDay;
        int endChap = Math.round(endChapF);
        String startBook = getChaptoBook(startChap);
        String endBook = getChaptoBook(endChap);
        Log.d(TAG, schedule.getEndPos().get("book").getAsString());
        Log.d(TAG, schedule.getEndPos().get("chapter").getAsString());

        /*
        Now loop through and use them to create a DailyReading object
         */

        for (int i = 0; i < numDays; i++) {
            info.set(DailyReading.START_CHAP, Integer.toString(startChap));
            info.set(DailyReading.END_CHAP, Integer.toString((endChap)));
            info.set(DailyReading.START_BOOK, startBook);
            info.set(DailyReading.END_BOOK, endBook);
            info.set(DailyReading.START_CHAP_REF, "Chapter " + Integer.toString(getBookToRef(startBook, startChap)));
            info.set(DailyReading.END_CHAP_REF, "Chapter " + Integer.toString(getBookToRef(endBook, endChap)));
            // debug
//            Log.d(TAG, info.get(DailyReading.START_BOOK));
//            Log.d(TAG, info.get(DailyReading.START_CHAP));
//            Log.d(TAG, info.get(DailyReading.END_BOOK));
//            Log.d(TAG, info.get(DailyReading.END_CHAP));
            // add a new daily reading to the array
            readings[i] = new DailyReading(info);

            // update chapter and book values for the next iteration.
            startChapF = endChapF;
            endChapF = startChapF + chapsPerDay;
            startChap = Math.round(startChapF);
            endChap = Math.round(endChapF);
            startBook = getChaptoBook(startChap);
            endBook = getChaptoBook(endChap);
        }
        publishProgress(readings);
        return null;
    }

    /**
     * Used to update the UI with cards containing reading information.
     * @param read
     */
    @Override
    protected void onProgressUpdate(DailyReading... read) {
        card = new CardView[read.length];
        checkBox = new CheckBox[read.length];
        reads = read;
        createCards(read);
    }
    public void createCards(DailyReading[] read){
        if(!finished)
            for(int i = 0; i < read.length; i++){
                final int k = i;
                DailyReading dailyReading = read[i];
                final DailyReading finalReading = read[i];
                card[i] = new CardView(context);
                card[i].setCardElevation(15);
                card[i].setContentPadding(10,10,10,10);
                card[i].setId(View.generateViewId());
                view.addView(card[i]);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)card[i].getLayoutParams();
                params.width = CardView.LayoutParams.MATCH_PARENT;
                params.height = Math.round(55 * displayMetrics.density);
                params.setMargins(1,5,1,15);
                card[i].setLayoutParams(params);
                final CardView finalCard = card[i];

                RelativeLayout innerLayout = new RelativeLayout(context);
                card[i].addView(innerLayout);
                FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams)innerLayout.getLayoutParams();
                params2.width = FrameLayout.LayoutParams.MATCH_PARENT;
                params2.height = FrameLayout.LayoutParams.MATCH_PARENT;
                if(i != 0)
                    params.addRule(RelativeLayout.BELOW,card[i-1].getId());
                else
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                innerLayout.setLayoutParams(params2);


                checkBox[i] = new CheckBox(context);
                innerLayout.addView(checkBox[i]);
                RelativeLayout.LayoutParams params4 = (RelativeLayout.LayoutParams)checkBox[i].getLayoutParams();
                params4.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                params4.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                params4.addRule(RelativeLayout.CENTER_VERTICAL);
                params4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params4.setMarginEnd(19);
                checkBox[i].setLayoutParams(params4);
                checkBox[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        if ( isChecked )
                        {
                            Log.d(TAG, "onCheckedChanged: " + finalReading.getEndBook() + " " + finalReading.getEndChapRef());
                            removeCard(finalReading);
                            theActivity.completed(finished);
                        }

                    }
                });

                TextView textView1 = new TextView(context);
                textView1.setText("Start at " + dailyReading.getStartBook() + " " + dailyReading.getStartChapRef().replace("Chapter ", "") + " and read " + Math.round(chapsPerDay) + " chapters.");
                textView1.setId(View.generateViewId());
                textView1.setTextSize(20);
                textView1.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
                innerLayout.addView(textView1);
                RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams)textView1.getLayoutParams();
                params3.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                params3.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                params3.addRule(RelativeLayout.LEFT_OF, checkBox[i].getId());
                params3.addRule(RelativeLayout.CENTER_VERTICAL);
                textView1.setLayoutParams(params3);
            }
            save();
    }
    public void removeCard(DailyReading read){
        schedule.buildCurrent(read.getEndBook(), read.getEndChapRef().replace("Chapter ", ""), read.getEndChap());
        if((schedule.getEndPos().get("chapId").getAsInt() - schedule.getCurrentPos().get("chapId").getAsInt()) < 1){
            finished = true;
        }
        schedule.setFinished(finished);
        schedule.setLastModified(today);
        schedule.buildJson();
        schedule.saveToFile(context, filenameResave);
        Log.d(TAG, "onCheckedChanged: Item Removed");

    }
    public void save(){
        if((schedule.getEndPos().get("chapId").getAsInt() - schedule.getCurrentPos().get("chapId").getAsInt()) < 1){
            finished = true;
        }
        schedule.setFinished(finished);
        schedule.buildJson();
        schedule.saveToFile(context, filenameResave);
        Log.d(TAG, "save: " + schedule.isFinished());
    }
    @Override
    protected void onPostExecute(Void result) {

    }

    /**
     * receives a chapter index, and returns the name of the book
     * that chapter is in.
     * Example: chapter = 150;
     * Result: Alma
     * @param chapter
     * @return
     */
    public String getChaptoBook(int chapter) {
        return chapToBook.floorEntry(chapter).getValue();
    }

    /**
     * receives the name of a book and the chapter index, returns what relative chapter
     * the id would be.
     * Example: book = Alma chapter = 150
     * Result: 54
     * @param book
     * @param chapter
     * @return
     */
    public int getBookToRef (String book, int chapter) {
        return chapter - bookToRef.get(book);
    }
}
