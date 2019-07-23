package com.itxiaox.xnet.base;

/**
 * 请求返回成功/失败，成功时把服务器返回的结果回调出去，失败时回调异常信息
 */
public interface HttpCallback<T> {
    void onSuccess(T response);
    void onFailure(int errcode,String errmsg);
}
