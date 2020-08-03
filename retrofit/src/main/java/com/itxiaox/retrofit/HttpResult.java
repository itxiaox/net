package com.itxiaox.retrofit;

/**
 * 统一封装返回数据格式
 * @param <T>
 */
public class HttpResult<T> {
    /**
     * 通用的数据格式
     * {
     *     "code":0,
     *     "msg":"成功",
     *     "data":{
     *
     *     }
     * }
     * </br>or</br>
     * {
     *     "code":0,
     *     "msg":"成功",
     *     "data":[]
     * }
     */

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
