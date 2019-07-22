package com.itxiaox.xnet.base;

import android.content.Context;

/**
 * 此接口提供的激素http请求通用的方法，该接口可以用Volley实现，也可以用OkHttp等其他方式来实现
 * 接口说明:
 * get参数包含一个url,以及返回数据的接口
 * post/put/delete方法还需要提供一个请求体参数
 */
public interface IRequestManager {

    /**
     * 初始化的一些配置,全局配置，在Application中配置
     * @param context 上下文对象
     */
    void init(Context context,String baseUrl);

    /**
     *
     * @param url 是除去baseUrl之外的部分
     * @param paramBody
     * @param requestCallback
     * @param <T>
     */
    <T> void  get(String url, RequestBody paramBody, IRequestCallback<T> requestCallback);

    <T>  void post(String url, RequestBody paramBody, IRequestCallback<T> requestCallback);

    void put(String url, String requestBodyJson, IRequestCallback requestCallback);

    void delete(String url, String requestBodyJson, IRequestCallback requestCallback);

}
