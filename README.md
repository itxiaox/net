# Android网络请求
[![](https://jitpack.io/v/itxiaox/net.svg)](https://jitpack.io/#itxiaox/net)

你的一个star，就是我分享的动力❤️。
----------


   >有关网络请求的一些常用功能实现，包含基本的http请求框架、多种方案替代的网络请求框架、文件下载上传框架(断点续传)等常用功能
   

## 一. 模块概览
 
  ###  1. Retrofit封装请求框架
  
  
- 提供Http/Https请求，支持POST/GET，满足实际项目中的网络请求开发需求。
-  支持切换BaseUrl以及添加多个请求客户端（多个BaseUrl）
   
   >  详见： [retrofit 封装框架](https://github.com/itxiaox/net/tree/master/retrofit)

   
  ###  2. 多方案网络请求框架
  
- 多种请求框架无缝切换，不影响上层的调用
-  支持Retrofit请求框架
-  支持Volley请求框架
-  支持OkHttp请求框架

>  详见：[多种替换方案的xnet网络请求框架](https://github.com/itxiaox/net/tree/master/http)

### 3. 下载上传框架
> 还在开发中。。。


## 二. 参考资料

### 2.1 博客

 - [Android网络请求库Okhttp、Volley和Retrofit的区别](https://www.jianshu.com/p/21fe87777d20?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation)
- [retrofit官网](http://square.github.io/retrofit/)
- 郭霖博客：[Android网络请求库 - Say hello to retrofit.](https://blog.csdn.net/ghost_programmer/article/details/52372065)

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
