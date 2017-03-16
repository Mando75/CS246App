package com.group4.readingapp.scripturereadingapp;

import java.util.List;

/**
 * Created by starw on 3/15/2017.
 */

public class DailyReading {

    private int startChap;
    private int endChap;
    private String startBook;
    private String endBook;
    private String startChapRef;
    private String endChapRef;
    private int endPos;

    protected static final int START_CHAP = 0;
    protected static final int END_CHAP = 1;
    protected static final int START_BOOK = 2;
    protected static final int END_BOOK = 3;
    protected static final int START_CHAP_REF = 4;
    protected static final int END_CHAP_REF = 5;
    protected static final int END_POS = 6;



    public DailyReading(List<String> info) {
        startChap = Integer.parseInt(info.get(START_CHAP));
        endChap = Integer.parseInt(info.get(END_CHAP));
        startBook = info.get(START_BOOK);
        endBook = info.get(END_BOOK);
        startChapRef = info.get(START_CHAP_REF);
        endChapRef = info.get(END_CHAP_REF);
        endPos = Integer.parseInt(info.get(END_POS));
    }
    public int getEndChap() {return endChap;}
    public int getEndPos() {return endPos;}
    public int getStartChap() {return startChap;}
    public String getEndBook() {return endBook;}
    public String getEndChapRef() {return endChapRef;}
    public String getStartBook() {return startBook;}
    public String getStartChapRef() {return startChapRef;}
    public void setStartBook(String startBook){this.startBook = startBook;}
    public void setStartChapRef(String startChapRef){this.startChapRef = startChapRef;}
    public void setEndPos(int endPos){this.endPos = endPos;}
    public void setEndBook(String endBook) {this.endBook = endBook;}
    public void setEndChap(int endChap) {this.endChap = endChap;}
    public void setEndChapRef(String endChapRef) {this.endChapRef = endChapRef;}
    public void setStartChap(int startChap) {this.startChap = startChap;}

}
