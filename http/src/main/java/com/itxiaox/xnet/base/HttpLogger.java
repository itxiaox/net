package com.itxiaox.xnet.base;

import android.util.Log;

import com.itxiaox.xnet.utils.JsonUtils;

import java.util.Locale;


public class HttpLogger {

    private static boolean logger = true;

    public static boolean  logger(){
        return logger;
    }
    private static final String TAG = "HttpLogger";

    public static void d(String msg){
        Log.d(TAG, msg);
    }

    public static void i(String msg){
        Log.i(TAG,msg);
    }

    public static void e(String msg){
        Log.e(TAG,msg);
    }

    public static void format(String format,Object... args){
        Log.d(TAG,String.format(Locale.getDefault(),format, args));
    }

    public static void json(String json){
        Log.d(TAG, JsonUtils.formatJson(json));
    }
}
