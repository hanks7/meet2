package com.imooc.meet;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 侯建军 QQ:474664736
 * @time 2020/4/27 13:51
 * @class describe
 */
public class Test {


    public static void main(String[] args) {
        test3();


//        test2("https://www.cnblogs.com/peachh/p/9740229.html",
//                "SiteURL",
//                "https://[\\w+\\.?/?]+\\.[A-Za-z]+"
//        );
//        test2("https://www.hao123.com/",
//                "SiteURL",
//                "https://[\\w+\\.?/?]+\\.[A-Za-z]+"
//        );

//        System.out.println(Html2Text(getString()));

    }

    private static void test3() {
        showlog("test3");
        //获取源代码
        String html=SendGet("http://www.cnki.net/","utf-8");
        //定义一个字符串用来第一次匹配到的数据
        String html1="";
        // 写入正则用来匹配名称
        Pattern NamePattern = Pattern.compile("<ul\\s+style=\"padding-right:0;\"\\s+class=\"col1\">(.+?)</ul>");
        Matcher NameMatcher = NamePattern.matcher(html);
        //迭代得到第一次匹配到的数据
        while(NameMatcher.find()){
            html1+=NameMatcher.group(1);
        }
        //执行第二次匹配
        Pattern NameTwoPattern = Pattern.compile("<li><a.+?>(.+?)</a>");
        Matcher NameTwoMatcher = NameTwoPattern.matcher(html1);
        //迭代得到第二次匹配到的数据
        while(NameTwoMatcher.find()){
            String name=NameTwoMatcher.group(1);
            //去除结果中的html标签
            name=name.replaceAll("<sup>新!</sup>","" );
            //输出结果
            System.out.println(name);
        }
    }

    /**
     * @param strUrl    要爬虫的网站
     * @param fileName  数据存储的位置
     * @param strRregex 正则表达式
     */
    private static void test2(String strUrl, final String fileName, String strRregex) {
        URL url = null;
        URLConnection urlconn = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        String regex = strRregex;//url匹配规则
        Pattern p = Pattern.compile(regex);
        try {
            url = new URL(strUrl);//爬取的网址、这里爬取的是一个生物网站
            urlconn = url.openConnection();
            pw = new PrintWriter(new FileWriter("D:/" + fileName + ".txt"), true);//将爬取到的链接放到D盘的SiteURL文件中
            br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            String buf = null;
            while ((buf = br.readLine()) != null) {
                Matcher buf_m = p.matcher(buf);
                while (buf_m.find()) {
                    showlog(buf_m.group());
                    pw.println(buf_m.group());
                }
            }
            showlog("爬取成功^_^");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pw.close();
        }
    }

    private static void showlog(String s) {
        System.out.println(s);
    }


