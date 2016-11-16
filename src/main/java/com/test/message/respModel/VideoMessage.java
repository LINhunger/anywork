package com.test.message.respModel;

/**
 * 视频消息
 * Created by zggdczfr on 2016/11/5.
 */
public class VideoMessage extends BaseMessage {

    private Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
