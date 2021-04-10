//package com.itxiaox.xnetdemo.retrofit;
//
//
//import android.content.Context;
//import android.os.Environment;
//import android.util.Log;
//
//
//import com.itxiaox.retrofit.DownLoadUtils;
//import com.itxiaox.retrofit.DownloadListener;
//import com.itxiaox.retrofit.HttpConfig;
//import com.itxiaox.retrofit.HttpManager;
//import com.orhanobut.logger.AndroidLogAdapter;
//import com.orhanobut.logger.FormatStrategy;
//import com.orhanobut.logger.Logger;
//import com.orhanobut.logger.PrettyFormatStrategy;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.concurrent.Executors;
//
//import okhttp3.ResponseBody;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//import static org.junit.Assert.assertEquals;
//
//
//public class RetrofitTest {
//
//    private static final String TAG = "RetrofitTest";
//    String baseUrl ;
//    private WXAPIService wxapiService;
//
//
//    @Before
//    public void init(){
////         baseUrl = "https://itxiaox.github.io";
//         baseUrl = "https://www.wanandroid.com";
//        //============方式一 默认配置========
//        //默认简单调用方式, 在Application#onCreate方法中调用
//        try{
//            HttpManager.initClient(baseUrl,true);
//        }catch(Exception e){
//            e.printStackTrace();
//            Log.i(TAG,"init "+e.getMessage());
//        }
//
//
////        //============方式二 灵活配置========
//////        添加日志拦截器
////        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
////            @Override
////            public void log(String message) {
//////                Log.d(TAG, "log:"+message);
////
////                Logger.e(message);
////            }
////        });
////        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
////        //添加通用的请求参数，放到heaader中
//////        HeadersInterceptor headersInterceptor = new HeadersInterceptor.Builder()
//////                .addHeaderParam("Content-Type","")
//////                .addHeaderParam("Accept","application/json")
//////                .build();
////
////       HttpConfig httpConfig = new HttpConfig.Builder()
////               .baseUrl(baseUrl)
//////               .addInterceptor(headersInterceptor)
////               .addInterceptor(logInterceptor)
////               .addConverterFactory(GsonConverterFactory.create())
////               .build();
////       HttpManager.init(httpConfig);
//    }
//
//    private void initLog() {
//        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(2)         // (Optional) How many method line to show. Default 2
//                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
////                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
//                .tag(TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
//                .build();
//
//        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
//    }
//
//    @Test
//    public void testRetrofit(){
//        wxapiService = HttpManager.create(WXAPIService.class);
//        wxapiService.getWXArticle().enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                  String result = response.body().string();
//                    System.out.printf("getWXArticle:"+result);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    System.out.printf("getWXArticle:"+e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                System.out.println("fail:"+t);
//            }
//        });
//    }
//
//
//
//
//
//
//}
