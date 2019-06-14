package com.itxiaox.xnet.retrofit;

import android.content.Context;

import com.itxiaox.xnet.base.IRequestCallback;
import com.itxiaox.xnet.base.IRequestManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitManager implements IRequestManager{
    private final String BASE_URL = "";
    WebService webService;

    public void init(){



    }

    @Override
    public void init(Context context) {
        OkHttpClient  okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        webService = retrofit.create(WebService.class);
    }

    @Override
    public void get(String url, IRequestCallback requestCallback) {
//        Call<T> call webService.getUser();
    }

    @Override
    public void post(String url, String requestBodyJson, IRequestCallback requestCallback) {

    }

    @Override
    public void put(String url, String requestBobyJson, IRequestCallback requestCallback) {

    }

    @Override
    public void delete(String url, String requestBodyJson, IRequestCallback requestCallback) {

    }
}
