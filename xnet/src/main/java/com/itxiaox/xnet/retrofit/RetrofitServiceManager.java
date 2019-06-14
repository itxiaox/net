package com.itxiaox.xnet.retrofit;

import android.annotation.SuppressLint;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceManager {
    private   String baseUrl ;
    private   int timeout;//超时时间
    private   int readeTimeOut;
    private Retrofit mRetrofit;


    public static class Builder{
         String baseUrl = "https://api.weixin.qq.com/";
         int timeout = 5;
         int readeTimeOut = 10;
         OkHttpClient.Builder okHttpClientBuilder;

        public Builder() {

        }
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }
        public Builder timeOut(int timeout) {
            this.timeout = timeout;
            return this;
        }
        public Builder readeTimeOut(int readeTimeOut) {
            this.readeTimeOut = readeTimeOut;
            return this;
        }

        public Builder client(OkHttpClient.Builder okHttpClient){
            this.okHttpClientBuilder = okHttpClient;
            return this;
        }

        public RetrofitServiceManager builder(){
            return  new RetrofitServiceManager(this);
        }
    }

    private  OkHttpClient.Builder builder;
    private RetrofitServiceManager(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.timeout = builder.timeout;
        this.readeTimeOut = builder.readeTimeOut;
        // 创建Retrofit
        getRetrofit();
    }

    public Retrofit getRetrofit(){
        if (mRetrofit==null){
            mRetrofit = new Retrofit.Builder()
                    .client(getDefaultClient())
//                  .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
        }
        return mRetrofit;
    }

    public void setRetrofit(Retrofit retrofit ){
        this.mRetrofit = retrofit;
    }

    public void setClient(OkHttpClient.Builder okHttpClient){
        this.builder = okHttpClient;
    }

    public OkHttpClient  getDefaultClient(){
        if(builder==null){
            builder = new OkHttpClient.Builder();
            builder.connectTimeout(timeout, TimeUnit.SECONDS);
            builder.writeTimeout(readeTimeOut, TimeUnit.SECONDS);
            builder.readTimeout(readeTimeOut, TimeUnit.SECONDS);
            // 添加公共参数拦截器
//        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
//                .addHeaderParam("platform","android")
//                .addHeaderParam("userToken","1234343434dfdfd3434")
//                .addHeaderParam("userId","123445")
//                .build();
//        builder.addInterceptor(commonInterceptor);
            //添加Https支持
            //信任所有服务器地址
            builder.hostnameVerifier((s, sslSession) -> {
                //设置为true
                return true;
            });
            //创建管理器
//            TrustManager[] trustAllCerts = new TrustManager[] {  };
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
//                sslContext.init(null, trustAllCerts, new SecureRandom());
                //为OkHttpClient设置sslSocketFactory
                builder.sslSocketFactory(sslContext.getSocketFactory(),new X509TrustManager() {
                    @SuppressLint("TrustAllX509TrustManager")
                    @Override
                    public void checkClientTrusted(
                            X509Certificate[] x509Certificates,
                            String s) {
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    @Override
                    public void checkServerTrusted(
                            X509Certificate[] x509Certificates,
                            String s) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[] {};
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(logInterceptor);
        }
        return builder.build();
    }

    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T> WebService api接口
     * @return WebService api接口
     */
    public <T> T create(Class<T> service) {
//        test(getWebService().getUser());
        return getRetrofit().create(service);
    }
//    public WebService getWebService() {
//        return RetrofitServiceManager.getInstance().create(WebService.class);
//    }

}
