package com.test.message.respModel;

/**
 * 视频消息体
 * Created by zggdczfr on 2016/10/22.
 */
public class Video {
    //多媒体链接
    private String MediaId;
    //视频标题
    private String Title;
    //视频描述
    private String Description;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

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
}
