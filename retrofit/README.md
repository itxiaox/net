# Retrofit封装库
[ ![Download](https://api.bintray.com/packages/itxiaox/maven/retrofit/images/download.svg?version=0.5.0) ](https://bintray.com/itxiaox/maven/retrofit/0.5.0/link)

    对retrofit 进行封装 ，在项目更加简单易用

# 使用方法
gradle 引用
    在根gradle中添加

    allprojects {
        repositories {
            jcenter()
            maven {
                url 'https://dl.bintray.com/itxiaox/maven/'
            }
        }
    }

    //在module 中添加依赖
    implementation 'com.itxiaox:retrofit:0.5.0'

代码中使用：

 - 在一般情况下，采用简单的默认配置即可
 
```
 //初始一次，一般可以采用在Application的onCreate中进行调用
 HttpManager.init(baseUrl,true)
//具体发送请求的
 wxapiService = HttpManager.create(WXAPIService.class);`
 wxapiService.getWXArticle().enqueue(...)``
```

- 但如果需要重新设置timeout,interceptor，convertAdapter等，可以自己重新配置HttpConfig。

```
//初始一次，一般可以采用在Application的onCreate中进行调用

//添加日志拦截器
HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
    @Override
  public void log(String message) {
        Log.d(TAG, "log:"+message); //在这里可以统一对请求的各项参数进行处理，
  }
});
logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

//需要在headers中统一添加共用参数
HeadersInterceptor headersInterceptor = new HeadersInterceptor.Builder()
              .addHeaderParam("Content-Type","")
              .addHeaderParam("Accept","application/json")
               .build();
//配置htpp请求
HttpConfig httpConfig = new HttpConfig.Builder()
  .baseUrl(baseUrl)
  .addInterceptor(headersInterceptor)
  .addInterceptor(logInterceptor)
  .addConverterFactory(GsonConverterFactory.create())
  .build();
 //初始化
 HttpManager.init(httpConfig);
```


 # LICENSE

    Copyright 2018 Xiaox

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.