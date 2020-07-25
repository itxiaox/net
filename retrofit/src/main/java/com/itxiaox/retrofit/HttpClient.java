package com.itxiaox.retrofit;

import com.itxiaox.retrofit.Utils.HttpsUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * HttpManager Http请求管理类
 */
public class HttpClient {

    private HttpConfig httpConfig;
    private Retrofit retrofit;

    public HttpClient(HttpConfig config){
        this.httpConfig = config;
        this.retrofit = newRetrofit();
    }

    public HttpClient(String baseUrl,boolean logger){
        HttpConfig httpConfig =  HttpConfig.createDefault(baseUrl, logger);
        this.httpConfig = httpConfig;
        this.retrofit = newRetrofit();
    }

    public  <T> T create(final Class<T> service){
        return this.retrofit.create(service);
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
