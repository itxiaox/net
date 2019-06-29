package com.itxiaox.xnetdemo.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WXAPIService {
    @GET("/device/ticket")
    Call<ResponseBody> getTicket();
    @GET("/device/user")
    Call<ResponseBody> getUser(@Query("code") String authCode);
    @GET("/device/scene_qrcode")
    Call<ResponseBody> getQRCode(@Query("org_guid") String org_guid);
}
