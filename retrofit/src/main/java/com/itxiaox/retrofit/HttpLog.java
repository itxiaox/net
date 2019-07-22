package com.itxiaox.retrofit;


import com.itxiaox.retrofit.Utils.JsonUtils;

import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;

import static okhttp3.internal.platform.Platform.INFO;

public class HttpLog implements HttpLoggingInterceptor.Logger {
    private static final String TAG = "HttpLog";
    private StringBuilder mMessage = new StringBuilder();


    @Override
    public void log(String message) {
        // 请求或者响应开始
//        if (message.startsWith("--> POST")) {
//            mMessage.setLength(0);
//        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
//        if ((message.startsWith("{") && message.endsWith("}"))
//                || (message.startsWith("[") && message.endsWith("]"))) {
//
//        }

        message = JsonUtils.formatJson(JsonUtils.decodeUnicode(message));
//        mMessage.append(message.concat("\n"));
        // 响应结束，打印整条日志
//        if (message.startsWith("<-- END HTTP")) {
//            System.out.println(TAG + ":" + mMessage.toString());
//        }
        Platform.get().log(INFO, message, null);
    }
}