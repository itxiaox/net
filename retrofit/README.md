# Retrofit封装库
[![](https://jitpack.io/v/itxiaox/net.svg)](https://jitpack.io/#itxiaox/net)
你的一个star，就是我分享的动力❤️。

----------

   >对retrofit 进行封装 ，提供一个更加简单易用的接口，在项目更加简单易用
   

## 一. 功能介绍
 

- 提供Http/Https请求，支持POST/GET
-   支持切换BaseUrl以及添加多个请求客户端（多个BaseUrl）

## 二. 使用方法

### 1. gradle 引用
 

 - 在根gradle中添加

	
	``` 

	allprojects {
		repositories {
			jcenter()
		   maven { url 'https://jitpack.io' }
		}
	}
	```

   

 -  在module 中添加依赖

	```
	implementation 'com.github.itxiaox:retrofit:1.0.4'
	```

### 2. 代码中使用：

 - 一般情况下可以采用默认的调用方式
 
    
	``` java
	 //初始一次，一般可以采用在Application的onCreate中进行调用
		 HttpManager.initClient(baseUrl,true);

	 //具体发送请求的
		wxapiService = HttpManager.create(WXAPIService.class);`
		wxapiService.getWXArticle().enqueue(...)
	```
    
-  但如果需要自己去配置相关请求设置，如重新设置timeout, interceptor，convertFactory, adapterFactory等，可以采用如下方式

	

``` java
//        添加日志拦截器
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
//                Log.d(TAG, "log:"+message);

                Logger.e(message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //添加通用的请求参数，放到heaader中
//        HeadersInterceptor headersInterceptor = new HeadersInterceptor.Builder()
//                .addHeaderParam("Content-Type","")
//                .addHeaderParam("Accept","application/json")
//                .build();
        HttpConfig httpConfig = new HttpConfig.Builder()
                .baseUrl(baseUrl)
 //               .addInterceptor(headersInterceptor)
                .addInterceptor(logInterceptor)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpManager.initClient(httpConfig);
```



## LICENSE

		Copyright 2018 itxiaox

		Licensed under the Apache License, Version 2.0 (the "License");
		you may not use this file except in compliance with the License.
		You may obtain a copy of the License at

		   http://www.apache.org/licenses/LICENSE-2.0

		Unless required by applicable law or agreed to in writing, software
		distributed under the License is distributed on an "AS IS" BASIS,
		WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
		See the License for the specific language governing permissions and
		limitations under the License.
