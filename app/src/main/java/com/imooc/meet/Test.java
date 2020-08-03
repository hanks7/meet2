package com.imooc.meet;

import java.io.File;
import java.io.IOException;

/**
 * @author 侯建军 QQ:474664736
 * @time 2020/4/27 13:51
 * @class describe
 */
public class Test {

    private static void pr(String tag, Object param) {
        System.out.println(tag + "===" + param + "");
    }

    public static void main(String[] args) {
//        pr("file0",File.pathSeparator);  //   ;
//        pr("file0",File.separator);      //  \

//        File file = new File("D:\\java\\b.txt");  //双\\是转义
//        pr("file0",file);
//        File file2 = new File("D:\\java", "a.txt");//父路径、子路径--可以适用于多个文件的！
//        pr("file0",file2);
//        File parent = new File("D:\\java");
//        File file3 = new File(parent, "a.txt");//File类的父路径、子路径
//        pr("file0",file3);

        mkdir();
    }


    public static void method01() {
        File file = new File("D:\\java\\a.txt");

        //写相对路径的话，会自动转成绝对路径，但是不去检验文件是否真实存在（只会给翻译回来，可能根本不存在） D:\JAVA0322\Day16\src
        File file0 = new File("src");
        pr("file0", file0);

        File file00 = new File("aa");//这个根本不存在  D:\JAVA0322\Day16\aa
        pr("file00", file00);

        //获取文件对象的文件名或者目录名
        pr("getName", file00.getName());

        //获取文件对象的路径所对应的字符串   类似于toString()方法
        pr("getPath", file00.getPath());

        //获取文件的大小（字节---Long类型）
        pr("length", file00.length());
    }


    /**
     * 创建文件
     * @throws IOException
     */
    public static void createNewFile() throws IOException {
        File file = new File("D:\\java\\d");
        //创建文件
        boolean flag = file.createNewFile();//都是创建的文件(最好都是加上后缀的），不能是文件夹
        pr("flag", flag);
    }


    /**
     * 文件删除
     */
    public static void delete() {
        File file = new File("D:\\java\\d");
        //删除文件（找不回来了）
        boolean flag = file.delete();
        pr("flag", flag);
    }

    /**
     * 文件判断
     */
    public static void judge() {
        File file = new File("D:\\java\\a.txt");
        //判断该文件对象所对应的文件是否存在
        pr("exists", file.exists());
        //判断该文件对象是否是文件夹
        pr("isDirectory", file.isDirectory());
        //判断该文件对象是否是文件
        pr("isFile", file.isFile());
    }

    /**
     * 创建单级目录
     *  首先必须保证D:\java这个目录存在
     */
    public static void mkdir() {
        File file = new File("D:\\java\\d.txt");
        boolean flag = file.mkdir();
        pr("flag", flag);
    }


    /**
     * 创建多级目录
     * 经过测试你你会发现b.text 会被创建成一个文件夹
     */
    public static void mkdirs() {
        File file = new File("D:\\java\\d\\a\\b.text");
        boolean flag = file.mkdirs();
        pr("flag", flag);
    }


}
