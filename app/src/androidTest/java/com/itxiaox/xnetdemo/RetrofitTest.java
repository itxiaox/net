package com.itxiaox.xnetdemo;


import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.itxiaox.retrofit.HeadersInterceptor;
import com.itxiaox.retrofit.HttpConfig;
import com.itxiaox.retrofit.HttpLog;
import com.itxiaox.retrofit.HttpManager;
import com.itxiaox.retrofit.Utils.JsonUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RunWith(AndroidJUnit4.class)
public class RetrofitTest {

    private static final String TAG = "RetrofitTest";
    String baseUrl ;
    private WXAPIService wxapiService;

    @Before
    public void init(){
//         baseUrl = "https://itxiaox.github.io";
         baseUrl = "https://www.wanandroid.com";


        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));


         //默认简单调用方式
        HttpManager.init(baseUrl,true);


        //添加日志拦截器
//        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                Log.d(TAG, "log:"+message);
//            }
//        });
//        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        //添加通用的请求参数，放到heaader中
////        HeadersInterceptor headersInterceptor = new HeadersInterceptor.Builder()
////                .addHeaderParam("Content-Type","")
////                .addHeaderParam("Accept","application/json")
////                .build();
//       HttpConfig httpConfig = new HttpConfig.Builder()
//               .baseUrl(baseUrl)
////               .addInterceptor(headersInterceptor)
//               .addInterceptor(logInterceptor)
//               .addConverterFactory(GsonConverterFactory.create())
//               .build();
//       HttpManager.init(httpConfig);



    }
    @Test
    public void testRetrofit(){
        wxapiService = HttpManager.create(WXAPIService.class);
        wxapiService.getWXArticle().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                  String result = response.body().string();
                  Logger.json(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("fail:"+t);
            }
        });
    }

}
