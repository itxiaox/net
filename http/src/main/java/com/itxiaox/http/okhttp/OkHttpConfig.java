package com.itxiaox.http.okhttp;

import com.itxiaox.http.base.HttpConfig;
import com.itxiaox.http.base.HttpLogger;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OKHttpConfig 配置实现类
 */
public class OkHttpConfig extends HttpConfig {


    private List<Interceptor> interceptors;

    /**
     * 默认配置，timeout = 15s，
     * @param baseUrl base_url
     * @param logger 是否开启日志
     * @return 返回默认的Http配置
     */
    public static OkHttpConfig createDefault(String baseUrl, boolean logger) {

        Builder okhttpBuilder = new Builder()
                .baseUrl(baseUrl)
                .connectTimeoutMilliseconds(DEFAULT_CONNECT_TIMEOUT);
        okhttpBuilder.addInterceptor(new NetworkStateInterceptor());
        if (logger) {
            okhttpBuilder.addInterceptor(new HttpLoggingInterceptor(message -> {
                HttpLogger.d(message);
            })
                    .setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return okhttpBuilder.build();
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


    private OkHttpConfig(Builder builder) {
        baseUrl = builder.baseUrl;
        connectTimeoutMilliseconds = builder.connectTimeoutMilliseconds;
        interceptors = builder.interceptors;
    }

    /**
     * 构造者
     */
    public static class Builder extends HttpConfig.Builder {
        protected String baseUrl;
        protected long connectTimeoutMilliseconds = DEFAULT_CONNECT_TIMEOUT;
        private List<Interceptor> interceptors = new ArrayList<>();

        public Builder() {

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
         * 创建  OkHttpConfig
         *
         * @return HttpConfig实例
         */
        public OkHttpConfig build() {
            return new OkHttpConfig(this);
        }

        public Builder baseUrl(String val) {
            baseUrl = val;
            return this;
        }

        public Builder connectTimeoutMilliseconds(long val) {
            connectTimeoutMilliseconds = val;
            return this;
        }

        public Builder interceptors(List<Interceptor> val) {
            interceptors = val;
            return this;
        }
    }




}
