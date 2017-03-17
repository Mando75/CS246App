package com.group4.readingapp.scripturereadingapp;

import android.app.AlarmManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        File[] files = getFilesDir().listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name)
            {
                return (name.endsWith(".json"));
            }
        });

        populateSchedules(files);

    }
    @Override
    public void onResume()
    {
        super.onResume();
        File[] files = getFilesDir().listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name)
            {
                return (name.endsWith(".json"));
            }
        });
        populateSchedules(files);
    }
    public void createSchedule(View view) {
        Intent intent = new Intent(MainActivity.this, ScheduleCreator.class);
        startActivity(intent);

    }
    public void populateSchedules(File[] files){

        RelativeLayout layout = (RelativeLayout) findViewById((R.id.cards));

        layout.removeAllViews();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) MainActivity.this.getSystemService(Context.WINDOW_SERVICE);
        int m = Math.round(5 * displayMetrics.density);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        CardView[] cardViews = new CardView[files.length];
        Schedule[] schedules = new Schedule[files.length];
        for(int i = 0; i < files.length; i++) {
            Log.d("File", files[i].getName());
            final String filename = files[i].getName();
            schedules[i] = new Schedule();
            schedules[i].loadFromFile(getApplicationContext(), files[i].getName());

            cardViews[i] = new CardView(this);
            cardViews[i].setCardElevation(15);
            cardViews[i].setContentPadding(10,10,10,10);
            cardViews[i].setId(View.generateViewId());
            cardViews[i].setClickable(true);
            cardViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ScheduleViewer.class);
                    intent.putExtra("filename", filename);
                    startActivity(intent);
                }
            });
            layout.addView(cardViews[i]);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)cardViews[i].getLayoutParams();
            params.width = CardView.LayoutParams.MATCH_PARENT;
            params.height = Math.round(90 * displayMetrics.density);
            params.setMargins(1,5,1,15);
            if(i != 0)
                params.addRule(RelativeLayout.BELOW,cardViews[i-1].getId());
            else
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            cardViews[i].setLayoutParams(params);

            RelativeLayout innerLayout = new RelativeLayout(this);
            cardViews[i].addView(innerLayout);
            FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams)innerLayout.getLayoutParams();
            params2.width = FrameLayout.LayoutParams.MATCH_PARENT;
            params2.height = FrameLayout.LayoutParams.MATCH_PARENT;
            innerLayout.setLayoutParams(params2);

            TextView textView1 = new TextView(this);
            textView1.setText(files[i].getName().replace(".json", "").replaceAll("[_]", " "));
            textView1.setId(View.generateViewId());
            textView1.setTextSize(25);
            textView1.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
            innerLayout.addView(textView1);
            RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams)textView1.getLayoutParams();
            params3.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
            params3.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            params3.setMargins(m,m,m,m);
            textView1.setLayoutParams(params3);

            TextView textView2 = new TextView(this);
            textView2.setText(schedules[i].getStartPos().get("book").getAsString() + " " +
                    schedules[i].getStartPos().get("chapter").getAsString() + " - " +
                    schedules[i].getEndPos().get("book").getAsString() + " " +
                    schedules[i].getEndPos().get("chapter").getAsString());
            textView2.setId(View.generateViewId());
            textView2.setTextSize(18);
            textView2.setTypeface(Typeface.create("sans-serif", Typeface.ITALIC));
            innerLayout.addView(textView2);
            RelativeLayout.LayoutParams params4 = (RelativeLayout.LayoutParams)textView2.getLayoutParams();
            params4.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
            params4.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            params4.addRule(RelativeLayout.BELOW, textView1.getId());
            params4.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params4.setMargins(5,5,5,5);
            textView2.setLayoutParams(params4);

            CheckBox checkBox = new CheckBox(this);
            innerLayout.addView(checkBox);
            RelativeLayout.LayoutParams params5 = (RelativeLayout.LayoutParams)checkBox.getLayoutParams();
            params5.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
            params5.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            params5.addRule(RelativeLayout.CENTER_VERTICAL);
            params5.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params5.setMarginEnd(19);
            checkBox.setLayoutParams(params5);
        }
    }

}
