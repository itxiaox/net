package com.itxiaox.xnetdemo;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.itxiaox.retrofit.HttpLog;
import com.itxiaox.retrofit.RetrofitManager;
import com.itxiaox.retrofit.RetrofitServiceManager;
import com.itxiaox.xnet.base.IRequestCallback;
import com.itxiaox.xnet.base.IRequestManager;
import com.itxiaox.xnet.base.RequestFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(AndroidJUnit4.class)
public class RetrofitTest {

    String baseUrl ;
    private WXAPIService wxapiService;

    public void init(){
//         baseUrl = "https://itxiaox.github.io";
         baseUrl = "https://www.wanandroid.com";

         //默认简单调用方式
        RetrofitManager.init(baseUrl);
        wxapiService = RetrofitManager.getWebService(WXAPIService.class);

        //使用默认的OkHttpClient配置，根据实际情况配置Retrofit.Builder
        OkHttpClient client = RetrofitManager.defaultClient().build();
        wxapiService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) .build()
                .create(WXAPIService.class);

//        wxapiService =  RetrofitManager.getBuilder()
//                .baseUrl(baseUrl)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(WXAPIService.class);

        //全部灵活配置
//        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
//        clientBuilder.connectTimeout(30, TimeUnit.SECONDS);
//        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLog());
//        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        clientBuilder.addNetworkInterceptor(logInterceptor);
//        wxapiService = new Retrofit.Builder().baseUrl(baseUrl)
//                .client(clientBuilder.build())
////                .addConverterFactory(GsonConverterFactory.create())
//                .build().create(WXAPIService.class);

    }
    @Test
    public void testRetrofit(){
        init();
        wxapiService.getWXArticle().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(" "+response+" ;call="+call.request().url());
                try {
                    System.out.println("success:"+response.body().string());
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

    @Test
    public void testRequest(){
        IRequestManager iRequestManager = RequestFactory. getRequestManager();
        iRequestManager.init(InstrumentationRegistry.getTargetContext());

        String url = "https://wanandroid.com/wxarticle/chapters/json";
        iRequestManager.get(url, new IRequestCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response="+response);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("throwable="+throwable);
            }
        });
    }
}
