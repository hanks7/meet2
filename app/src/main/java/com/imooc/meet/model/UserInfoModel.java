package com.imooc.meet.model;

/**
 * FileName: UserInfoModel
 * Founder: LiuGuiLin
 * Profile: 用户信息的模型
 */
public class UserInfoModel {

    //背景颜色
    private int bgColor;
    //标题
    private String title;
    //内容
    private String content;

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
