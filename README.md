# 有关网络的研究学习

## retrofit 封装框架

volley vs okhttp vs retrofit
https://www.jianshu.com/p/21fe87777d20?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation


retrofit官网：http://square.github.io/retrofit/
郭霖博客：Android网络请求库 - Say hello to retrofit.
https://blog.csdn.net/ghost_programmer/article/details/52372065

实现有多种替代解决方案的业务逻辑



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

    implementation 'com.itxiaox:xnet:0.1.0'
