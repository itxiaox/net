package com.itxiaox.xnet;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.itxiaox.xnet.base.HttpCallback;
import com.itxiaox.xnet.base.HttpLogger;
import com.itxiaox.xnet.base.HttpManager;
import com.itxiaox.xnet.base.HttpParams;
import com.itxiaox.xnet.base.HttpFactory;
import com.itxiaox.xnet.okhttp.OkHttpConfig;
import com.itxiaox.xnet.retrofit.RetrofitConfig;
import com.itxiaox.xnet.utils.Utils;
import com.orhanobut.logger.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

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

    private HttpManager httpManager;
    @Before
    public void useAppContext() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        Utils.init(appContext);
        String baseUrl = "https://www.wanandroid.com";

        //方法一
        //默认初始化
//      httpManager = HttpFactory.init(appContext,baseUrl,HttpFactory.HttpManagerType.OKHTTP);

        //方法二
        //自定义HttpConfig配置初始化
      //  添加日志拦截器
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
//                Log.d(TAG, "log:"+message);

                HttpLogger.d("HttpLoggingInterceptor="+message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


//        OkHttpConfig okHttpConfig = new OkHttpConfig.Builder().baseUrl(baseUrl)
//                .connectTimeoutMilliseconds(5000)
//                .addInterceptor(logInterceptor).build();
//        httpManager = HttpFactory.init(appContext,okHttpConfig,HttpFactory.HttpManagerType.OKHTTP);


        RetrofitConfig retrofitConfig = new RetrofitConfig.Builder()
                .baseUrl(baseUrl)
                .connectTimeoutMilliseconds(5000)
                .addInterceptor(logInterceptor)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        httpManager = HttpFactory.init(appContext,retrofitConfig,HttpFactory.HttpManagerType.RETROFIT);
    }

    /**
     * 测试retrofit 封装，Get请求，https请求
     */
    @Test
    public void testGet(){
        String url = "/wxarticle/chapters/json/";
//        String url = "http://www.wanandroid.com/tools/mockapi/9932/getUser";
        httpManager.get(url, null, new HttpCallback<String>() {
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

    /**
     * 测试retrofit 封装，Post请求，https请求
     */
    @Test
    public void testPost(){
        String url = "/user/login";
        HttpParams httpParams = new HttpParams();
        httpParams.addParams("username","itxiaox");
        httpParams.addParams("password","xiao10034263");

        httpManager.post(url, httpParams, new HttpCallback<String>() {
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
