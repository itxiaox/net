package com.itxiaox.download.core;

public interface DownloadListener {
    void onStart();//从下载开始

    void onProgress(int progress);//下载进度

    void onFinish(String path);//下载完成

    void onFail(String errorInfo);//下载失败
}
