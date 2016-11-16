package com.test.message.respModel;

/**
 * 普通消息回复实体
 * 文本信息消息体
 * Created by zggdczfr on 2016/11/5.
 */
public class TextMessage extends BaseMessage {

    //回复消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
