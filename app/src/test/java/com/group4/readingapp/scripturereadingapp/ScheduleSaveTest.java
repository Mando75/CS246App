package com.group4.readingapp.scripturereadingapp;

import android.content.Context;
import android.test.mock.MockContext;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Array;

/**
 * Created by Bryan on 2/24/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ScheduleSaveTest
{

    @Mock
    MockContext context;
    @Test
    public void saveTest() throws Exception {

        context.getApplicationContext();
        String[] files = context.fileList();
        for (int i = 0; i < files.length; i++) {
            Log.d("tag", files[i]);
        }
//        Schedule testSchedule = new Schedule();
//        testSchedule.buildStart("1 Nephi", "Chapter 1", "1");
//        testSchedule.buildEnd("Moroni", "Chapter 10", "35");
//        testSchedule.buildCurrent("Alma", "Chapter 5", "1");
//        testSchedule.saveToFile(context, "testFile.json");

    }
}
