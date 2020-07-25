package com.itxiaox.xnetdemo.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface WXAPIService {
//    @GET("/device/ticket")
//    Call<ResponseBody> getTicket();
//    @GET("/device/user")
//    Call<ResponseBody> getUser(@Query("code") String authCode);
//    @GET("/device/scene_qrcode")
//    Call<ResponseBody> getQRCode(@Query("org_guid") String org_guid);

    @GET("/ITPages/api/userinfo.json")
    Call<ResponseBody>  getUserInfo();
//    https://wanandroid.com/wxarticle/chapters/json
    @GET("/wxarticle/chapters/json")
    Call<ResponseBody> getWXArticle();

    //需要注意的是要添加@Streaming注解。

    /**
     * 默认情况下，Retrofit在处理结果前会将服务器端的Response全部读进内存。如果服务器端返回的是一个非常大的文件，则容易发生oom
     * 。使用@Streaming的主要作用就是把实时下载的字节就立马写入磁盘，而不用把整个文件读入内存。
     * @param url
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url);
}
