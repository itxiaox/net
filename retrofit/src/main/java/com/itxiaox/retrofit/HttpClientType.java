package com.itxiaox.retrofit;

/**
 * 客户端类型
 */
public enum  HttpClientType {
    /**
     * 单个的客户端
     * 全局只有一个BaseUrl
     */
    SINGLE,
    /**
     * 多个客户端
     * 全局有多个BaseUrl
     */
    MULTIPLE
}
