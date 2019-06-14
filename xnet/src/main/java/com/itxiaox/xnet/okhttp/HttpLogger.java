package com.itxiaox.xnet.okhttp;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpLogger implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        System.out.println("HttpLogger:"+message);
    }
}