package com.itxiaox.xnet.base;

/**
 * 请求返回成功/失败，成功时把服务器返回的结果回调出去，失败时回调异常信息
 */
public interface IRequestCallback {
    void onSuccess(String response);
    void onFailure(Throwable throwable);
}
