package com.test.message.respModel;

/**
 * 客服消息接口
 * Created by zggdczfr on 2016/10/27.
 */
public class CustomerMessage {
    //接收者账号(OpenID)
    private String ToUserName;
    //开发者微信号
    private String FromUserName;
    //消息创建时间
    private long CreateTime;
    //消息类型
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
