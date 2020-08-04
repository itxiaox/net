package com.itxiaox.http.base;

import android.content.Context;

import com.itxiaox.http.okhttp.OKHttpManager;
import com.itxiaox.http.retrofit.RetrofitManager;
import com.itxiaox.http.volley.VolleyManager;

/**
 * 该类的作用是用于返回一个IRequestManager对象，这个IRequestManager的实现类
 * 可以是使用Volley实现的http请求对象，也可以是OkHttp实现的http请求对象
 * Activity/Fragment/Presenter中，只要调用getRequestManager()方法就能得到
 * http请求的操作接口，而不用关心具体是使用什么实现的。
 */
public class HttpFactory {
    private static HttpManager httpManager;

    public static HttpManager getHttpManager() {
        if (httpManager == null) {
            throw new NullPointerException("httpManager is null,please init first");
        }
        return httpManager;
//        return VolleyManager.getInstance();
    }

    public enum HttpManagerType {
        OKHTTP,
        VOLLEY,
        RETROFIT
    }

    /**
     * init HttpManger,在整个app中只实例化一次即可，一般在application的onCreate中进行调用
     *
     * @param httpManagerType OKHTTP:底层以OkHttp来实现，VOLLEY:底层以Volley来实现：RETROFIT:底层以Retrofit来实现
     * @return
     */
    public static HttpManager init(Context context, String baseUrl, HttpManagerType httpManagerType) {
        httpManager = createHttpManager(httpManagerType);
        httpManager.init(context, baseUrl);
        return httpManager;
    }

    public static HttpManager init(Context context, HttpConfig httpConfig, HttpManagerType httpManagerType) {
        httpManager = createHttpManager(httpManagerType);
        httpManager.init(context, httpConfig);
        return httpManager;
    }

    private static HttpManager createHttpManager(HttpManagerType httpManagerType){
        switch (httpManagerType) {
            case OKHTTP:
                httpManager = OKHttpManager.getInstance();
                break;
            case VOLLEY:
                httpManager = VolleyManager.getInstance();
                break;
            case RETROFIT:
                httpManager = RetrofitManager.getInstance();
                break;
            default:
                httpManager = RetrofitManager.getInstance();
                break;
        }
        return httpManager;
    }

}
