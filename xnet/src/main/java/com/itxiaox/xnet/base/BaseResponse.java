package com.itxiaox.xnet.base;

/**
 * 网络请求结果基类
 * @param <T> 请求结果对象
 */
public class BaseResponse<T> {
    public int status;
    public String message;
    public T data;
    public boolean isSuccess(){
        return  status == 200;
    }
}
