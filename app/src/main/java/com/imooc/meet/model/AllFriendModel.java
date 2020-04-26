package com.imooc.meet.model;

/**
 * FileName: AllFriendModel
 * Founder: LiuGuiLin
 * Profile: 全部好友的数据模型
 */
public class AllFriendModel {

    private String userId;
    private String url;
    private boolean sex;
    private String nickName;
    private String desc;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
