package com.itxiaox.http.retrofit;

import android.content.Context;

import com.itxiaox.http.base.HttpCallback;
import com.itxiaox.http.base.HttpConfig;
import com.itxiaox.http.base.HttpLogger;
import com.itxiaox.http.base.HttpManager;
import com.itxiaox.http.base.HttpParams;

public class RetrofitManager implements HttpManager {
    private HttpClient.Builder builder;
    private HttpClient httpClient;
    private static RetrofitManager retrofitManager;
    private static RetrofitConfig config;
    @Override
    public void init(Context context,String baseUrl) {
        HttpLogger.d("init RetrofitManager");
        config = RetrofitConfig.createDefault(baseUrl, HttpLogger.logger());
        builder =  new HttpClient
                .Builder();
    }

    @Override
    public void init(Context context, HttpConfig httpConfig) {
        HttpLogger.d("init RetrofitManager");
        config = (RetrofitConfig) httpConfig;
        builder =  new HttpClient.Builder();
    }

    public static RetrofitManager getInstance(){
        if(retrofitManager==null){
            synchronized (RetrofitManager.class){
                if (retrofitManager==null){
                    retrofitManager = new RetrofitManager();
                }
            }
        }
       return retrofitManager;
    }


    @Override
    public <T> void get(String url, HttpParams paramBody, HttpCallback<T> requestCallback) {

        builder.url(url);//这里的url是除 baseUrl之外
        builder.tag(url);
        if(paramBody!=null&&paramBody.getParams().size()>0){
            builder.params(paramBody.getParams());
        }
        httpClient = builder.build(config);

        httpClient.get(new OnResultListener<T> (){
            @Override
            public void onSuccess(T result) {
                super.onSuccess(result);
                requestCallback.onSuccess(result);
            }

            @Override
            public void onFailure(int errcode, String message) {
                super.onFailure(errcode, message);
                requestCallback.onFailure(errcode,message);
            }
        });
    }

    @Override
    public <T> void post(String url, HttpParams paramBody, HttpCallback<T> requestCallback) {

        builder.url(url);//这里的url是除 baseUrl之外
        if(paramBody!=null&&paramBody.getParams().size()>0){
            builder.params(paramBody.getParams());
        }
        builder.tag(url);
        httpClient = builder.build(config);
        httpClient.post(new OnResultListener<T> (){
            @Override
            public void onSuccess(T result) {
                super.onSuccess(result);
                requestCallback.onSuccess(result);
            }

            @Override
            public void onFailure(int errcode, String message) {
                super.onFailure(errcode, message);
                requestCallback.onFailure(errcode,message);
            }
        });
    }
}
