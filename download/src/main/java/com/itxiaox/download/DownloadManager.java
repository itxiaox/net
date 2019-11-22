package com.itxiaox.download;

import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class DownloadManager {



    public static LiveData<WorkInfo> download(String baseUrl,String url,String savePath){

        Data requestData = new Data.Builder()
                .putString(Constants.KEY_BASE_URL, baseUrl)
                .putString(Constants.KEY_URL,url)
                .putString(Constants.KEY_SAVE_FILE,savePath)
                .build();
        OneTimeWorkRequest downloadRequest = new OneTimeWorkRequest.Builder(DownloadWorker.class)
                .setInputData(requestData)
                .addTag(url)
                .build();
        WorkManager.getInstance()
                .enqueue(downloadRequest);
         return WorkManager.getInstance().getWorkInfoByIdLiveData(downloadRequest.getId());
    }

    public void stopDownload(String tag){
        WorkManager.getInstance().cancelAllWorkByTag(tag);
    }

}
