package com.itxiaox.http.okhttp;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

public class OkhttpLoggerInterceptor implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        System.out.println("OkhttpLoggerInterceptor:"+message);
    }
}