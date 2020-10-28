package com.liuguilin.framework.utils.permission;

public interface PermissionResult {
    void onGranted();
    void onDenied();
    void onNext();
}
