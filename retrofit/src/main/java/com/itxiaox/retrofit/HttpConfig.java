package com.itxiaox.retrofit;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpConfig {

    private String baseUrl;
    private long connectTimeoutMilliseconds;
    private List<Interceptor> interceptors;
    private List<Converter.Factory> converterFactories;
    private List<CallAdapter.Factory> adapterFactories;

    private static final long DEFAULT_CONNECT_TIMEOUT = 15 * 1000;


    /**
     * 默认配置，timeout = 15s，
     * @param baseUrl base_url
     * @param logger 是否开启日志
     * @return 返回默认的Http配置
     */
    public static HttpConfig createDefault(String baseUrl, boolean logger) {
        Builder builder = new Builder();
        if (logger) {
            builder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.baseUrl(baseUrl)
                .connectTimeoutMilliseconds(DEFAULT_CONNECT_TIMEOUT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 获取base_url
     * @return base_url
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 获取超时时间，单位ms
     * @return 超时时间
     */
    public long getConnectTimeoutMilliseconds() {
        return connectTimeoutMilliseconds;
    }

    /**
     * 获取拦截器
     * @return 拦截器
     */
    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    /**
     * Get ConverterFactories
     * @return ConverterFactories
     */

    public List<Converter.Factory> getConverterFactories() {
        return converterFactories;
    }
    /**
     * Get AdapterFactories
     * @return AdapterFactories
     */
    public List<CallAdapter.Factory> getAdapterFactories() {
        return adapterFactories;
    }

    private HttpConfig(Builder builder) {
        baseUrl = builder.baseUrl;
        connectTimeoutMilliseconds = builder.connectTimeoutMilliseconds;
        interceptors = new ArrayList<>(builder.interceptors);
        converterFactories = new ArrayList<>(builder.converterFactories);
        adapterFactories = new ArrayList<>( builder.adapterFactories);
    }

    /**
     * 构造者
     */
    public static final class Builder {
        private String baseUrl;
        private long connectTimeoutMilliseconds;
        private List<Interceptor> interceptors = new ArrayList<>();
        private List<Converter.Factory> converterFactories = new ArrayList<>();
        private List<CallAdapter.Factory> adapterFactories = new ArrayList<>();

        public Builder() {
        }

        /**
         * 设置base_url
         *
         * @param url baseUrl
         * @return
         */
        public Builder baseUrl(String url) {
            baseUrl = url;
            return this;
        }

        /**
         * 设置超时时间
         *
         * @param time timeout, 单位毫秒
         * @return the builder
         */
        public Builder connectTimeoutMilliseconds(long time) {
            connectTimeoutMilliseconds = time;
            return this;
        }

        /**
         * 添加Interceptor 拦截器，可以通过拦截器实现添加header， 统一参数，日志拦截等功能
         *
         * @param interceptor interceptor the interceptor
         * @return the builder
         */
        public Builder addInterceptor(Interceptor interceptor) {
            interceptors.add(interceptor);
            return this;
        }

        /**
         * 添加 convert factory 转换器， 默认使用GsonFactory
         * @param factory the factory
         * @return the builder
         */

        public Builder addConverterFactory(Converter.Factory factory) {
            converterFactories.add(factory);
            return this;
        }
        /**
         * 添加Adapter factory转换器
         * Add call adapter factory builder.
         * @param factory the factory
         * @return the builder
         */
        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            adapterFactories.add(factory);
            return this;
        }

        /**
         * 创建  HttpConfig
         * @return HttpConfig实例
         */
        public HttpConfig build() {
            return new HttpConfig(this);
        }
    }


//    public static HttpConfig createDefault(String baseUrl,boolean logger){
//
//
//
//    }


}
