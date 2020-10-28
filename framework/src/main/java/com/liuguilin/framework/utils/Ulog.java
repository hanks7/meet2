package com.liuguilin.framework.utils;

import android.Manifest;
import android.content.Context;
import android.util.Log;

import com.liuguilin.framework.utils.permission.PermissionReq;

/**
 * @author 侯建军
 * @data on 2018/1/4 10:40
 * @org www.hopshine.com
 * @function 请填写
 * @email 474664736@qq.com
 */

public class Ulog {
    public static Context context;
    /**
     * 打印日志的前缀
     */
    public static final String TAG = "cc-";
    /**
     * 日志存放目录
     */
    public static final String logFileName = "MeetLog";
    /**
     * 编码名称
     */
    public static final String charsetName = "utf-8";

    public static void i(Object content) {
        Log.i(TAG, content + "");
    }

    public static void i(Object tag, Object content) {
        Log.i(TAG + tag, content + "");
    }

    public static void e(Object content) {
        Log.e(TAG, content + "");
    }

    public static void e(Object tag, Object content) {
        Log.e(TAG + tag, content + "");
    }


    /**
     * 写入内存卡
     *
     * @param text
     */
    public static void writeToFile(String text) {
        if (!PermissionReq.isHasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            return;

        i("writeToFile", "---\n\n" + text + "\n\n\n\n---");
//        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat mSimpleDateFormatTag = new SimpleDateFormat("yyyy-MM-dd");
//        //开始写入
//        FileOutputStream fileOutputStream = null;
//        BufferedWriter bufferedWriter = null;
//        try {
//            //文件路径
//            String fileRoot = Environment.getExternalStorageDirectory().getPath() + "/" + logFileName + "/";
//            String fileName = mSimpleDateFormatTag.format(new Date()) + ".txt";
//            // 时间 + 内容
//            String log = "\n\n\n\n-----------" + mSimpleDateFormat.format(new Date()) + "----------\n"
//                    + "\n" + text;
//            //检查父路径
//            File fileGroup = new File(fileRoot);
//            //创建根布局
//            if (!fileGroup.exists()) {
//                fileGroup.mkdirs();
//            }
//            //创建文件
//            File fileChild = new File(fileRoot + fileName);
//            if (!fileChild.exists()) {
//                fileChild.createNewFile();
//            }
//            fileOutputStream = new FileOutputStream(fileRoot + fileName, true);
//            //编码问题 GBK 正确的存入中文
//
//            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, Charset.forName(charsetName)));
//            bufferedWriter.write(log);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            i(e.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//            i(e.toString());
//        } finally {
//            if (bufferedWriter != null) {
//                try {
//                    bufferedWriter.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }


}
