package com.group4.readingapp.scripturereadingapp;

import org.json.JSONObject;

import java.io.File;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ctrki on 2/24/2017.
 */

public class Schedule {
    JSONObject mainSchedule = new JSONObject();
    String scheduleName;
    JSONObject startPos;
    JSONObject endPos;
    JSONObject currentPos;
    String book;
    String chapter;
    String verse;
    Integer frequency;
    Date startDate;
    Date endDate;

    public void setBook(String book) {
        this.book = book;
    }

    public void setMainSchedule(JSONObject mainSchedule) {
        this.mainSchedule = mainSchedule;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public void setCurrentPos(JSONObject currentPos) {
        this.currentPos = currentPos;
    }

    public void setEndPos(JSONObject endPos) {
        this.endPos = endPos;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public void setStartPos(JSONObject startPos) {
        this.startPos = startPos;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public Date getStartDate() {
        return startDate;
    }

    public JSONObject getCurrentPos() {
        return currentPos;
    }

    public Date getEndDate() {
        return endDate;
    }

    public JSONObject getEndPos() {
        return endPos;
    }

    public JSONObject getMainSchedule() {
        return mainSchedule;
    }

    public JSONObject getStartPos() {
        return startPos;
    }

    public String getBook() {
        return book;
    }

    public String getChapter() {
        return chapter;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public String getVerse() {
        return verse;
    }


    public void generateJSON(){

    }
    public void loadFromFile(String filename){

    }
    public void saveToFile(String filename){

    }
    public void displayScheduleConsole(){

    }
}