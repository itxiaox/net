package com.itxiaox.http.base;

/**
 * Http请求配置类，主要配置header信息，timeout, ConvertAdapter等信息
 */
public class HttpConfig {
    protected String baseUrl;
    protected long connectTimeoutMilliseconds;
    protected static final long DEFAULT_CONNECT_TIMEOUT = 15 * 1000;

    protected boolean enableCookie = false;

    private HttpConfig(Builder builder) {
        baseUrl = builder.baseUrl;
        connectTimeoutMilliseconds = builder.connectTimeoutMilliseconds;
        enableCookie = builder.enableCookie;
    }

    public HttpConfig() {
    }

    public String getBaseUrl() {
        return baseUrl;
    }


    public long getConnectTimeoutMilliseconds() {
        return connectTimeoutMilliseconds;
    }

    public static class Builder {
        protected String baseUrl;
        protected long connectTimeoutMilliseconds = DEFAULT_CONNECT_TIMEOUT;
        private boolean enableCookie;

        public Builder() {
        }

        public Builder baseUrl(String val) {
            baseUrl = val;
            return this;
        }


        public Builder connectTimeoutMilliseconds(long val) {
            connectTimeoutMilliseconds = val;
            return this;
        }

        public Builder enableCookie(boolean val) {
            enableCookie = val;
            return this;
        }

        public HttpConfig build() {
            return new HttpConfig(this);
        }


        public String getBaseUrl() {
            return baseUrl;
        }

        public long getConnectTimeoutMilliseconds() {
            return connectTimeoutMilliseconds;
        }
    }


}
