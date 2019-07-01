package com.itxiaox.retrofit;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class HttpManager {

    private static HttpManager instance;

    HttpConfig httpConfig;

    private HttpManager(HttpConfig config){
        this.httpConfig = config;
    }


    /**
     * 采用默认配置的初始化
     * @param baseUrl base_url
     * @param logger 是否显示日志输出
     */
    public static void init(String baseUrl,boolean logger){
        init(HttpConfig.createDefault(baseUrl, logger));
    }


    /**
     * 日志
     * @param config 自定义配置
     */
    public static void init(HttpConfig config){
        if (instance == null){
            instance = new HttpManager(config);
        }
    }

    public static HttpManager getInstance(){
        return instance;
    }

    public static <T> T create(final Class<T> service){
        if (instance == null){
            throw new  NullPointerException("HttpManager not init");
        }
        return instance.newRetrofit().create(service);
    }

    private Retrofit newRetrofit(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(httpConfig.getBaseUrl());
        // 设置转换器
        List<Converter.Factory> converterFactories = httpConfig.getConverterFactories();
        if (converterFactories != null && !converterFactories.isEmpty()) {
            for (Converter.Factory factory : converterFactories) {
                builder.addConverterFactory(factory);
            }
        }
        // 设置适配器
        List<CallAdapter.Factory> adapterFactories = httpConfig.getAdapterFactories();
        if (adapterFactories != null && !adapterFactories.isEmpty()) {
            for (CallAdapter.Factory factory : adapterFactories) {
                builder.addCallAdapterFactory(factory);
            }
        }
        //设置okhttp
        OkHttpClient httpClient = newOkHttpClient();
        return builder.client(httpClient).build();
    }

    private OkHttpClient newOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(httpConfig.getConnectTimeoutMilliseconds(), TimeUnit.MILLISECONDS);
        //添加Https支持
        //信任所有服务器地址
        builder.hostnameVerifier((s, sslSession) -> {
            //设置为true
            return true;
        });
//        try {
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            //为OkHttpClient设置sslSocketFactory
//            builder.sslSocketFactory(sslContext.getSocketFactory(),new X509TrustManager() {
//                @Override
//                public void checkClientTrusted(
//                        X509Certificate[] x509Certificates,
//                        String s) {
//                }
//
//                @Override
//                public void checkServerTrusted(
//                        X509Certificate[] x509Certificates,
//                        String s) {
//                }
//
//                @Override
//                public X509Certificate[] getAcceptedIssuers() {
//                    return new X509Certificate[] {};
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //设置拦截器
        List<Interceptor> interceptors = httpConfig.getInterceptors();
        if (interceptors != null && !interceptors.isEmpty()) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        return builder.build();
    }
}
