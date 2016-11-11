package com.test.message.reqModel;

/**
 * 普通消息pojo实体
 * 文本消息
 * Created by zggdczfr on 2016/10/21.
 */
public class TextMessage extends BaseMessage {
    //消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
