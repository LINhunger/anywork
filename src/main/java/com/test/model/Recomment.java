package com.test.model;

/**
 * Created by hunger on 2016/11/5.
 */
public class Recomment {
    private int recommentId;//回复id
    private int commentId;//评论id
    private User sender;//发送者对象
    private User receiver;//接收者对象
    private String content;//内容
    private int targetId;//作业或请求的id
    private int type;//2代表作业的回复，3代表请求的回复

    public int getRecommentId() {
        return recommentId;
    }

    public void setRecommentId(int recommentId) {
        this.recommentId = recommentId;
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
                "recommentId=" + recommentId +
                ", commentId=" + commentId +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", content='" + content + '\'' +
                ", targetId=" + targetId +
                ", type=" + type +
                '}';
    }
}
