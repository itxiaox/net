package com.itxiaox.retrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 配置Http请求相关参数
 *
 * <p>
 * 默认:
 * timeout = 60s;
 * .addConverterFactory(GsonConverterFactory.create())
 * </P>
 */
public class HttpConfig {

    private String baseUrl;
    /**
     * 读取超时
     */
    private long readTimeout;
    private TimeUnit readTimeUnit;
    /**
     * 写超时
     */
    private long writeTimeout;
    private TimeUnit writeTimeUnit;
    /**
     * 连接超时
     */
    private long connectTimeout;
    private TimeUnit connectTimeUnit;
    /**
     * 等待时间单位
     */

    private ConnectionPool connectionPool;

    /**
     * 添加连接重试控制
     */
    private boolean retryOnConnectionFailure;

    private List<Interceptor> interceptors;
    private List<Converter.Factory> converterFactories;
    private List<CallAdapter.Factory> adapterFactories;

    private static final long DEFAULT_CONNECT_TIMEOUT = 30 * 1000;


    /**
     * 默认配置，timeout = 15s，
     *
     * @param baseUrl base_url
     * @param logger  是否开启日志
     * @return 返回默认的Http配置
     */
    public static HttpConfig createDefault(String baseUrl, boolean logger) {
        Builder builder = new Builder();
        if (logger) {
            builder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.baseUrl(baseUrl)
                .connectTimeoutMilliseconds(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public boolean isRetryOnConnectionFailure() {
        return retryOnConnectionFailure;
    }

    /**
     * 获取base_url
     *
     * @return base_url
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 获取读取超时时间
     *
     * @return 超时时间
     */
    public long getReadTimeout() {
        return readTimeout;
    }

    /**
     * 读取写超时时间
     *
     * @return 读取超时时间
     */
    public long getWriteTimeout() {
        return writeTimeout;
    }

    public TimeUnit getReadTimeUnit() {
        return readTimeUnit;
    }


    public TimeUnit getWriteTimeUnit() {
        return writeTimeUnit;
    }


    public TimeUnit getConnectTimeUnit() {
        return connectTimeUnit;
    }

    /**
     * 获取连接超时时间
     *
     * @return 连接超时时间
     */
    public long getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * 获取拦截器
     *
     * @return 拦截器
     */
    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    /**
     * Get ConverterFactories
     *
     * @return ConverterFactories
     */

    public List<Converter.Factory> getConverterFactories() {
        return converterFactories;
    }

    /**
     * Get AdapterFactories
     *
     * @return AdapterFactories
     */
    public List<CallAdapter.Factory> getAdapterFactories() {
        return adapterFactories;
    }

    private HttpConfig(Builder builder) {
        baseUrl = builder.baseUrl;
        this.connectTimeout = builder.connectTimeout;
        this.connectTimeUnit = builder.connectTimeUnit;
        this.readTimeout = builder.readTimeout;
        this.readTimeUnit = builder.readTimeUnit;
        this.writeTimeout = builder.writeTimeout;
        this.writeTimeUnit = builder.writeTimeUnit;
        this.connectionPool = builder.connectionPool;
        this.retryOnConnectionFailure = builder.retryOnConnectionFailure;
        interceptors = new ArrayList<>(builder.interceptors);
        converterFactories = new ArrayList<>(builder.converterFactories);
        adapterFactories = new ArrayList<>(builder.adapterFactories);
    }

    /**
     * 构造者
     */
    public static final class Builder {
        private String baseUrl;
        /**
         * 读取超时
         */
        private long readTimeout;
        private TimeUnit readTimeUnit;
        /**
         * 写超时
         */
        private long writeTimeout;
        private TimeUnit writeTimeUnit;
        /**
         * 连接超时
         */
        private long connectTimeout;
        private TimeUnit connectTimeUnit;
        /**
         * 等待时间单位
         */

        private ConnectionPool connectionPool;

        private  boolean retryOnConnectionFailure = true;
        private List<Interceptor> interceptors = new ArrayList<>();
        private List<Converter.Factory> converterFactories = new ArrayList<>();
        private List<CallAdapter.Factory> adapterFactories = new ArrayList<>();

        public Builder() {
        }

        /**
         * 设置base_url
         *
         * @param url baseUrl
         * @return 请求地址
         */
        public Builder baseUrl(String url) {
            baseUrl = url;
            return this;
        }

        /**
         * 设置失败重连
         * @param retryOnConnectionFailure  是否失败重连
         * @return HttpConfig
         */
        public Builder retryOnConnectionFailure(boolean retryOnConnectionFailure){
            this.retryOnConnectionFailure = retryOnConnectionFailure;
            return this;
        }
        /**
         * 设置连接超时时间
         *
         * @param time timeout, 单位毫秒
         * @param timeUnit 单位
         * @return the builder
         */
        public Builder connectTimeoutMilliseconds(long time, TimeUnit timeUnit) {
            connectTimeout = time;
            connectTimeUnit = timeUnit;
            return this;
        }


        /**
         * 设置读取超时时间
         *
         * @param time 超时时间
         * @param timeUnit  单位
         * @return httpConfig对象
         */
        public Builder readTimeoutMilliseconds(long time, TimeUnit timeUnit) {
            readTimeout = time;
            readTimeUnit = timeUnit;
            return this;
        }

        /**
         * 设置写超时时间
         *
         * @param time 超时时间
         * @param timeUnit  单位
         * @return httpConfig对象
         */
        public Builder writeTimeoutMilliseconds(long time, TimeUnit timeUnit) {
            writeTimeout = time;
            writeTimeUnit = timeUnit;
            return this;
        }

        public Builder connectionPool(ConnectionPool connectionPool){
            this.connectionPool = connectionPool;
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
         *
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
         *
         * @param factory the factory
         * @return the builder
         */
        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            adapterFactories.add(factory);
            return this;
        }

        /**
         * 创建  HttpConfig
         *
         * @return HttpConfig实例
         */
        public HttpConfig build() {
            return new HttpConfig(this);
        }
    }


}
