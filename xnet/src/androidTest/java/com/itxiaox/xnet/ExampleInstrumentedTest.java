package com.itxiaox.xnet;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.itxiaox.xnet.base.IRequestCallback;
import com.itxiaox.xnet.base.IRequestManager;
import com.itxiaox.xnet.base.RequestBody;
import com.itxiaox.xnet.base.RequestFactory;
import com.itxiaox.xnet.utils.Utils;
import com.orhanobut.logger.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@SuppressWarnings("ALL")
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumentedTest";
    String APP_ID = "wx880db3ff4529e9aa";
    String AppSecret = "b989124f9c581d12f76100da5f5064e2";
    Context appContext;
    @Before
    public void useAppContext() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        Utils.init(appContext);
        String baseUrl = "https://www.wanandroid.com";
        IRequestManager iRequestManager = RequestFactory. getRequestManager();
        iRequestManager.init(appContext,baseUrl);
    }

    /**
     * 测试retrofit 封装，Get请求，https请求
     */
    @Test
    public void testRetrofitGet(){
        IRequestManager iRequestManager = RequestFactory. getRequestManager();
        String url = "/wxarticle/chapters/json/";
//        String url = "http://www.wanandroid.com/tools/mockapi/9932/getUser";
        iRequestManager.get(url, null, new IRequestCallback<String>() {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, "onSuccess: "+response);
            }

            @Override
            public void onFailure(int errcode, String errmsg) {
                Log.i(TAG, "onFailure: "+errmsg);
            }
        });
    }

    @Test
    public void testRetrofitPost(){
        IRequestManager iRequestManager = RequestFactory. getRequestManager();
        String url = "/user/login";


        RequestBody paramBody = new RequestBody();
        paramBody.addParams("username","itxiaox");
        paramBody.addParams("password","xiao10034263");

        iRequestManager.post(url, paramBody, new IRequestCallback<String>() {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, "onSuccess: "+response);
            }

            @Override
            public void onFailure(int errcode, String errmsg) {
                Log.i(TAG, "onFailure: "+errmsg);
            }
        });
    }







}
