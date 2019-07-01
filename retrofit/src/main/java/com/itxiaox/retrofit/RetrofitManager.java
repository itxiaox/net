package com.itxiaox.retrofit;

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

@Deprecated
public class RetrofitManager {


    private  static Retrofit.Builder builder;
    private  static OkHttpClient.Builder client;
    private static final int TIMEOUT = 30;//默认超声时间

    public static Retrofit.Builder getBuilder(){
        if(builder==null){
            builder = new Retrofit.Builder();
        }
        return builder;
    }

    public static  OkHttpClient.Builder getClient(){
        if(client == null){
            client = new OkHttpClient.Builder();
        }
        return client;
    }

    public static OkHttpClient.Builder defaultClient(){
        if(client == null){
            client = new OkHttpClient.Builder();
            client.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
            client.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
            client.readTimeout(TIMEOUT, TimeUnit.SECONDS);
            //添加Https支持
            //信任所有服务器地址
            client.hostnameVerifier((s, sslSession) -> {
                //设置为true
                return true;
            });
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                //为OkHttpClient设置sslSocketFactory
                client.sslSocketFactory(sslContext.getSocketFactory(),new X509TrustManager() {
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
            //设置HttpLog日志过滤器
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLog());
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addNetworkInterceptor(logInterceptor);

            // 添加公共参数拦截器
//        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
//                .addHeaderParam("platform","android")
//                .addHeaderParam("userToken","1234343434dfdfd3434")
//                .addHeaderParam("userId","123445")
//                .build();
//        client.addInterceptor(commonInterceptor);

        }
        return  client;
    }



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
     * @param baseUrl 接口基本路径
     */
    public static void init(String baseUrl){
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLog());
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if(builder == null){
            builder = new Retrofit.Builder().baseUrl(baseUrl)
                    .client(defaultClient().build())
                    .addConverterFactory(GsonConverterFactory.create());
        }
    }

    /**
     * 获取WebService 操作接口
     * @param service 自定义的WebService接口
     * @param <T> 类型为自定义的WebService
     * @return 返回的WebService接口实例
     */
    public static <T> T getWebService(Class<T> service){
        if(builder==null)throw new IllegalStateException("RetrofitServiceManager not init");
        return builder.build().create(service);
    }
}
