package com.itxiaox.xnet.retrofit;

import android.content.Context;

import com.itxiaox.xnet.base.IRequestCallback;
import com.itxiaox.xnet.base.IRequestManager;
import com.itxiaox.xnet.base.RequestBody;

import java.util.Map;
import java.util.Set;

public class RetrofitManager implements IRequestManager{
    HttpClient.Builder builder;
    HttpClient httpClient;
    private static RetrofitManager retrofitManager;
    @Override
    public void init(Context context,String baseUrl) {
        builder =  new HttpClient.Builder().baseUrl(baseUrl);
//        OkHttpClient  okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(10, TimeUnit.SECONDS)
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(okHttpClient)
////                .addConverterFactory(GsonConverterFactory.create())
////                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//        webService = retrofit.create(WebService.class);
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
    public <T> void get(String url, RequestBody paramBody, IRequestCallback<T> requestCallback) {

        builder.url(url);//这里的url是除 baseUrl之外
        builder.tag(url);
        if(paramBody!=null&&paramBody.getParams().size()>0){
            builder.params(paramBody.getParams());
        }
        httpClient = builder.build();

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
    public <T> void post(String url, RequestBody paramBody, IRequestCallback<T> requestCallback) {

        builder.url(url);//这里的url是除 baseUrl之外
        if(paramBody!=null&&paramBody.getParams().size()>0){
            builder.params(paramBody.getParams());
        }
        builder.tag(url);
        httpClient = builder.build();
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


    @Override
    public void put(String url, String requestBobyJson, IRequestCallback requestCallback) {

    }

    @Override
    public void delete(String url, String requestBodyJson, IRequestCallback requestCallback) {

    }
}
