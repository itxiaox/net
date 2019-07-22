package com.itxiaox.xnetdemo;


import android.util.Log;

import com.itxiaox.retrofit.Utils.JsonUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testJsonUtils(){
        String jsonString = "sffsf sfs  sfs g{\"bind_wechat\":0,\"expiration\":604800,\"token\":\"eyJhbGciOiJIUzUxMiIsImlhdCI6MTU2MjgyMTczMSwiZXhwIjoxNTYzNDI2NTMxfQ.eyJpZCI6NjN9.X2gYaAsQcs1ybFjIvapsfoesLWtIIOV6yUKzblhrlv2miXsTAaxtdwqowSF32iUggso73bUByfspIWHm-4DitQ\",\"usertype\":\"doctor\"}\n" +
                "2019-07-11 13:08:51.013 27089-27199/com.jishimed.pocus D/OkHttp: <-- END HTTP (235-byte body)";

        System.out.println(JsonUtils.isJson(jsonString));
        System.out.println(JsonUtils.formatJson(jsonString));
    }
}