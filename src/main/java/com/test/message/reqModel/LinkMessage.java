package com.test.message.reqModel;

/**
 * 普通消息pojo实体
 * 连接消息
 * Created by zggdczfr on 2016/11/5.
 */
public class LinkMessage extends BaseMessage {
    //消息标题
    private String Title;
    //消息描述
    private String Description;
    //消息链接
    private String Url;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
