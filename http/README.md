# 多方案网络请求框架
[![](https://jitpack.io/v/itxiaox/net.svg)](https://jitpack.io/#itxiaox/net)

你的一个star，就是我分享的动力❤️。
----------


   >实现有多种替代解决方案的业务逻辑的封装，实现一键切换底层的请求方式（retrofit/volley/okhttp），而上层的业务逻辑不做任何改变。可以在实际项目中可以无缝的切换网络请求框架，以便充分利用各种框架的特点。
   

## 一. 功能介绍
 

- 多种请求框架无缝切换，不影响上层的调用
-  支持Retrofit请求框架
-  支持Volley请求框架
-  支持OkHttp请求框架

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

	``` gradle
	  implementation 'com.github.itxiaox.net:http:1.0.6'
	```

### 2. 代码中使用：
##### 2.1 初始化

#### a. 简单调用，采用默认的初始化方法：



   在Application的onCreate方法中进行初始化
   

``` javascript
//  OKHTTP:使用okhttp实现请求；VOLLEY：使用volley实现请求；RETROFIT：使用retrofit实现请求
	HttpManager  httpManager = HttpFactory.init(appContext,baseUrl,HttpFactory.HttpManagerType.VOLLEY);
```
#### b. 高级调用，采用自定义配置的方法进行调用：

在Application的onCreate方法中，

```
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

```

#### 2.2 在具体的网络请求处：
```
     /**
     * 测试Get请求，https请求
     */
    @Test
    public void testGet(){
        String url = "/wxarticle/chapters/json/";
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
     * 测试Post请求，https请求
     */
    @Test
    public void testPost(){
        String url = "/user/login";
        HttpParams httpParams = new HttpParams();
        httpParams.addParams("username","张三");
        httpParams.addParams("password","123456");

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
