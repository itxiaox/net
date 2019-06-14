package com.itxiaox.xnet.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itxiaox.xnet.base.IRequestCallback;
import com.itxiaox.xnet.base.IRequestManager;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyRequestManager implements IRequestManager {
    public static RequestQueue queue;

    public static VolleyRequestManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final VolleyRequestManager instance = new VolleyRequestManager();
    }

    private VolleyRequestManager(){}

    public void init(Context context) {
        queue = Volley.newRequestQueue(context.getApplicationContext());
    }

    /**
     * 返回Queue 可以进行队列操作
     * @return RequestQueue
     */
    public RequestQueue getQueue(){
       return queue;
    }

    @Override
    public void get(String url, IRequestCallback requestCallback) {
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
            if(requestCallback!=null)
            requestCallback.onSuccess(response);
        }, error -> {
            if(requestCallback!=null)
                requestCallback.onFailure(error);
           }
        );
        request.setTag(url);
        queue.add(request);
    }

    @Override
    public void post(String url, String requestBodyJson, IRequestCallback requestCallback) {
        requestWithBody(url, requestBodyJson, requestCallback, Request.Method.POST);
    }

    @Override
    public void put(String url, String requestBodyJson, IRequestCallback requestCallback) {
        requestWithBody(url, requestBodyJson, requestCallback, Request.Method.PUT);
    }

    @Override
    public void delete(String url, String requestBodyJson, IRequestCallback requestCallback) {
        requestWithBody(url, requestBodyJson, requestCallback, Request.Method.DELETE);
    }

    /**
     * 封装带请求体的请求方法
     *
     * @param url             url
     * @param requestBodyJson Json string请求体
     * @param requestCallback 回调接口
     * @param method          请求方法
     */
    private void requestWithBody(String url, String requestBodyJson, final IRequestCallback requestCallback, int method) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(requestBodyJson);
        } catch (JSONException e) {
            e.printStackTrace();
            //// TODO: 2016/12/11 异常处理
        }
        JsonRequest<JSONObject> request = new JsonObjectRequest(method, url, jsonObject,
                response -> {
                    if(requestCallback!=null)
                    requestCallback.onSuccess(response != null ? response.toString() : null);
                },
                error -> {
                    if(requestCallback!=null)
                    requestCallback.onFailure(error);
                });
        request.setTag(url);
        queue.add(request);
    }

    public void cancelAll(String tag){
        queue.cancelAll(tag);
    }
}
