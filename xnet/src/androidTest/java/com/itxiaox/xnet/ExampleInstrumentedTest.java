package com.itxiaox.xnet;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.itxiaox.xnet.base.IRequestCallback;
import com.itxiaox.xnet.base.IRequestManager;
import com.itxiaox.xnet.base.RequestFactory;
import com.itxiaox.xnet.retrofit.RetrofitServiceManager;
import com.itxiaox.xnet.test.TestApiService;

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
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.xiao.base.xnet.test", appContext.getPackageName());
    }

    @Test
    public void testRequest(){
        IRequestManager iRequestManager = RequestFactory. getRequestManager();
        iRequestManager.init(InstrumentationRegistry.getTargetContext());

        String url = "http://www.wanandroid.com/tools/mockapi/9932/getUser";
        iRequestManager.get(url, new IRequestCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response="+response);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("throwable="+throwable);
            }
        });
    }

//        @Test
//        public void  tetGetToken(){
//            service = new RetrofitServiceManager.Builder()
//                    .baseUrl("https://api.weixin.qq.com/")
//                    .builder()
//                    .create(TestApiService.class);
//            Call<ResponseBody> call = service.getAccess_token("client_credential",APP_ID,AppSecret);
//
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    try {
//                        Log.i(TAG, "onResponse: "+response.body().string());
//                        System.out.println("onResponse: "+response.body().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    System.out.println("onResponse: "+t);
//                }
//            });
//
//        }
}
