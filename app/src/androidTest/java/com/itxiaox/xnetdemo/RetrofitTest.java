package com.itxiaox.xnetdemo;


import android.support.test.runner.AndroidJUnit4;

import com.itxiaox.retrofit.HttpConfig;
import com.itxiaox.retrofit.HttpLog;
import com.itxiaox.retrofit.HttpManager;
import com.itxiaox.retrofit.Utils.JsonUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(AndroidJUnit4.class)
public class RetrofitTest {

    String baseUrl ;
    private WXAPIService wxapiService;

    public void init(){
//         baseUrl = "https://itxiaox.github.io";
         baseUrl = "https://www.wanandroid.com";


         //默认简单调用方式
        HttpManager.init(baseUrl,true);
//        RetrofitManager.init(baseUrl);
//        wxapiService = RetrofitManager.getWebService(WXAPIService.class);
//
//        //使用默认的OkHttpClient配置，根据实际情况配置Retrofit.Builder
//        OkHttpClient client = RetrofitManager.defaultClient().build();
//        wxapiService = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create()) .build()
//                .create(WXAPIService.class);

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

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLog());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
       HttpConfig httpConfig = new HttpConfig.Builder()
               .baseUrl(baseUrl).addInterceptor(logInterceptor)
               .addConverterFactory(GsonConverterFactory.create())
               .build();
       HttpManager.init(httpConfig);



    }
    @Test
    public void testRetrofit(){
        init();
        wxapiService = HttpManager.create(WXAPIService.class);
        wxapiService.getWXArticle().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println("success:"+ JsonUtils.formatJson(response.body().string()));
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

//    @Test
//    public void testRequest(){
//        IRequestManager iRequestManager = RequestFactory. getRequestManager();
//        iRequestManager.init(InstrumentationRegistry.getTargetContext());
//
//        String url = "https://wanandroid.com/wxarticle/chapters/json";
//        iRequestManager.get(url, new IRequestCallback() {
//            @Override
//            public void onSuccess(String response) {
//                System.out.println("response="+response);
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                System.out.println("throwable="+throwable);
//            }
//        });
//    }
}
