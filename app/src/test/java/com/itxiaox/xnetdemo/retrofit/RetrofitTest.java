package com.itxiaox.xnetdemo.retrofit;


import com.itxiaox.retrofit.RetrofitServiceManager;

import org.junit.Before;
import org.junit.Test;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitTest {

    String baseUrl ;
    private WXAPIService wxapiService;

    @Before
    public void init(){
         baseUrl = "http://wx.jishimed.com/";
         baseUrl = "http://wx.jishimed.com/";
         wxapiService = new RetrofitServiceManager.Builder().baseUrl(baseUrl).builder().create(WXAPIService.class);
    }
    @Test
    public void testRetrofit(){

        wxapiService.getTicket().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("success:"+response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("fail:"+t);
            }
        });

    }
}
