package com.itxiaox.retrofit;

import com.google.gson.Gson;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceManager {
    private   String baseUrl ;
    private   int timeout;//超时时间
    private   int readeTimeOut;
    private  Retrofit mRetrofit;
    private  List<Interceptor> interceptors;
    private  List<Interceptor> networkInterceptors;;
    private   Converter.Factory converter;

    public static class Builder{
        private String baseUrl = "";
        private int timeout = 5;
        private int readeTimeOut = 10;
        private OkHttpClient.Builder builder;
        private List<Interceptor> interceptors;
        private List<Interceptor> networkInterceptors;
        private Converter.Factory converter;
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
        public Builder addConverterFactory(Converter.Factory converter) {
            this.converter = converter;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor){
            if(interceptors==null||interceptors.size()<=0){
                interceptors = new ArrayList<>();
            }
            this.interceptors.add(interceptor);
            return  this;
        }
        public Builder addNetworkInterceptor(Interceptor interceptor){
            if(networkInterceptors==null||networkInterceptors.size()<=0){
                networkInterceptors = new ArrayList<>();
            }
            this.networkInterceptors.add(interceptor);
            return  this;
        }

        public Builder client(OkHttpClient.Builder okHttpClient){
            this.builder = okHttpClient;
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
        this.interceptors = builder.interceptors;
        this.networkInterceptors = builder.networkInterceptors;
        this.converter = builder.converter;
        this.builder = builder.builder;
        // 创建Retrofit
        getRetrofit();
    }

    public Retrofit getRetrofit(){
        if (mRetrofit==null){
            if(baseUrl==null||baseUrl.equals(""))throw new NullPointerException("baseUrl is Null");
            if(converter==null){
                converter = GsonConverterFactory.create();
            }
            mRetrofit = new Retrofit.Builder()
                    .client(getOkHttpClient().build())
//                  .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(converter)
                    .baseUrl(baseUrl)
                    .build();
        }
        return mRetrofit;
    }


    private void setRetrofit(Retrofit retrofit ){
        this.mRetrofit = retrofit;
    }

    public void setClient(OkHttpClient.Builder okHttpClient){
        this.builder = okHttpClient;
    }

    private OkHttpClient.Builder getOkHttpClient(){
        if(builder==null){
            builder = new OkHttpClient.Builder();
            builder.connectTimeout(timeout, TimeUnit.SECONDS);
            builder.writeTimeout(readeTimeOut, TimeUnit.SECONDS);
            builder.readTimeout(readeTimeOut, TimeUnit.SECONDS);
            //添加Https支持
            //信任所有服务器地址
            builder.hostnameVerifier((s, sslSession) -> {
                //设置为true
                return true;
            });
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                //为OkHttpClient设置sslSocketFactory
                builder.sslSocketFactory(sslContext.getSocketFactory(),new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(
                            X509Certificate[] x509Certificates,
                            String s) {
                    }

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
            if(networkInterceptors!=null&&networkInterceptors.size()>0){
                for (Interceptor interceptor: networkInterceptors) {
                    builder.addNetworkInterceptor(interceptor);
                }
            }
            if(interceptors!=null&&interceptors.size()>0){
                for (Interceptor interceptor: interceptors) {
                    builder.addInterceptor(interceptor);
                }
            }
        }
        return builder;
    }


    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T> WebService api接口
     * @return WebService api接口
     */
    public <T> T create(Class<T> service) {
        return getRetrofit().create(service);
    }

    private static RetrofitServiceManager retrofitServiceManager;


    /**
     * 初始化，设置baseUrl，只需初始化一次即可，可以在Application的onCreate中进行初始化
     * 本初始化采用以下默认配置
     * 1. TimeOut为30秒
     * 2. ReadTimeOut为30秒，
     * 3. 采用了GsonConverterFactory转换器
     * <pre>
     *     RetrofitServiceManager.init(baseUrl);//在Application的onCreate中进行初始化
     *     //在其它需要进行网络请求的地方只需调用
     *    XXService  xxx = RetrofitServiceManager.getWebService(xxService.class);
     * <pre/>
     * @param url
     */
    public static void init(String url){
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLog());
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            // 添加公共参数拦截器
//        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
//                .addHeaderParam("platform","android")
//                .addHeaderParam("userToken","1234343434dfdfd3434")
//                .addHeaderParam("userId","123445")
//                .build();
//        builder.addInterceptor(commonInterceptor);
        if(retrofitServiceManager==null){
            retrofitServiceManager =   new Builder()
                    .baseUrl(url)
                    .timeOut(30)
                    .readeTimeOut(30)
                    .addNetworkInterceptor(logInterceptor)
//                    .addConverterFactory(GsonConverterFactory.create())
                    .builder();
        }

    }

    /**
     * 获取WebService 操作接口
     * @param service 自定义的WebService接口
     * @param <T> 类型为自定义的WebService
     * @return 返回的WebService接口实例
     */
    public static <T> T getWebService(Class<T> service){
        if(retrofitServiceManager==null)throw new IllegalStateException("RetrofitServiceManager not init");
        return retrofitServiceManager.create(service);
    }
}
