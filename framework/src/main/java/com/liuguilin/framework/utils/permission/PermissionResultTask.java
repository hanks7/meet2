package com.liuguilin.framework.utils.permission;

import android.content.Context;

public abstract class PermissionResultTask implements PermissionResult {
    Context context;
    String msg;

    public PermissionResultTask(Context context, String msg) {
        this.context = context;
        this.msg = msg;
    }

    /**
     * 权限被拒绝,淡出跳转该应用设置的dialog
     */
    public void onDenied() {
        PermissionReq.showDialog(context, msg);
    }

    /**
     * 无论权限是否申请成功都继续执行的方法
     */
    public void onNext() {

    }
}
