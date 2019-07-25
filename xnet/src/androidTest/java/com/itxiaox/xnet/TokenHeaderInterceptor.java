package com.itxiaox.xnet;

import android.text.TextUtils;



import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 应用中，实现token的自定义TokenHeaderInterceptor
 *
 * 原理解析
 * 一般token的使用整体逻辑是这样的，登陆接口获取一个token 和一个刷新token用的refresh_token,
 * 在接下来的token请求中，所有的接口都需要携带token请求，以便服务端来验证请求的来源是合法的。
 *
 * 这个token是有时效性的，服务端会给他一个时效性，一般设为2小时，在这个时间段内，
 * 这个token是有效的，可以请求成功，而过了这个时间段，这个token就失效了。
 * 这时候需要我们使用refresh_token 请求刷新token的接口来获取新的token 和 refresh_token，
 * 这样就可以源源不断的保持我们持有的token是有效合法的
 *
 *
 * 设计思想
 * 由于token过期这个事情应该是让用户无察觉的，所以我们android端需要偷偷的跑在后台，
 * 换句话说就是，在请求接口的时候，如果返回了TOKEN过期异常之后，我们需要立即再请求刷新接口，
 * 然后再使用新TOKEN 重新请求该接口，那么可以想想，这整个逻辑，如果每个接口都需要这么写，
 * 意味着我们会有很大的工作量，那么这是不科学的，所以我们需要使用一个拦截器，那样就很简单了
 *
 * 接下来我放上我的代码，并根据代码解释下
 * ---------------------
 * 作者：xiao_sier
 * 来源：CSDN
 * 原文：https://blog.csdn.net/xiao_sier/article/details/72764780
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 *
 *
 * 参考参考，实现方法可以参考pocus实际项目中的使用
 * 在OktttpClient.Builder创建的时候
 *  builder.addInterceptor(new TokenHeaderInterceptor());
 */
public class TokenHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
//        String token = MainDataManager.getInstance().getToken();
        String token = "";
        //从sp中取得保存的token
//        String token = (String) SPUtils.get(Constants.KEY_TOKEN,"");

        Request request = chain.request();
        if(TextUtils.isEmpty(token)){//还没登录的时候，执行当前原始请求，如登录接口
            Response response = chain.proceed(request);
            return  response;
        }else {
            //获取本地的token，添加到每个请求的header中
            Request orginalRequest = chain.request()
                    .newBuilder()
                    .header("Authorization", "Basic "+token)
                    .build();
            Response response = chain.proceed(orginalRequest);
//            LogUtils.d("response.code=" + response.code());

            //根据和服务端的约定判断token过期
            if (isTokenExpired(response)) {
//                LogUtils.d("自动刷新Token,然后重新请求数据");
                //同步请求方式，获取最新的Token
                String newToken = getNewToken();
                if(TextUtils.isEmpty(newToken)){//获取新的的token失败，跳转到登录界面，重新登录
                    //todo, 跳转到登录界面
//                    LogUtils.d("调转到登录");
                }
                //保存sp中
//                SPUtils.put(Constants.KEY_TOKEN,newToken);
                //使用新的Token，创建新的请求， 使用新的token进行重定向
                Request newRequest = chain.request()
                        .newBuilder()
                        .header("Authorization", "Basic "+token)
                        .build();
                //重新请求
                return chain.proceed(newRequest);
            }
            return response;
        }
    }
    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        if (response.code() == 301 || response.code()==401) {
            return true;
        }
        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken() throws IOException {
        // 通过获取token的接口，同步请求接口
        //todo 调用刷新token接口
        String newToken = "";
        return newToken;
    }
}