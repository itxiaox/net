# Retrofit封装库
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
    在module 中添加依赖

    implementation 'com.itxiaox:retrofit:0.1.0'

代码中使用：

 - 默认简单调用方式
 
    
     //初始一次，一般可以采用在Application的onCreate中进行调用
     
        RetrofitManager.init(baseUrl);
        //具体发送请求的
        wxapiService = RetrofitManager.getWebService(WXAPIService.class);`
        wxapiService.getWXArticle().enqueue(...)``
    
    
-   灵活配置, 可以使用默认的retrofit/okhttpclient, 也可以重新自己创建，

        OkHttpClient client = RetrofitManager.defaultClient().build();
        wxapiService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) .build()
                .create(WXAPIService.class);
        wxapiService.getWXArticle().enqueue(...)``
        

        //全部灵活配置
          OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
          clientBuilder.connectTimeout(30, TimeUnit.SECONDS);
          HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new       HttpLog());
          logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
          clientBuilder.addNetworkInterceptor(logInterceptor);
          wxapiService = new Retrofit.Builder().baseUrl(baseUrl)
                        .client(clientBuilder.build())
        //                .addConverterFactory(GsonConverterFactory.create())
          .build().create(WXAPIService.class);
		 
		 
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
