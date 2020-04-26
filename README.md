<h1 id="donate">遇见</h1>

![封面](https://git.imooc.com/coding-390/Meet/raw/master/Class/Img/Meet.jpg)

项目中除了Bmob的私有空间是需要收费的，其他都是免费的，而私有空间主要的作用是用来上传头像和文件的，所以各位可以使用我的Key验证此功能，
但是尽量不要直接使用我的Key，因为容量和请求次数也是有限的，在源码根目录的Class中包含了PPT和截图，如果在课程中碰到问题可以查阅本篇的
问题答疑或者在课程交流群中艾特老师即可.

<h2 id="donate">KEY</h2>

:zap:**课程中所使用到的第三方平台的Key,只作为参考,建议自行申请**

- Bmob Key：f8efae5debf319071b44339cf51153fc
- 融云 Key：k51hidwqk4yeb
- 融云 Secret：os83U32SrAG
- 高德地图 Key：abde3c5f58d7dd9a762019906cef613e
- 高德地图 Web Key：389bc08b815e3146bfd1e45fd7f47fc5
- 讯飞 Key：5b18db70

<h2 id="donate">如何获取SHA1</h2>

:zap:**高德地图申请Key的时候所需要的SHA1获取方式**

- 1.win + R 打开命令行
- 2.输入cmd 打开了终端
- 3.cd .android/
- 4.keytool -v -list -keystore debug.keystore
- 5.密码：android

<h2 id="donate">问题答疑</h2>

:zap:**实时更新同学们在实战学习中碰到的问题及解决办法**

**1.为什么星球View会有卡顿呢？**

这个是因为我们的头像上传的时候直接上传了原图，然后加载的也是原图，所以图片都很大，正确的做法是头像上传需要裁减，并且加载图片需要压缩，可以参考源码,我做了优化

**2.加载数据为什么会慢?**

个人应该不会有这种情况，Demo就会，因为带宽不够，Bmob最多并发2000，毕竟是个人免费版本。

**3.时间转换类为什么少了8个小时?**

老师当时没有考虑到时区的问题，使用中国时区的话这个差值应该是28800000ms，所以在计算小时的实现需要加上少了的时区

```java
long hours = ((ms + 28800000) % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
```

**4.为什么文件读取没有权限？**

Android6.0运行时权限老师放在后面讲了，如果没有权限可以点击 设置 - 应用管理 - 自己的App - 权限 打开所有权限

**5.LogUtils无法写入日志到SD卡**

收到这个问题其实还是有点惊讶的，因为我调试的时候是好的，然而再调试一次，发现就有问题了，提示我找不到文件，后来才发现是小米手机的一个坑

1.直接在设置中打开权限是无法写入的

2.在代码中requestPermission则可以写入

3.我优化了写入的这段代码，传送门[LogUtils](https://git.imooc.com/coding-390/Meet/src/master/framework/src/main/java/com/liuguilin/framework/utils/LogUtils.java)

**6.选择头像上传裁剪无反应**

这是某些机型的适配问题，我已经对源码进行了优化，可查阅源码。传送门[FileHelper](https://git.imooc.com/coding-390/Meet/src/master/framework/src/main/java/com/liuguilin/framework/helper/FileHelper.java)

**7.源码为什么无法使用IM以及音视频通话功能？**

由于App使用的人比较多，老师的额度已经用光了，请自行替换自用的Key进行体验

替换方法：

IMLib/AndroidManifest.xml/ 中的 RONG_CLOUD_APP_KEY 替换成自己的Key

**8.为什么音乐播放完成,Handler还在继续回调进度？**

[问题地址,点击直达](http://coding.imooc.com/learn/questiondetail/153063.html)

**9.Bmob功能失效**

这是由于Bmob最新的策略，账户需要绑定备案域名才能访问，如果自己有已经备案的域名，老师可以教你如何绑定，如果没有，可以使用我的Bmob Key 以及 私有地址

**10.Cleartext HTTP traffic to xxx not permitted by network security policy**

这是Android系统针对于Https的限制，解决办法有两个：

第一种方法：

在res/xml下创建文件network_security_config.xml,内容如下

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>
```

并在清单文件的Application根节点中使用:

```xml
android:networkSecurityConfig="@xml/network_security_config"
```

可查阅源码
 
第二种方法：

直接在清单文件Application根节点中使用

```xml
android:usesCleartextTraffic="true"
```

<h2 id="donate">相关链接</h2> 

:zap:**实战课程中所引用的链接地址**

- [AndroidX](https://developer.android.google.cn/jetpack/androidx)
- [Bmob](https://www.bmob.cn/)
- [融云官网](https://www.rongcloud.cn/)
- [IM API DOC](https://www.rongcloud.cn/docs/api/android/imlib/index.html)
- [音视频 API DOC](https://www.rongcloud.cn/docs/api/android/rtclib/index.html)
- 适配刘海屏
    - [OPPO](https://open.oppomobile.com/wiki/doc#id=10159)
    - [VIVO](https://dev.vivo.com.cn/documentCenter/doc/135)
    - [小米](https://dev.mi.com/console/doc/detail?pId=1160)
    - [小米水滴屏幕](https://dev.mi.com/console/doc/detail?pId=1293)
- [Android M](https://developer.android.google.cn/about/versions/marshmallow/android-6.0-changes?hl=zh_cn)
- [3D 星球View](https://github.com/misakuo/3dTagCloudAndroid)
- [DatePicker](https://github.com/AigeStudio/DatePicker)
- [CircleImageView](https://github.com/hdodenhof/CircleImageView)
- [OkHttp](https://square.github.io/okhttp/)
- [Glide](https://muyangmin.github.io/glide-docs-cn/)
- [融云Token](https://www.rongcloud.cn/docs/android_imlib.html#prepare_get_token)
- [融云SDK](https://www.rongcloud.cn/downloads)
- [LitePal](https://github.com/LitePalFramework/LitePal)
- [EventBus](https://github.com/greenrobot/EventBus)
- [PhotoView](https://github.com/chrisbanes/PhotoView)
- [静态地图](https://lbs.amap.com/api/webservice/guide/api/staticmaps/)
- [讯飞](https://www.xfyun.cn/doc/)
- [二维码](https://github.com/yipianfengye/android-zxingLibrary)
- [饺子播放器](https://github.com/lipangit/JiaoZiVideoPlayer)
- [MAT](http://www.eclipse.org/mat/downloads.php)
- [LeakCanary](https://square.github.io/leakcanary/)
- [Walle](https://github.com/Meituan-Dianping/walle)

<h2 id="donate">Zxing性能优化 :fire:</h2> 

<h4 id="donate">1.参数优化</h4> 

```java
#避免滥用格式，指定扫描二维码
Intent intent = new Intent(this,CaptureActivity.class);
intent.setAction(Intents.Scan.ACTION);
intent.puExtra(Intents.Scan.FORMATS,"QR_CODE");
startActivityForResult(intent,REQUEST_CODE);
```

<h4 id="donate">2.自动聚焦时间</h4> 

将AutoFocusManager的AUTO_FOCUS_INTERVAL_MS修改即可。

<h4 id="donate">3.扫描精度</h4> 

如果没有优化的话，只会在中间的扫描区域才能扫描二维码，我们修改后则可以全屏扫描

关键的封装类在PlanarYUVLuminanceSource

CameraManager中的buildLuminanceSource方法修改返回值

```java
return new PlanarYUVLuminanceSource(data, width, height, 0, 0 , width),height, false);
```
	  
<h4 id="donate">4.快速扫描</h4> 

```java
DEFAULT_INTENT_RESULT_DURATION_MS = 0
```

<h2 id="donate">内存优化 :fire:</h2> 

OOM:内存溢出

Android系统垃圾回收机制：GC

内存管理机制：Low Menory Killer  佛系

最理想的状态：我吃完饭，走了，工作人员就来碗筷收起来

实际情况：懒人机制，你吃完饭你放这里，只有当没有空位置，这个时候才去回收

隐患：GC触发之后，所有的线程会暂停


什么是内存？

Android 基于Linux，App运行在沙箱机制，就是一个App独立运行在一个虚拟机中,即使我出错了
也不会影响系统，所以每一个虚拟机，运行的过程，我们叫做【进程】

获取系统进程：ps

打印对应进程的内存情况：dumpsys meminfo 【com.imooc.meet】

Heap Size：总内存

Heap Alloc:已使用

Heap Free：还剩下

Low Menory Killer：App启动和销毁 

App的启动销毁管理机制：Activity栈，先进先出，保护机制 -> LRU Cache (缓存淘汰机制)

内存优化：

- 1.养成良好的编码习惯
- 2.保存日志通过MAT工具分析

hprof文件：MAT工具不能识别的

需要转换：Android SDK / platfrom-tools / hprof-conv.exe

命令：hprof-con old new

Github:LeakCanary:直接绑定App检测内存问题

<h2 id="donate">布局优化 :fire:</h2> 

命令：adb shell service call window 1 i32 4939

开启 1 关闭 2 检查：adb shell service call window 3

返回值：Parcel(00000000 00000000   '........') 关闭状态

返回值：Parcel(00000000 00000001   '........') 开启状态

- 1.include: 复用布局
- 2.merge: 辅助扩展 主要作用防止在引用布局的时候多余的布局嵌套
- 3.ViewStub: 懒加载

误区：
- include：layout
- ViewStub：android:layout

<h2 id="donate">即时通讯优化 :fire:</h2> 

<h4 id="donate">1.即时通讯的问题</h4>

- 1.检查语言翻译 
- 2.检查RxJava的订阅关系
- 3.RxJava线程调度
	- 通话记录
	- 获取视频第一帧
- 4.新朋友
- 5.地图问题
	- 1.无法显示定位点
	- 2.无法显示定位地址
	- 3.静态地图显示高清
	
<h4 id="donate">2.即时通讯的功能展望</h4>

- 1.消息类：语音，红包，表情，共享位置
- 2.功能类：置顶，分组，删除好友
- 3.聊天界面可以优化 自定义背景， 头像挂件，时间的分割

<h2 id="donate">加固 :fire:</h2> 

加固：加固的方式很多种，本次案例使用的是 360加固宝

- axml 程序配置文件和资源文件
- dex 字节码文件
- arsc 编译后的资源  
- assets 未编码的文件
- libs so

<h2 id="donate">混淆 :fire:</h2>

<h4 id="donate">Proguard</h4>

- 文件压缩器
	- 压缩(Shrinking)：默认开启，优化Apk的体积，移除无用的类和成员
- 代码优化 
	- 优化(Optimization)主要体现在我们的字节码上，让我们的应用运行更快
- 代码混淆器 
	- 混淆(Obfuscation)：类和成员随机命名，增加了反编译和阅读的难度，keep保持
- 预校验器

<h4 id="donate">混淆的规则</h4>

我们来看下Proguard都有哪些可操作的方法吧。

1.压缩 Shrinking

默认开启，优化Apk体积，移除未使用的类和成员

```java
#关闭压缩
-dontshrink
```

2.优化 Optimization

默认开启，在字节码中进行优化，让应用运行更快

```java
#关闭优化
#-dontoptimize  

#表示proguard对代码进行迭代优化的次数，Android一般为5
-optimizationpasses n  
```

3.混淆 Obfuscation

默认开启，类和成员随机命名，增加反编译及阅读难度，可以使用keep命令保存

```java
#关闭混淆
-dontobfuscate  
```

4.keep
keep的用法非常的多

```java
#表示只是保持该包下的类名，而子包下的类名还是会被混淆
-keep class com.android.xx.*
#两颗星表示把本包和所含子包下的类名都保持
-keep class com.android.xx.*
#既可以保持该包下的类名，又可以保持类里面的内容不被混淆;
-keep class com.android.xx.*{*;}
#既可以保持该包及子包下的类名，又可以保持类里面的内容不被混淆;
-keep class com.android.xx.**{*;}
#保持某个类名不被混淆，但是内部内容会被混淆
-keep class com.android.Activity
#保持某个类的 类名及内部的所有内容不会混淆
-keep class com.android.Activity{*;}
#保持类中特定内容，而不是所有的内容可以使用如下
-keep class com.android.Activity {
  #匹配所有构造器
  <init>;
  #匹配所有域
  <fields>;
  #匹配所有方法
  <methods>;
}
#还可以进一步的优化
-keep class com.android.Activity{
  #保持该类下所有的共有方法不被混淆
  public <methods>;
  #保持该类下所有的共有内容不被混淆
  public *;
  #保持该类下所有的私有方法不被混淆
  private <methods>;
  #保持该类下所有的私有内容不被混淆
  private *;
  #保持该类的String类型的构造方法
  public <init>(java.lang.String);
}
#要保留一个类中的内部类不被混淆需要用 $ 符号
-keep classcom.android.Activity$TestClass{*;}
#保持继承关系不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.view.View
#不需要保持类名可以使用keepclassmembers
```

5.不可混淆

部分的规则是不可混淆的

a.JNI不可混淆

```java
#保持native方法不被混淆
-keepclasseswithmembernames class * {    
  native <methods>; 
}
```

b.清单文件

清单文件中所有使用到的类以及所有Android SDK下的类都无需混淆

c.自定义View

需要保持自定义View不混淆

d.Json对象类

需要保持Json对象类不混淆

e.第三方

正规的第三方都会提供混淆规则的

f.Parcelable的子类和Creator静态成员变量

Parcelable的子类和Creator静态成员变量不混淆，否则会产生Android.os.BadParcelableException异常；

```java
-keep class * implements Android.os.Parcelable { 
  # 保持Parcelable不被混淆            
  public static final Android.os.Parcelable$Creator *;
}
```

g.枚举

```java
#需要保持
-keepclassmembers enum * { 
public static **[] values();
 public static ** valueOf(java.lang.String);
 }
```

模板

```
#指定压缩级别
-optimizationpasses 5

#不跳过非公共的库的类成员
-dontskipnonpubliclibraryclassmembers

#混淆时采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#把混淆类中的方法名也混淆了
-useuniqueclassmembernames

#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification

#将文件来源重命名为“SourceFile”字符串
-renamesourcefileattribute SourceFile
#保留行号
-keepattributes SourceFile,LineNumberTable
#保持泛型
-keepattributes Signature

#保持所有实现 Serializable 接口的类成员
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#Fragment不需要在AndroidManifest.xml中注册，需要额外保护下
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment

# 保持测试相关的代码
-dontnote junit.framework.**
-dontnote junit.runner.**
-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**

```

混淆大部分情况下还是需要自己来处理的

- 1.保持JNI
- 2.保持自定义View
- 3.保持Android SDK类
- 4.保持第三方SDK

然后再去根据具体规则补充......

<h2 id="donate">App下载体验 :fire:</h2>

[下载页面,答案：喜欢](https://www.pgyer.com/imoocmeet)
































