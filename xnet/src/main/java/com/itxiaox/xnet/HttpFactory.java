//package com.itxiaox.xnet;
//
//import com.itxiaox.xnet.base.IRequestManager;
//import com.itxiaox.xnet.okhttp.OkHttpRequestManager;
//import com.itxiaox.xnet.retrofit.RetrofitManager;
//import com.itxiaox.xnet.volley.VolleyRequestManager;
//
//import static com.itxiaox.xnet.HttpFactory.HttpRequestType.*;
//
//public class HttpFactory {
//    private static IRequestManager requestManager;
//    HttpRequestType httpRequestType;
//
//    String  baseUrl = "";
//    public enum HttpRequestType{
//        OKHTTP,VOLLEY,RETROFIT
//    }
//
//    public  static IRequestManager createRequest(HttpRequestType httpRequestType ){
//        if(requestManager==null){
//            synchronized (HttpFactory.class){
//                if(requestManager==null){
//                    switch (httpRequestType ){
////                        case OKHTTP:
////                            requestManager = OkHttpRequestManager.getInstance();
////                            break;
//                        case VOLLEY:
//                            requestManager = VolleyRequestManager.getInstance();
//                            break;
//                        case RETROFIT:
//                            requestManager = RetrofitManager.getInstance();
//                            break;
//                    }
//                }
//            }
//        }
//       return requestManager;
//    }
//
//}