    private static String getString() {
        return "  <div class=\"swiper-slide\">\n" +
                "                    <div id=\"learnChapter\" class=\"learn-chapter\"><h3>第1章 课程导学与准备工作</h3><ul><li data-mid=\"29652\"><i class=\"chapter-icon1 imwap-code\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29652\">1-1 <span class=\"medianame\">课前必读（不看会错过一个亿）</span></a><i class=\"section-state-icon imwap-check_circle section-state-learn\"></i></li><li data-mid=\"30153\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30153\">1-2 <span class=\"medianame\">导学</span></a><i class=\"section-state-icon imwap-check_circle section-state-learn\"></i></li></ul><h3>第2章 AndroidX与Gradle</h3><ul><li data-mid=\"29840\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29840\">2-1 <span class=\"medianame\">结合Google迁移AndroidX</span></a><i class=\"section-state-icon imwap-check_circle section-state-learn\"></i></li><li data-mid=\"29841\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29841\">2-2 <span class=\"medianame\">自定义Gradle配置文件</span></a><i class=\"section-state-icon imwap-check_circle section-state-learn\"></i></li><li class=\"active\" data-mid=\"29842\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29842\">2-3 <span class=\"medianame\">如何引用自定义配置</span></a><i class=\"section-state-icon imwap-Progress2 section-state-learn\"></i></li><li data-mid=\"29843\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29843\">2-4 <span class=\"medianame\">如何配置Gradle常量</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29844\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29844\">2-5 <span class=\"medianame\">小结</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29845\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29845\">2-6 <span class=\"medianame\">提升Gradle构建速度的十大技巧</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li></ul><h3>第3章 即时通讯和音视频基础</h3><ul><li data-mid=\"29851\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29851\">3-1 <span class=\"medianame\">认识Bmob的用户模块</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29847\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29847\">3-2 <span class=\"medianame\">认识融云即时通讯模块</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29848\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29848\">3-3 <span class=\"medianame\">认识融云音视频模块</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30182\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30182\">3-4 <span class=\"medianame\">设计通讯层基础架构</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li></ul><h3>第4章 通用Framework设计</h3><ul><li data-mid=\"29915\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29915\">4-1 <span class=\"medianame\">创建Framework</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29916\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29916\">4-2 <span class=\"medianame\">封装静态log日志</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29917\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29917\">4-3 <span class=\"medianame\">单例封装时间转换类</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29925\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29925\">4-4 <span class=\"medianame\">沉浸式状态栏</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29926\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29926\">4-5 <span class=\"medianame\">MediaPlayer媒体播放</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li></ul><h3>第5章 App启动流程</h3><ul><li data-mid=\"29927\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29927\">5-1 <span class=\"medianame\">App适配刘海屏</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29928\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29928\">5-2 <span class=\"medianame\">引导页和帧动画1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29931\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29931\">5-3 <span class=\"medianame\">引导页和帧动画2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29932\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29932\">5-4 <span class=\"medianame\">自定义View拖拽验证码1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29933\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29933\">5-5 <span class=\"medianame\">自定义View拖拽验证码2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29934\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29934\">5-6 <span class=\"medianame\">Bmob的集成</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29935\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29935\">5-7 <span class=\"medianame\">Bmob云函数之增删查改</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29936\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29936\">5-8 <span class=\"medianame\">Bmob短信验证码与用户注册</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29937\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29937\">5-9 <span class=\"medianame\">自定义DialogView</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29938\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29938\">5-10 <span class=\"medianame\">自定义LoadingView</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29939\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29939\">5-11 <span class=\"medianame\">Android动态权限与窗口权限1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29940\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29940\">5-12 <span class=\"medianame\">Android动态权限与窗口权限2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29941\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29941\">5-13 <span class=\"medianame\">App启动优化</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li></ul><h3>第6章 主页框架搭建</h3><ul><li data-mid=\"29942\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29942\">6-1 <span class=\"medianame\">Fragment优化与切换</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29943\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29943\">6-2 <span class=\"medianame\">3D星球View的实现</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29948\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29948\">6-3 <span class=\"medianame\">头像上传于FileProvider1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29949\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29949\">6-4 <span class=\"medianame\">头像上传于FileProvider2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29950\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29950\">6-5 <span class=\"medianame\">头像上传于FileProvider3</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29951\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29951\">6-6 <span class=\"medianame\">搜索好友与推荐1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29952\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29952\">6-7 <span class=\"medianame\">搜索好友与推荐2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29953\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29953\">6-8 <span class=\"medianame\">从通讯录匹配好友</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29954\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29954\">6-9 <span class=\"medianame\">自定义头部拉伸ScrollView</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29955\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29955\">6-10 <span class=\"medianame\">封装万能的RecyclerView适配器</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li></ul><h3>第7章 融云集成</h3><ul><li data-mid=\"29956\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29956\">7-1 <span class=\"medianame\">获取融云Token</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29957\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29957\">7-2 <span class=\"medianame\">融云的集成和服务连接</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29958\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29958\">7-3 <span class=\"medianame\">融云的消息体系</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29959\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29959\">7-4 <span class=\"medianame\">发送添加好友消息1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29961\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29961\">7-5 <span class=\"medianame\">发送添加好友消息2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29972\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29972\">7-6 <span class=\"medianame\">LitePal数据库的集成</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30150\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30150\">7-7 <span class=\"medianame\">EventBus事件栈</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29962\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29962\">7-8 <span class=\"medianame\">同意添加好友消息</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30151\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30151\">7-9 <span class=\"medianame\">会话管理1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30152\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30152\">7-10 <span class=\"medianame\">会话管理2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li></ul><h3>第8章 即时通讯集成</h3><ul><li data-mid=\"29971\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29971\">8-1 <span class=\"medianame\">全部好友列表</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29973\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29973\">8-2 <span class=\"medianame\">发送文本消息1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29974\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29974\">8-3 <span class=\"medianame\">发送文本消息2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29975\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29975\">8-4 <span class=\"medianame\">发送文本消息3</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29976\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29976\">8-5 <span class=\"medianame\">发送图片消息1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"29977\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=29977\">8-6 <span class=\"medianame\">发送图片消息2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30126\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30126\">8-7 <span class=\"medianame\">发送位置消息1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30127\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30127\">8-8 <span class=\"medianame\">发送位置消息2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30128\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30128\">8-9 <span class=\"medianame\">发送位置消息3</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30129\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30129\">8-10 <span class=\"medianame\">发送位置消息4</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30130\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30130\">8-11 <span class=\"medianame\">讯飞语音听写识别</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li></ul><h3>第9章 音视频通话开发</h3><ul><li data-mid=\"30188\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30188\">9-1 <span class=\"medianame\">音视频双端通信</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30189\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30189\">9-2 <span class=\"medianame\">（选学）认识窗口WindowManager</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30190\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30190\">9-3 <span class=\"medianame\">实现音频通话服务1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30191\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30191\">9-4 <span class=\"medianame\">实现音频通话服务2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30192\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30192\">9-5 <span class=\"medianame\">实现视频通话服务</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30442\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30442\">9-6 <span class=\"medianame\">实现音频通话悬浮窗</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30443\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30443\">9-7 <span class=\"medianame\">实现通话记录1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30444\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30444\">9-8 <span class=\"medianame\">实现通话记录2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li></ul><h3>第10章 基础功能开发</h3><ul><li data-mid=\"30266\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30266\">10-1 <span class=\"medianame\">二维码Zxing的扫描</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30267\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30267\">10-2 <span class=\"medianame\">Zxing的性能优化讲解</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30268\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30268\">10-3 <span class=\"medianame\">应用图片分享</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30269\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30269\">10-4 <span class=\"medianame\">隐私设置禁止联系人</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li></ul><h3>第11章 星球开发</h3><ul><li data-mid=\"30558\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30558\">11-1 <span class=\"medianame\">随机匹配1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30559\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30559\">11-2 <span class=\"medianame\">随机匹配2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30560\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30560\">11-3 <span class=\"medianame\">灵魂匹配1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30561\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30561\">11-4 <span class=\"medianame\">灵魂匹配2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30562\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30562\">11-5 <span class=\"medianame\">缘分匹配</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30563\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30563\">11-6 <span class=\"medianame\">恋爱匹配</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li></ul><h3>第12章 朋友圈开发</h3><ul><li data-mid=\"30631\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30631\">12-1 <span class=\"medianame\">朋友圈设计1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30632\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30632\">12-2 <span class=\"medianame\">朋友圈设计2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30633\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30633\">12-3 <span class=\"medianame\">朋友圈音乐分享</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30634\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30634\">12-4 <span class=\"medianame\">朋友圈视频分享</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30635\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30635\">12-5 <span class=\"medianame\">朋友圈悬浮设计</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li></ul><h3>第13章 应用性能优化</h3><ul><li data-mid=\"30741\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30741\">13-1 <span class=\"medianame\">内存性能优化</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30742\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30742\">13-2 <span class=\"medianame\">布局性能优化</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30743\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30743\">13-3 <span class=\"medianame\">即时通讯优化</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li></ul><h3>第14章 应用上线与安全加固</h3><ul><li data-mid=\"30744\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30744\">14-1 <span class=\"medianame\">Gradle与多渠道打包1</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30745\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30745\">14-2 <span class=\"medianame\">Gradle与多渠道打包2</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30746\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30746\">14-3 <span class=\"medianame\">加固</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li><li data-mid=\"30747\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30747\">14-4 <span class=\"medianame\">混淆</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li></ul><h3>第15章 课程总结</h3><ul><li data-mid=\"30748\"><i class=\"chapter-icon1 imwap-play_arrow\"></i><a href=\"https://coding.m.imooc.com/learn?cid=390&amp;mid=30748\">15-1 <span class=\"medianame\">总结</span></a><i class=\"section-state-icon imwap-panorama-fisheye\"></i></li></ul></div>\n" +
                "                </div>";
    }

    //从html中提取纯文本
    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll("\n"); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            showlog("Html2Text: " + e.getMessage());
        }
//剔除空格行
        textStr = textStr.replaceAll("[ ]+", " ");
        textStr = textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        return textStr;// 返回文本字符串
    }


    /**
     *      * 爬虫工具类
     *      * @param url 传入连接地址
     *      * @param param  设置网页编码格式
     *      * @return
     *     
     */
    static String SendGet(String url, String param) {
        // 定义一个字符串用来存储网页内容
        String result = "";
        // 定义一个缓冲字符输入流
        BufferedReader in = null;

        try {
            // 将string转成url对象
            URL realUrl = new URL(url);
            // 初始化一个链接到那个url的连接
            URLConnection connection = realUrl.openConnection();
            // 开始实际的连接
            connection.connect();
            // 初始化 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), param));
            // 用来临时存储抓取到的每一行的数据
            String line;
            while ((line = in.readLine()) != null) {
                // 遍历抓取到的每一行并将其存储到result里面
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;

    }


}
