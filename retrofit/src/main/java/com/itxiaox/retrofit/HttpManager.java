package com.itxiaox.retrofit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import okhttp3.HttpUrl;

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
        if (!isHttpUrl(baseUrl)){
            throw new IllegalArgumentException("无效的baseUrl");
        }
        httpClient = new HttpClient(baseUrl,logger);
        return httpClient;
    }
    /**
     *  根据配置初始化一个HttpClient
     *  多次设置会重新创建HttpClient对象,相当于baseUrl重置
     * @param httpConfig
     * @return
     */
    public static HttpClient initClient(HttpConfig httpConfig) {
        if (!isHttpUrl(httpConfig.getBaseUrl())){
            throw new IllegalArgumentException("无效的baseUrl");
        }
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
        if (!isHttpUrl(baseUrl)){
            throw new IllegalArgumentException("无效的baseUrl");
        }
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
        if(!isHttpUrl(httpConfig.getBaseUrl())){
            throw new IllegalArgumentException("无效的baseUrl");
        }
        if (httpClientMap == null) {
            httpClientMap = new HashMap<>();
        }
        HttpClient httpClient = new HttpClient(httpConfig);
        //先移除旧的，再添加新的
        removeMultiClient(httpConfig.getBaseUrl());
        httpClientMap.put(httpConfig.getBaseUrl(),httpClient );
        return httpClient;
    }

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
        if (!isHttpUrl(httpConfig.getBaseUrl())){
            throw new IllegalArgumentException("无效的baseUrl");
        }
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
    public static HttpClient initNewHttpClient(String baseUrl, boolean logger) throws Exception{
        if (!isHttpUrl(baseUrl)){
            throw new IllegalArgumentException("无效的baseUrl");
        }
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



    public static boolean isHttpUrl(String url){
        HttpUrl httpUrl;
        if (url==null||url.equals("")){
            throw new NullPointerException("url is null");
        }
        try{
            httpUrl = HttpUrl.get(url);
            return httpUrl!=null;
        }catch(Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    public static boolean isUrl(String url){
        String regex = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(url).matches();
    }

}
