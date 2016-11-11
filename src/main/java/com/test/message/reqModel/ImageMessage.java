package com.test.message.reqModel;

/**
 * 普通消息pojo实体
 * 图片消息
 * Created by zggdczfr on 2016/10/21.
 */
public class ImageMessage extends BaseMessage {
    //图片链接
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
