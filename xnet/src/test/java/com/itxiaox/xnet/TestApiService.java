package com.itxiaox.xnet;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

@SuppressWarnings("ALL")
public interface TestApiService {


    @GET("/cgi-bin/token")
    Call<ResponseBody> getAccess_token(
            @Query("grant_type")
                    String grant_type,
            @Query("appid")
                    String app_id,
            @Query("secret")
                    String secret);

}
