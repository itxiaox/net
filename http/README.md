# XNet网络框架
[ ![Download](https://api.bintray.com/packages/itxiaox/maven/xnet/images/download.svg?version=0.5.0-alpha) ](https://bintray.com/itxiaox/maven/xnet/0.5.0-alpha/link)
------------

实现有多种替代解决方案的业务逻辑的封装，实现一键切换底层的请求方式（retrofit/volley/okhttp），而上层的业务逻辑不做任何改变

## 使用方法

- 添加依赖
	```
	allprojects {
		repositories {
			jcenter()
			maven {
				url 'https://dl.bintray.com/itxiaox/maven/'
			}
		}
	}
	在module 中添加依赖
	implementation 'com.itxiaox:xnet:0.5.0-alpha'
	```

**初始化**

1. 简单调用，采用默认的初始化方法：
在Application的onCreate方法中进行初始化
```
HttpManager  httpManager = HttpFactory.init(appContext,baseUrl,HttpFactory.HttpManagerType.VOLLEY);//  OKHTTP:使用okhttp实现请求；VOLLEY：使用volley实现请求；RETROFIT：使用retrofit实现请求
```

2.高级调用，采用自定义配置的方法进行调用
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

**在具体的网络请求处：**
```
     /**
     * 测试Get请求，https请求
     */
    @Test
    public void testGet(){
        String url = "/wxarticle/chapters/json/";
//        String url = "http://www.wanandroid.com/tools/mockapi/9932/getUser";
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
    ```

# LICENSE

    Copyright 2019 Xiaox

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 
 