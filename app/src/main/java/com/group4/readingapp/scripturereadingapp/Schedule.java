package com.group4.readingapp.scripturereadingapp;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by loren on 2/24/2017.
 */

public class Schedule {
    private JsonObject mainSchedule;
    private JsonObject startPos;
    private JsonObject endPos;
    private JsonObject currentPos;
    private Integer remindHour;
    private Date startDate;
    private Date endDate;

    Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    //setters
    public void setMainSchedule(JsonObject mainSchedule) {this.mainSchedule = mainSchedule;}
    public void setCurrentPos(JsonObject currentPos) {this.currentPos = currentPos;}
    public void setEndPos(JsonObject endPos) {this.endPos = endPos;}
    public void setStartPos(JsonObject startPos) {this.startPos = startPos;}
    public void setEndDate(Date endDate) {this.endDate = endDate;}
    public void setFrequency(Integer frequency) {this.remindHour = frequency;}
    public void setStartDate(Date startDate) {this.startDate = startDate;}
    //getters
    public Date getStartDate() {return startDate;}
    public JsonObject getCurrentPos() {return currentPos;}
    public Date getEndDate() {return endDate;}
    public JsonObject getEndPos() {return endPos;}
    public JsonObject getMainSchedule() {return mainSchedule;}
    public JsonObject getStartPos() {return startPos;}
    public Integer getFrequency() {return remindHour;}


    public void buildJson(){
        mainSchedule = new JsonObject();
        mainSchedule.add("startPos", new Gson().toJsonTree(startPos));
        mainSchedule.add("endPos", new Gson().toJsonTree(endPos));
        mainSchedule.add("currentPos", new Gson().toJsonTree(currentPos));
        String dateStart = formatter.format(startDate);
        mainSchedule.addProperty("startDate", dateStart);
        String dateEnd = formatter.format(endDate);
        mainSchedule.addProperty("frequency", remindHour);
    }
    public void buildStart(String book, String chapter, String verse){
        startPos = null;
        startPos = new JsonObject();
        startPos.addProperty("book", book);
        startPos.addProperty("chapter", chapter);
        startPos.addProperty("verse", verse);
    }
    public void buildEnd(String book, String chapter, String verse){
        endPos = null;
        endPos = new JsonObject();
        endPos.addProperty("book", book);
        endPos.addProperty("chapter", chapter);
        endPos.addProperty("verse", verse);
    }
    public void buildCurrent(String book, String chapter, String verse){
        currentPos = null;
        currentPos = new JsonObject();
        currentPos.addProperty("book", book);
        currentPos.addProperty("chapter", chapter);
        currentPos.addProperty("verse", verse);
    }
    public void loadFromFile(Context context, String filename){
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Loading File", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Loading File", "Can not read file: " + e.toString());
        }
        mainSchedule = null;
        mainSchedule = new Gson().fromJson(ret, JsonObject.class);

    }
    public void saveToFile(Context context, String filename){
        try {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
        outputStreamWriter.write(mainSchedule.toString());
        outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void displayScheduleConsole(){

    }
}