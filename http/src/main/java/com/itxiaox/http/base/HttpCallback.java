package com.itxiaox.http.base;

/**
 * 请求返回成功/失败，成功时把服务器返回的结果回调出去，失败时回调异常信息
 */
public interface HttpCallback<T> {
    /**
     * 返回成功
     * @param response 返回结果response
     */
    void onSuccess(T response);

    /**
     * 返回识别
     * @param  errcode 错误码
     * @param errmsg 错误信息
     */
    void onFailure(int errcode,String errmsg);
}
