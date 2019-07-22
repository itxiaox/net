//package com.itxiaox.xnet.okhttp;
//
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//
//import com.itxiaox.xnet.base.IRequestCallback;
//import com.itxiaox.xnet.base.IRequestManager;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Interceptor;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//
//public class OkHttpRequestManager implements IRequestManager {
//    private static final int DEFAULT_TIME_OUT = 30000;
//    private String token = "token";
//    public static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
//    private OkHttpClient okHttpClient;
//    private Handler handler= new Handler(Looper.getMainLooper()){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//
//        }
//    };
//
//    public static OkHttpRequestManager getInstance() {
//        return SingletonHolder.INSTANCE;
//    }
//
//
//    private static class SingletonHolder {
//        private static final OkHttpRequestManager INSTANCE = new OkHttpRequestManager();
//    }
//
//
//    private OkHttpRequestManager() {
//    }
//
//    @Override
//    public void init(Context context,String baseUrl) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接 超时时间
//        builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
//        builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//读操作 超时时间
//        builder.retryOnConnectionFailure(true);//错误重连
//        // 添加公共参数拦截器
////        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
////                .addHeaderParam("userName", "")//添加公共参数
////                .addHeaderParam("device", "")
////                .build();
////        builder.addInterceptor(basicParamsInterceptor);
//        //项目中设置头信息
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
////        builder.addInterceptor(headerInterceptor);
//
//        okHttpClient = builder.build();
//
//    }
//    @Override
//    public void get(String url, IRequestCallback requestCallback) {
//        Request request = new Request.Builder()
//                .url(url)
//                .get()
//                .build();
//        addCallBack(requestCallback, request);
//    }
//
//    @Override
//    public void post(String url, Map<String,String> pa, IRequestCallback requestCallback) {
//        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        addCallBack(requestCallback, request);
//    }
//
//    @Override
//    public void put(String url, String requestBodyJson, IRequestCallback requestCallback) {
//        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);
//        Request request = new Request.Builder()
//                .url(url)
//                .put(body)
//                .build();
//        addCallBack(requestCallback, request);
//    }
//
//    @Override
//    public void delete(String url, String requestBodyJson, IRequestCallback requestCallback) {
//        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);
//        Request request = new Request.Builder()
//                .url(url)
//                .delete(body)
//                .build();
//        addCallBack(requestCallback, request);
//    }
//
//    private void addCallBack(final IRequestCallback requestCallback, Request request) {
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(final Call call, final IOException e) {
//                e.printStackTrace();
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        requestCallback.onFailure(e);
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(final Call call, final Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    final String json = response.body().string();
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            requestCallback.onSuccess(json);
//                        }
//                    });
//                } else {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            requestCallback.onFailure(new IOException(response.message() + ",url=" + call.request().url().toString()));
//                        }
//                    });
//                }
//            }
//        });
//    }
//}