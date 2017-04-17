package com.shide.jPush.bean;

/**
 * Created by wangc on 2017/4/17.
 */
public class MsgDTO {
    private String title;
    private String msgImgUrl;
    private String sendUserName;
    private String sendType;
    private String msgType;
    private String userid;
    private String msgContent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsgImgUrl() {
        return msgImgUrl;
    }

    public void setMsgImgUrl(String msgImgUrl) {
        this.msgImgUrl = msgImgUrl;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}
