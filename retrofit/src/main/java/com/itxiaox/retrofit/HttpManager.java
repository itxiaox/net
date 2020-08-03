package com.itxiaox.retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * Http请求管理类
 */
public class HttpManager {

    private static HttpClient httpClient;
    private static Map<String, HttpClient> httpClientMap;

    /**
     * 初始化一个默认HttpClient
     * 相当于一个Single HttpClientType
     * @param baseUrl
     * @param logger
     * @return
     */
    public static HttpClient initClient(String baseUrl,boolean logger){
        if (httpClient==null){
            synchronized (HttpManager.class){
                if (httpClient == null){
                    httpClient = new HttpClient(baseUrl,logger);
                }
            }
        }
        return httpClient;
    }

    /**
     * 重置BaseUrl
     * 适用于在APP中重新设置BaseUrl的情况
     * @param baseUrl 重新设置的baseUrl
     * @param logger 是否开启日志
     * @return
     */
    public static HttpClient resetClient(String baseUrl,boolean logger){
        httpClient = null;
        if (httpClientMap!=null){
            httpClientMap.remove(baseUrl);
        }
        return initClient(baseUrl,logger);
    }
    /**
     * 重置BaseUrl
     * 适用于在APP中重新设置BaseUrl的情况
     * @param httpConfig 重新配置
     * @return
     */
    public static HttpClient resetClient(HttpConfig httpConfig){
        httpClient = null;
        if (httpClientMap!=null){
            httpClientMap.remove(httpConfig.getBaseUrl());
        }
        return initClient(httpConfig);
    }

    /**
     *  根据配置初始化一个HttpClient
     *  相当于一个Single HttpClientType
     * @param httpConfig
     * @return
     */
    public static HttpClient initClient(HttpConfig httpConfig){
        if (httpClient==null){
            synchronized (HttpManager.class){
                if (httpClient == null){
                    httpClient = new HttpClient(httpConfig);
                }
            }
        }
        return httpClient;
    }

    /**
     * 获取WebServices
     * @param clazz webServices类型
     * @param <T>
     * @return
     */
    public static <T> T  createWebService(Class<T> clazz) {
        if(httpClient==null){
            throw new NullPointerException("HttpClient in null , HttpFactory#initClient first,please");
        }
        return httpClient.create(clazz);
    }

    /**
     * 适用于多个baseUrl并行的情况
     * @param clazz WebService类
     * @param baseUrl baseUrl
     * @param type httpClient类型： SINGLE:单baseUrl, MULTIPLE: 多baseUrl
     * @param <T> WebService类型
     * @return
     */
    public static <T> T  createWebService(Class<T> clazz,String baseUrl, HttpClientType type) {
        HttpClient httpClient = null;
        if (httpClientMap != null) {
            httpClient = httpClientMap.get(baseUrl);
        }
        if (httpClient == null) {
            httpClient = new HttpClient(baseUrl,true);
            if (httpClient == null) {
                throw new NullPointerException("HttpManager create fail !!1");
            }
            if (HttpClientType.SINGLE == type) {
                return httpClient.create(clazz);
            } else if (HttpClientType.MULTIPLE == type) {
                if (httpClientMap == null) {
                    httpClientMap = new HashMap<>();
                }
                httpClientMap.put(baseUrl, httpClient);
            } else {
                throw new IllegalArgumentException("Invalid type type=SINGLE/MULTIPLE");
            }

        }
        return httpClient.create(clazz);
    }
    /**
     * 适用于多个baseUrl并行的情况
     * @param clazz WebService类
     * @param httpConfig httpConfig
     * @param type httpClient类型： SINGLE:单baseUrl, MULTIPLE: 多baseUrl
     * @param <T> WebService类型
     * @return
     */
    public static <T> T  createWebService(Class<T> clazz,HttpConfig httpConfig, HttpClientType type) {
        HttpClient httpManager = null;
        if (httpClientMap != null) {
            httpManager = httpClientMap.get(httpConfig.getBaseUrl());
        }
        if (httpManager == null) {
            httpManager = new HttpClient(httpConfig);
            if (httpManager == null) {
                throw new NullPointerException("HttpManager create fail !!1");
            }
            if (HttpClientType.SINGLE == type) {
                return httpManager.create(clazz);
            } else if (HttpClientType.MULTIPLE == type) {
                if (httpClientMap == null) {
                    httpClientMap = new HashMap<>();
                }
                httpClientMap.put(httpConfig.getBaseUrl(), httpManager);
            } else {
                throw new IllegalArgumentException("Invalid type type=SINGLE/MULTIPLE");
            }

        }
        return httpManager.create(clazz);
    }



}
