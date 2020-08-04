package com.itxiaox.http.base;

import com.itxiaox.http.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpParams {

    private Map<String,String> param = new HashMap<>();
    public void addParams(String key,String value){
        param.put(key, value);
    }
    public Map<String,String> getParams(){
        return param;
    }


    /**
     * 将参数转换为json字符串
     * @return 返回json字符串
     * @throws JSONException
     */
    public String toJSON () throws JSONException {
        Set<Map.Entry<String, String>> entrySets = param.entrySet();
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String,String> map:
             entrySets) {
            jsonObject.put(map.getKey(),map.getValue());

        }
        return jsonObject.toString();
    }
    /**
     * 将参数转换为JSONObject
     * @return 返回json对象
     * @throws JSONException
     */
    public JSONObject toJSONObject () throws JSONException {
        Set<Map.Entry<String, String>> entrySets = param.entrySet();
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String,String> map:
                entrySets) {
            jsonObject.put(map.getKey(),map.getValue());

        }
        return jsonObject;
    }

    public  boolean isEmpty(){
        return param==null||param.isEmpty();
    }


    /**
     * 根据请求参数拼接Get请求的URL
     * @param url baseUrl+url, 请求地址+路径，参数之前的全部内容，
     * @return 返回Get请求的全部路径
     */
    public String createGetUrl(String url){
        String value = "";
        if (!param.isEmpty()) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                String mapKey = entry.getKey();
                String mapValue = entry.getValue();
                String span = value.equals("") ? "" : "&";
                String part = StringUtils.buffer(span, mapKey, "=", mapValue);
                value = StringUtils.buffer(value, part);
            }
        }
        return StringUtils.isEmpty(value)?url:StringUtils.buffer(url, "?", value);
    }

}
