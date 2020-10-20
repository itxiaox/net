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
     * 多次设置会重新创建HttpClient对象,相当于baseUrl重置
     * @param baseUrl
     * @param logger
     * @return
     */
    public static HttpClient initClient(String baseUrl,boolean logger){
        httpClient = new HttpClient(baseUrl,logger);
        return httpClient;
    }
    /**
     *  根据配置初始化一个HttpClient
     *  多次设置会重新创建HttpClient对象,相当于baseUrl重置
     * @param httpConfig
     * @return
     */
    public static HttpClient initClient(HttpConfig httpConfig){
        httpClient = new HttpClient(httpConfig);
        return httpClient;
    }
    /**
     * 创建多个baseUrl客户端
     * 一个baseUrl对应一个HttpClient客户端
     *
     * @param baseUrl
     * @param logger
     * @return
     */
    public static HttpClient initMultiClient(String baseUrl,boolean logger){
        if (httpClientMap == null) {
            httpClientMap = new HashMap<>();
        }
        if (httpClientMap.containsKey(baseUrl)){
            httpClientMap.remove(baseUrl);
        }
        HttpClient httpClient = new HttpClient(baseUrl,logger);
        //先移除旧的，再添加新的
        removeMultiClient(baseUrl);
        httpClientMap.put(baseUrl,httpClient );
        return httpClient;
    }

    public static void removeMultiClient(String baseUrl){
        if (httpClientMap!=null){
            if (httpClientMap.containsKey(baseUrl)){
                httpClientMap.remove(baseUrl);
            }
        }
    }

    /**
     * 初始化多baseUrl客户端
     * 一个baseUrl对应一个HttpClient客户端
     * @param httpConfig
     * @return
     */
    public static HttpClient initMultiClient(HttpConfig httpConfig){
        if (httpClientMap == null) {
            httpClientMap = new HashMap<>();
        }
        HttpClient httpClient = new HttpClient(httpConfig);
        //先移除旧的，再添加新的
        removeMultiClient(httpConfig.getBaseUrl());
        httpClientMap.put(httpConfig.getBaseUrl(),httpClient );
        return httpClient;
    }

    /**
     * 重置BaseUrl
     * 适用于在APP中重新设置BaseUrl的情况
     * @param baseUrl 重新设置的baseUrl
     * @param logger 是否开启日志
     * @return
     */
//    public static HttpClient resetClient(String baseUrl,boolean logger){
//        httpClient = null;
//        if (httpClientMap!=null){
//            httpClientMap.remove(baseUrl);
//        }
//        return initClient(baseUrl,logger);
//    }
//    /**
//     * 重置BaseUrl
//     * 适用于在APP中重新设置BaseUrl的情况
//     * @param httpConfig 重新配置
//     * @return
//     */
//    public static HttpClient resetClient(HttpConfig httpConfig){
//        httpClient = null;
//        if (httpClientMap!=null){
//            httpClientMap.remove(httpConfig.getBaseUrl());
//        }
//        return initClient(httpConfig);
//    }

    /**
     * 获取WebServices, 仅适用于单个的客户端
     * @param clazz webServices类型
     * @param <T>
     * @return
     */
    public static <T> T  createWebService(Class<T> clazz) {
        if(httpClient==null){
            throw new NullPointerException("HttpClient in null , call initClient() first,please");
        }
        return httpClient.create(clazz);
    }


    /**
     * 获取WebServices, 适用于多个baseUrl的客户端,
     * @param clazz webServices类型
     * @param <T>
     * @return
     */
    public static <T> T  createMultiWebService(Class<T> clazz,String baseUrl) {
        HttpClient httpClient = httpClientMap.get(baseUrl);
        if (httpClient == null) {
            throw new NullPointerException("HttpClient in null , initMultiClient first,please");
        }
       return httpClient.create(clazz);
    }


    private static HttpClient newHttpClient;
    /**
     * 创建一个HttpClient, 用于一种特殊情况，同一个baseUrl，对应两个，HttpClient的情况，但是需要实例化的OKHttpClient不同
     * @param httpConfig
     * @return
     */
    public static HttpClient initNewHttpClient(HttpConfig httpConfig){
        if (newHttpClient==null){
            newHttpClient = new HttpClient(httpConfig);
        }
       return newHttpClient;
    }
    /**
     * 创建一个HttpClient, 用于一种特殊情况，同一个baseUrl，对应两个，HttpClient的情况，但是需要实例化的OKHttpClient不同
     * @param baseUrl
     * @return
     */
    public static HttpClient initNewHttpClient(String baseUrl, boolean logger){
        if (newHttpClient==null){
            newHttpClient = new HttpClient(baseUrl,logger);
        }
        return newHttpClient;
    }
    /**
     * 根据新创建的newHttpClient创建服务
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T  createNewWebService(Class<T> clazz) {
        if(newHttpClient==null){
            throw new NullPointerException("newHttpClient in null , call initNewHttpClient() first,please");
        }
        return newHttpClient.create(clazz);
    }


//    /**
//     * 适用于多个baseUrl并行的情况
//     * @param clazz WebService类
//     * @param baseUrl baseUrl
//     * @param type httpClient类型： SINGLE:单baseUrl, MULTIPLE: 多baseUrl
//     * @param <T> WebService类型
//     * @return
//     */
//    public static <T> T  createWebService(Class<T> clazz,String baseUrl, HttpClientType type) {
//        HttpClient httpClient = null;
//        if (httpClientMap != null) {
//            httpClient = httpClientMap.get(baseUrl);
//        }
//        if (httpClient == null) {
//            httpClient = new HttpClient(baseUrl,true);
//            if (httpClient == null) {
//                throw new NullPointerException("HttpManager create fail !!1");
//            }
//            if (HttpClientType.SINGLE == type) {
//                return httpClient.create(clazz);
//            } else if (HttpClientType.MULTIPLE == type) {
//                if (httpClientMap == null) {
//                    httpClientMap = new HashMap<>();
//                }
//                httpClientMap.put(baseUrl, httpClient);
//            } else {
//                throw new IllegalArgumentException("Invalid type type=SINGLE/MULTIPLE");
//            }
//
//        }
//        return httpClient.create(clazz);
//    }
//    /**
//     * 适用于多个baseUrl并行的情况
//     * @param clazz WebService类
//     * @param httpConfig httpConfig
//     * @param type httpClient类型： SINGLE:单baseUrl, MULTIPLE: 多baseUrl
//     * @param <T> WebService类型
//     * @return
//     */
//    public static <T> T  createWebService(Class<T> clazz,HttpConfig httpConfig, HttpClientType type) {
//        HttpClient httpManager = null;
//        if (httpClientMap != null) {
//            httpManager = httpClientMap.get(httpConfig.getBaseUrl());
//        }
//        if (httpManager == null) {
//            httpManager = new HttpClient(httpConfig);
//            if (httpManager == null) {
//                throw new NullPointerException("HttpManager create fail !!1");
//            }
//            if (HttpClientType.SINGLE == type) {
//                return httpManager.create(clazz);
//            } else if (HttpClientType.MULTIPLE == type) {
//                if (httpClientMap == null) {
//                    httpClientMap = new HashMap<>();
//                }
//                httpClientMap.put(httpConfig.getBaseUrl(), httpManager);
//            } else {
//                throw new IllegalArgumentException("Invalid type type=SINGLE/MULTIPLE");
//            }
//
//        }
//        return httpManager.create(clazz);
//    }



}
