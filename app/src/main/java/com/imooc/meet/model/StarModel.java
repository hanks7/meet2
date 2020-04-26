package com.imooc.meet.model;

/**
 * FileName: StarModel
 * Founder: LiuGuiLin
 * Profile: 星球View的数据模型
 */
public class StarModel {

    //昵称
    private String nickName;
    //ID
    private String userId;
    //头像
    private String photoUrl;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
