package com.test.message.respModel;

/**
 * 回消息体-基本消息
 * 一种为不需要上传资源到微信服务器的这里成为【普通消息回复】
 * 一种需要上传资源到微信服务器的这里称为【多媒体消息回复】
 * Created by zggdczfr on 2016/10/22.
 */
public class BaseMessage {

    //接收方账号(OpenID)
    private String ToUserName;
    //开发者微信号
    private String FromUserName;
    //消息创建时间(整型)
    private long CreateTime;
    //消息类型(text/music/news)
    private String MsgType;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
}
