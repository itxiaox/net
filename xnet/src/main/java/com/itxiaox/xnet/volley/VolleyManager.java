package com.itxiaox.xnet.volley;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.itxiaox.xnet.base.HttpCallback;
import com.itxiaox.xnet.base.HttpConfig;
import com.itxiaox.xnet.base.HttpLogger;
import com.itxiaox.xnet.base.HttpManager;
import com.itxiaox.xnet.base.HttpParams;
import com.itxiaox.xnet.utils.ErrorCode;
import com.itxiaox.xnet.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyManager implements HttpManager {
    public  RequestQueue queue;
    private String baseUrl;
    public static VolleyManager getInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public void init(@NonNull Context context, @NonNull String baseUrl) {
        HttpLogger.d("init VolleyManager");
        this.baseUrl = baseUrl;
        queue = Volley.newRequestQueue(context.getApplicationContext());
    }

    // todo  自定义配置还未完善实现
    @Override
    public void init(Context context, HttpConfig httpConfig) {
        HttpLogger.d("init VolleyManager");
        this.baseUrl = httpConfig.getBaseUrl();
        queue = Volley.newRequestQueue(context.getApplicationContext());
    }

    @Override
    public <T> void get(@NonNull  String url, HttpParams httpParams, @NonNull  HttpCallback<T> httpCallback) {
        createRequest(url,httpParams, Request.Method.GET,httpCallback);
    }

    @Override
    public <T> void post(@NonNull  String url,@NonNull HttpParams httpParams, @NonNull HttpCallback<T> httpCallback) {
        createRequest(url,httpParams, Request.Method.POST,httpCallback);
    }

    private static class SingletonHolder {
        private static final VolleyManager instance = new VolleyManager();
    }

    private VolleyManager(){}


    /**
     * 返回Queue 可以进行队列操作
     * @return RequestQueue
     */
    public RequestQueue getQueue(){
       return queue;
    }

    private void createRequest(String url,HttpParams params,int method,HttpCallback httpCallback){
        JsonObjectRequest  request ;
        String absoulte_url = StringUtils.buffer(baseUrl,url);
        JSONObject jsonObject = null;
        if (Request.Method.GET == method){
             absoulte_url = params==null?absoulte_url:params.createGetUrl(baseUrl+url);
            HttpLogger.format("%s get-> url=%s",url,absoulte_url);
        }else {
            try {
                jsonObject = params.toJSONObject();
                HttpLogger.format("%s post-> url=%s \nparams %s",url,absoulte_url,jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        request = new JsonObjectRequest(absoulte_url,jsonObject,
                response -> {
                    String result = response.toString();
                    HttpLogger.format("%s onSuccess-> response",url);
                    HttpLogger.json(result);
                    httpCallback.onSuccess(result);
                }, error -> {
            httpCallback.onFailure(ErrorCode.REQUEST_FAIL,error.getMessage());
        }
        );
        request.setTag(url);
        queue.add(request);
    }

    public void cancelAll(String tag){
        queue.cancelAll(tag);
    }
}
