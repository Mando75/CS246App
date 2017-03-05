package com.group4.readingapp.scripturereadingapp;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Bryan on 2/24/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ScheduleSaveTest
{

    @Mock
    Context context;
    @Test
    public void saveTest() throws Exception {

        context.getApplicationContext();
        Schedule testSchedule = new Schedule();
        testSchedule.buildStart("1 Nephi", "Chapter 1", "1");
        testSchedule.buildEnd("Moroni", "Chapter 10", "35");
        testSchedule.buildCurrent("Alma", "Chapter 5", "1");
        testSchedule.saveToFile(context, "testFile.json");

    }
}
