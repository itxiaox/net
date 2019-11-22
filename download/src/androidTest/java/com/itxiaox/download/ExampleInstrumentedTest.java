package com.itxiaox.download;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.work.Configuration;
import androidx.work.Data;
import androidx.work.WorkInfo;
import androidx.work.testing.SynchronousExecutor;
import androidx.work.testing.WorkManagerTestInitHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumentedTest";
    Context appContext;
    @Before
    public void setup() {
        // Context of the app under test.
         appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Configuration config = new Configuration.Builder()
                // Set log level to Log.DEBUG to
                // make it easier to see why tests failed
                .setMinimumLoggingLevel(Log.DEBUG)
                // Use a SynchronousExecutor to make it easier to write tests
                .setExecutor(new SynchronousExecutor())
                .build();

        WorkManagerTestInitHelper.initializeTestWorkManager(appContext,config);
    }




    @Test
    public void testDownload(){
        String baseUrl = "http://imtt.dd.qq.com";
        String url = "/16891/89E1C87A75EB3E1221F2CDE47A60824A.apk?fsname=com.snda.wifilocating_4.2.62_3192.apk&csr=1bbd";
        String path =appContext.getDir("apk",Context.MODE_PRIVATE).getAbsolutePath()+ File.separator+"net"+File.separator+"test.apk";
        Log.d(TAG, "testDownload: path="+path);
//      LiveData<WorkInfo>
        LiveData<WorkInfo> workInfoLiveData =  DownloadManager.download(baseUrl,url,path);

        WorkInfo workInfo = workInfoLiveData.getValue();
         Data outputData = workInfo.getOutputData();

         //Asert
        assertThat(workInfo.getState(),is(WorkInfo.State.SUCCEEDED));
//        assertThat(outputData, is(input));
    }
}
