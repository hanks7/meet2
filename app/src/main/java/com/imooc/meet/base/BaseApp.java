package com.imooc.meet.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.liuguilin.framework.Framework;
import com.liuguilin.framework.utils.DensityUtil;
import com.liuguilin.framework.utils.Ulog;

/**
 * FileName: BaseApp
 * Founder: LiuGuiLin
 * Profile: App
 */
public class BaseApp extends Application {
    public static BaseApp app;

    public static BaseApp getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DensityUtil.setDensity(this,420f);
        app = this;
        Ulog.context = this;
        /**
         * Application的优化
         * 1.必要的组件在程序主页去初始化
         * 2.如果组件一定要在App中初始化，那么尽可能的延时
         * 3.非必要的组件，子线程中初始化
         */

        //只在主进程中初始化
        if (getApplicationInfo().packageName.equals(
                getCurProcessName(getApplicationContext()))) {
            //获取渠道
            //String flavor = FlavorHelper.getFlavor(this);
            //Toast.makeText(this, "flavor:" + flavor, Toast.LENGTH_SHORT).show();
            Framework.getFramework().initFramework(this);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    /**
     * 获取当前进程名
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess :
                activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
