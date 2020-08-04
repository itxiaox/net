package com.itxiaox.http.okhttp;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.itxiaox.http.base.HttpCallback;
import com.itxiaox.http.base.HttpConfig;
import com.itxiaox.http.base.HttpLogger;
import com.itxiaox.http.base.HttpManager;
import com.itxiaox.http.base.HttpParams;
import com.itxiaox.http.utils.ErrorCode;
import com.itxiaox.http.utils.StringUtils;
import org.json.JSONException;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OKHttpManager implements HttpManager {
    private static final String TAG = "OKHttpManager";
    private static final int DEFAULT_TIME_OUT = 30000;
    private String token = "token";
    private String baseUrl;
    public static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient okHttpClient;
    private Handler handler= new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public static OKHttpManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final OKHttpManager INSTANCE = new OKHttpManager();
    }


    private OKHttpManager() {
    }

    @Override
    public void init(Context context,String baseUrl) {
        HttpLogger.d("init OKHttpManager");
        this.baseUrl = baseUrl;
        OkHttpConfig okHttpConfig = OkHttpConfig.createDefault(baseUrl,HttpLogger.logger());
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(okHttpConfig.getConnectTimeoutMilliseconds(), TimeUnit.SECONDS);//连接 超时时间
        builder.writeTimeout(okHttpConfig.getConnectTimeoutMilliseconds(), TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(okHttpConfig.getConnectTimeoutMilliseconds(), TimeUnit.SECONDS);//读操作 超时时间
        //默认开启错误重连
        builder.retryOnConnectionFailure(true);//错误重连
        // 添加公共参数拦截器
        for (Interceptor interceptor: okHttpConfig.getInterceptors()) {
            builder.addInterceptor(interceptor);
        }
        okHttpClient = builder.build();
    }

    @Override
    public void init(Context context, HttpConfig httpConfig) {
        HttpLogger.d("init OKHttpManager");
        OkHttpConfig okHttpConfig = (OkHttpConfig) httpConfig;
        this.baseUrl = okHttpConfig.getBaseUrl();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(okHttpConfig.getConnectTimeoutMilliseconds(), TimeUnit.SECONDS);//连接 超时时间
        builder.writeTimeout(okHttpConfig.getConnectTimeoutMilliseconds(), TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(okHttpConfig.getConnectTimeoutMilliseconds(), TimeUnit.SECONDS);//读操作 超时时间
        //默认开启错误重连
        builder.retryOnConnectionFailure(true);//错误重连
        // 添加公共参数拦截器
        for (Interceptor interceptor: okHttpConfig.getInterceptors()) {
            builder.addInterceptor(interceptor);
        }
        okHttpClient = builder.build();
    }

    @Override
    public <T> void get(String url, HttpParams httpParams, HttpCallback<T> requestCallback) {
        checkUrl(url);
        String absouleteUrl = baseUrl+url;
        if (httpParams!=null&&!httpParams.isEmpty()){
            absouleteUrl = httpParams.createGetUrl(absouleteUrl);
            try {
                HttpLogger.d(String.format("%s - get params:%s",httpParams.toJSON()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        HttpLogger.d(String.format("%s - get url:%s",getClass().getSimpleName(),absouleteUrl));
        Request request = new Request.Builder()
                .url(absouleteUrl)
                .tag(url)
                .get()
                .build();
        addCallBack(requestCallback, request);
    }

    @Override
    public <T> void post(String url, HttpParams httpParams, HttpCallback<T> requestCallback) {
        checkUrl(url);
        String absoulteUrl = baseUrl+url;
        HttpLogger.d(String.format("%s - post url:%s",getClass().getSimpleName(),absoulteUrl));
        Request.Builder builder =  new Request.Builder()
                .tag(url)
                .url(absoulteUrl);
        if(httpParams==null||httpParams.isEmpty()){
           throw new IllegalStateException("param is empty");
        }
        try {
            String paramJson = httpParams.toJSON();
            HttpLogger.d(String.format("%s - get params:%s",String.format(url),paramJson));
            RequestBody body =  RequestBody.create(TYPE_JSON, paramJson);
            builder.post(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = builder.build();
        addCallBack(requestCallback, request);
    }


    private void addCallBack(final HttpCallback requestCallback, Request request) {
        if(okHttpClient==null){
            throw new NullPointerException("okHttpClient is null,please invoke init method first");
        }
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                HttpLogger.d(String.format("%s OKHttpManager-onFailure,%s",call.request().tag(),e.getMessage()));
                handler.post(() -> requestCallback.onFailure(ErrorCode.REQUEST_FAIL,e.getMessage()));
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String result = response.body().string();
                    HttpLogger.format("%s OKHttpManager-onSuccess",
                            String.valueOf(call.request().tag()));
                    HttpLogger.json(result);
                    handler.post(() -> requestCallback.onSuccess(result));
                } else {
                    HttpLogger.d(String.format(Locale.getDefault(),"%s OKHttpManager-onSuccess,errcode=%d,%s",call.request().tag()
                            ,response.code(),response.message()));
                    handler.post(() -> requestCallback.onFailure(response.code(),response.message()));
                }
            }
        });
    }

    private void checkUrl(String url){
        if(StringUtils.isEmpty(url)){
            throw new NullPointerException("url is null");
        }

    }
}

//项目中设置头信息
//        Interceptor headerInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request originalRequest = chain.request();
//                Request.Builder requestBuilder = originalRequest.newBuilder()
//                        .addHeader("Accept-Encoding", "gzip")
//                        .addHeader("Accept", "application/json")
//                        .addHeader("Content-Type", "application/json; charset=utf-8")
//                        .method(originalRequest.method(), originalRequest.body());
////                requestBuilder.addHeader("Authorization", "Bearer " + token);//添加请求头信息，服务器进行token有效性验证
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            }
//        };
//        builder.addInterceptor(headerInterceptor);