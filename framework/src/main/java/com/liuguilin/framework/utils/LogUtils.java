package com.liuguilin.framework.utils;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FileName: LogUtils
 * Founder: LiuGuiLin
 * Profile: Log
 * <p>
 * Log不光作为日志的打印，还可以记录日志 ——> File
 */
public class LogUtils {

    private static SimpleDateFormat mSimpleDateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void i(Object text) {
        Ulog.i(text);
        writeToFile(text + "");
    }

    public static void i(String tag, Object text) {
        Ulog.i(tag, text);
        writeToFile(tag + ":" + text);
    }

    public static void e(Object tag, Object text) {
        Ulog.i(tag, text);
        writeToFile(tag + ":" + text);
    }

    public static void e(Object text) {
        Ulog.i("error", text);
        writeToFile(text + "");
    }

    /**
     * 写入内存卡
     *
     * @param text
     */
    private static void writeToFile(String text) {
        //开始写入
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            //文件路径
            String fileRoot = Environment.getExternalStorageDirectory().getPath() + "/Meet/";
            String fileName = "Meet.log";
            // 时间 + 内容
            String log = mSimpleDateFormat.format(new Date()) + " " + text + "\n";
            //检查父路径
            File fileGroup = new File(fileRoot);
            //创建根布局
            if (!fileGroup.exists()) {
                fileGroup.mkdirs();
            }
            //创建文件
            File fileChild = new File(fileRoot + fileName);
            if (!fileChild.exists()) {
                fileChild.createNewFile();
            }
            fileOutputStream = new FileOutputStream(fileRoot + fileName, true);
            //编码问题 GBK 正确的存入中文
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, Charset.forName("Utf-8")));
//            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, Charset.forName("gbk")));
            bufferedWriter.write(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            e("FileNotFoundException", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            e("IOException", e.toString());
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
