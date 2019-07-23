package com.itxiaox.retrofit;

import com.itxiaox.retrofit.Utils.HttpsUtils;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * HttpManager Http请求管理类
 */
public class HttpManager {

    private static HttpManager instance;

    private HttpConfig httpConfig;
    private Retrofit retrofit;

    private HttpManager(HttpConfig config){
        this.httpConfig = config;
        this.retrofit = newRetrofit();
    }


    /**
     * 采用默认配置的初始化
     * @param baseUrl base_url
     * @param logger 是否显示日志输出
     */
    public static void init(String baseUrl,boolean logger){
        init(HttpConfig.createDefault(baseUrl, logger));
    }


    /**
     * 自定义配置
     * @param config 自定义配置
     */
    public static void init(HttpConfig config){
        if (instance == null){
            instance = new HttpManager(config);
        }
    }

    public static HttpManager getInstance(){
        return instance;
    }

    public static <T> T create(final Class<T> service){
        if (instance == null){
            throw new  NullPointerException("HttpManager not init");
        }
        return instance.retrofit.create(service);
    }

    private Retrofit newRetrofit(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(httpConfig.getBaseUrl());
        // 设置转换器
        List<Converter.Factory> converterFactories = httpConfig.getConverterFactories();
        if (converterFactories != null && !converterFactories.isEmpty()) {
            for (Converter.Factory factory : converterFactories) {
                builder.addConverterFactory(factory);
            }
        }
        // 设置适配器
        List<CallAdapter.Factory> adapterFactories = httpConfig.getAdapterFactories();
        if (adapterFactories != null && !adapterFactories.isEmpty()) {
            for (CallAdapter.Factory factory : adapterFactories) {
                builder.addCallAdapterFactory(factory);
            }
        }
        //设置okhttp
        OkHttpClient httpClient = newOkHttpClient();
        return builder.client(httpClient).build();
    }

    private OkHttpClient newOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(httpConfig.getConnectTimeoutMilliseconds(), TimeUnit.MILLISECONDS);
        //添加Https支持
        //方法一
        //信任所有服务器地址,这里直接返回true也是信任了所有证书
//        builder.hostnameVerifier((s, sslSession) -> {
//            //设置为true
//            return true;
//        });
        //方法二
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactoryUnsafe();
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        builder.hostnameVerifier(HttpsUtils.getHostnameVerifier());

        builder.addInterceptor(new TokenInterceptor());//添加获取token的拦截器,token身份校验，Authorization

//        CookieManager cookieManager = new CookieManager();
//        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
//        builder.cookieJar(new CookieJar() {
//            @Override
//            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//
//            }
//
//            @Override
//            public List<Cookie> loadForRequest(HttpUrl url) {
//                return null;
//            }
//        });

//        builder.authenticator(new Authenticator() {
//            @Override
//            public Request authenticate(Route route, Response response) throws IOException {
//                return null;
//            }
//        });

        //设置拦截器
        List<Interceptor> interceptors = httpConfig.getInterceptors();
        if (interceptors != null && !interceptors.isEmpty()) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        return builder.build();
    }
}
