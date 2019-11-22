package com.itxiaox.download;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.Operation;
import androidx.work.WorkInfo;

import java.io.File;

import static android.os.Environment.getDataDirectory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void download(View view) {

        String baseUrl = "https://yingling-s3test.s3.cn-north-1.amazonaws.com.cn";
//        String url = baseUrl+"/Models/models5.1.zip";
        String url = "/Models/models5.1.zip";
        String fileName = url.substring(url.lastIndexOf('/')+1);
        String path = getDir("models",
                Context.MODE_PRIVATE).getAbsolutePath()+ File.separator+fileName;
        Log.d(TAG, "testDownload: path="+path);

        LiveData<WorkInfo> liveData = DownloadManager.download(baseUrl,url,path);
        liveData.observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
               WorkInfo.State  state =  workInfo.getState();
                Log.d(TAG, "onChanged: "+state);
                Data data = workInfo.getOutputData();
                Log.d(TAG, "onChanged: result="+data.getString("result"));

            }
        });
    }
}
