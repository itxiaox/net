package com.itxiaox.xnetdemo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface WXAPIService2 {
    @GET("/ITPages/api/userinfo.json")
    Call<ResponseBody>  getUserInfo();

}
