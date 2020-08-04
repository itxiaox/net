package com.itxiaox.http.okhttp;

import com.itxiaox.http.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class NetworkStateInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        if(NetworkUtils.isConnected()){//有网络，执行后面请求
            return chain.proceed(chain.request());
        }else {
            throw new IOException("没有网络");//okhttp 只是向上抛出了IOException，所以这里只能用IOException或者其子类
        }
    }
}
