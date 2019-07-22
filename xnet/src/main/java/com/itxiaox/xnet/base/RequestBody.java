package com.itxiaox.xnet.base;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {

    /**
     * 接口名字
     */
    public String service_name;
    private Map<String,String> param = new HashMap<>();
    public void addParams(String key,String value){
        param.put(key, value);
    }



    public Map<String,String> getParams(){
        return param;
    }



}
