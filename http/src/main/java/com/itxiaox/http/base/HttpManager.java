package com.itxiaox.http.base;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * 此接口提供的激素http请求通用的方法，该接口可以用Volley实现，也可以用OkHttp等其他方式来实现
 * 接口说明:
 * get参数包含一个url,以及返回数据的接口
 * post/put/delete方法还需要提供一个请求体参数
 */
public interface HttpManager {

    /**
     * 初始化的一些配置,全局配置，在Application中配置
     * @param context 上下文对象
     */
     void init(Context context,@NonNull String baseUrl);


     void init(Context context,HttpConfig httpConfig);
    /**
     *
     * @param url 是除去baseUrl之外的部分,一般为接口名字
     * @param httpParams 请求参数体
     * @param httpCallback 请求结果回调
     * @param <T> 返回值的类型
     */
    <T> void  get(@NonNull String url, HttpParams httpParams,@NonNull HttpCallback<T> httpCallback);

    /**
     *
     * @param url 是除去baseUrl之外的部分,一般为接口名字
     * @param httpParams 请求参数体
     * @param httpCallback 请求结果回调
     * @param <T> 返回值的类型
     */
    <T> void post(@NonNull String url, @NonNull  HttpParams httpParams, @NonNull HttpCallback<T> httpCallback);

    /**
     * //todo 暂未实现
     */
    default <T> void put(String url, HttpParams httpParams, HttpCallback<T> requestCallback){}
    /**
     * //todo 暂未实现
     */
    default <T>  void delete(String url, HttpParams httpParams, HttpCallback<T> requestCallback){}

}
