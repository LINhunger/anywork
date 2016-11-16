package com.test.model;

import java.util.Date;

/**
 * Created by hunger on 2016/11/5.
 */
public class Recomment {
    private int commentId;//回复id
    private int aCommentId;//评论id
    private User sender;//发送者对象
    private User receiver;//接收者对象
    private String content;//内容
    private int targetId;//作业或请求的id
    private int type;//2代表作业的回复，3代表请求的回复

    /**
     * 增加
     */
    private Date createTime;//创建时间

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getaCommentId() {
        return aCommentId;
    }

    public void setaCcommentId(int aCcommentId) {
        this.aCommentId = aCcommentId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Recomment{" +
                "recommentId=" + commentId +
                ", commentId=" + aCommentId +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", content='" + content + '\'' +
                ", targetId=" + targetId +
                ", type=" + type +
                ", createTime=" + createTime +
                '}';
    }
}
