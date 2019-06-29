package com.itxiaox.xnet;

import com.itxiaox.xnet.base.IRequestManager;
import com.itxiaox.xnet.okhttp.OkHttpRequestManager;
import com.itxiaox.xnet.volley.VolleyRequestManager;

public class HttpFactory {
    private static IRequestManager requestManager;
    HttpRequestType httpRequestType;

    String  baseUrl = "";
    public enum HttpRequestType{
        OKHTTP,VOLLEY
    }

    public  static IRequestManager createRequest(HttpRequestType httpRequestType ){
        if(requestManager==null){
            synchronized (HttpFactory.class){
                if(requestManager==null){
                    switch (httpRequestType.ordinal() ){
                        case 0:
                            requestManager = OkHttpRequestManager.getInstance();
                            break;
                        case 1:
                            requestManager = VolleyRequestManager.getInstance();
                            break;
                    }
                }
            }
        }
       return requestManager;
    }
}
