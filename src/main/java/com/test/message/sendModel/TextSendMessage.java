package com.test.message.sendModel;

import java.util.List;

/**
 * Created by zggdczfr on 2016/11/1.
 */
public class TextSendMessage {

    private List<String> touser;
    private String msgtype;
    private Content text;

    public Content getText() {
        return text;
    }

    public void setText(Content text) {
        this.text = text;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public List<String> getTouser() {
        return touser;
    }

    public void setTouser(List<String> touser) {
        this.touser = touser;
    }

}

