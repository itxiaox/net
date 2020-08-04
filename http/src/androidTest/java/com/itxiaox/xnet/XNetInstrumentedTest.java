package com.itxiaox.xnet;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.itxiaox.http.base.HttpCallback;
import com.itxiaox.http.base.HttpFactory;
import com.itxiaox.http.base.HttpLogger;
import com.itxiaox.http.base.HttpManager;
import com.itxiaox.http.base.HttpParams;
import com.itxiaox.http.okhttp.NetworkStateInterceptor;
import com.itxiaox.http.retrofit.RetrofitConfig;
import com.itxiaox.http.utils.Utils;

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
public class XNetInstrumentedTest {
    private static final String TAG = "XNetInstrumentedTest";
    String APP_ID = "wx880db3ff4529e9aa";
    String AppSecret = "b989124f9c581d12f76100da5f5064e2";
    Context appContext;
    String baseUrl = "https://www.wanandroid.com";
    private HttpManager httpManager;
    @Before
    public void useAppContext() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        Utils.init(appContext);
        //方法一
        //默认初始化
      httpManager = HttpFactory.init(appContext,baseUrl,HttpFactory.HttpManagerType.VOLLEY);
      //方法二，灵活的配置HttpConfig
//        init2();
    }


    /**
     * 自定义配置
     */
    private void init2(){
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
                .addInterceptor(new NetworkStateInterceptor())
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
//        String url = "http://www.wanandroid.com/wxarticle/chapters/json/";
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
        //
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
