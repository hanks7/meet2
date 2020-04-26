package com.liuguilin.framework.bmob;

import cn.bmob.v3.BmobObject;

/**
 * FileName: FateSet
 * Founder: LiuGuiLin
 * Profile: 缘分池
 */
public class FateSet extends BmobObject {

    //用户ID
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
