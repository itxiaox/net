package com.itxiaox.xnetdemo.http;


import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.itxiaox.http.base.HttpCallback;
import com.itxiaox.http.base.HttpFactory;
import com.itxiaox.http.base.HttpManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.junit.Before;
import org.junit.Test;

public class HttpTest {

    private static final String TAG = "RetrofitTest";
    String baseUrl;
    private Context appContext;

    HttpManager httpManager;

    @Test
    public void useAppContext() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Before
    public void init() {
//      baseUrl = "https://itxiaox.github.io";
        baseUrl = "https://www.wanandroid.com";
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        HttpFactory.init(appContext, baseUrl, HttpFactory.HttpManagerType.OKHTTP);
        httpManager = HttpFactory.getHttpManager();
    }

    private void initLog() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    @Test
    public void testRetrofit() {

        String url = "/wxarticle/chapters/json";
        httpManager.get(url, null, new HttpCallback<String>() {
            @Override
            public void onSuccess(String response) {
//                String result = response;
                Logger.d(response);
            }

            @Override
            public void onFailure(int errcode, String errmsg) {
                Logger.d("code:"+errcode+";error:"+errmsg);
            }
        });
    }

    @Test
    public void testRetrofit2() {
//        StringBuffer buffer = new StringBuffer();
//        String baseUrl = "https://www.wanandroid.com";
//        WXAPIService wxApiService = HttpManager.createWebService(WXAPIService.class,baseUrl,
//                HttpClientType.MULTIPLE);
//        buffer.append("===="+baseUrl+"====");
//        wxApiService.getWXArticle().enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    String result = response.body().string();
//                    Logger.i("onResponse result:"+result);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Logger.i("onResponse exception:"+e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Logger.d("fail:"+t);
//            }
//        });
//
//        String baseUrl2 = "https://itxiaox.github.io";
//        WXAPIService wxapiService2 = HttpManager.createWebService(WXAPIService.class,baseUrl,
//                HttpClientType.MULTIPLE);
//        buffer.append("===="+baseUrl2+"====");
//        wxapiService2.getWXArticle().enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    String result = response.body().string();
//                    Logger.i("onResponse exception:"+result);
////                    Logger.json(result);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Logger.i("onResponse exception:"+e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Logger.d("onResponse fail:"+t);
//            }
//        });

    }


    @Test
    public void testDownload() {
//        String url = "/16891/89E1C87A75EB3E1221F2CDE47A60824A.apk?fsname=com.snda.wifilocating_4.2.62_3192.apk&csr=1bbd";
//        String path = Environment.getDataDirectory().getAbsolutePath()+File.separator+"net"+File.separator+"test.apk";
//        Log.d(TAG, "testDownload: path="+path);
//        download(url, path, new DownloadListener() {
//            @Override
//            public void onStart() {
//                Log.d(TAG, "onStart: ");
//            }
//
//            @Override
//            public void onProgress(int progress) {
//
//                Log.d(TAG, "onProgress: "+progress);
//            }
//
//            @Override
//            public void onFinish(String path) {
//
//                Log.d(TAG, "onFinish: "+path);
//            }
//
//            @Override
//            public void onFail(String errorInfo) {
//
//                Log.d(TAG, "onFail: "+errorInfo);
//            }
//        });
    }

//    public void download(String url,String path, DownloadListener downloadListener) {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://imtt.dd.qq.com")
//                //通过线程池获取一个线程，指定callback在子线程中运行。
//                .callbackExecutor(Executors.newSingleThreadExecutor())
//                .build();
//
//        DownloadService service = retrofit.create(DownloadService.class);
////        String url = "";
//        service.download(url).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                //将Response写入到从磁盘中，详见下面分析
//                //注意，这个方法是运行在子线程中的
//                DownLoadUtils.writeResponseToDisk(path, response, downloadListener);
////                    Logger.json(result);
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                System.out.println("fail:"+t);
//            }
//        });
//    }


}
