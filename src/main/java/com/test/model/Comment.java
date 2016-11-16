package com.test.model;

import java.util.Date;
import java.util.List;

/**
 * Created by hunger on 2016/11/5.
 */
public class Comment {

    private int commentId;//评论id
    private User sender;//发送者对象
    private int targetId;//作业或请求的id
    private int type;//0代表作业的评论回复，1代表请求的评论回复
    private String content;//内容
    private List<Recomment> recomments;//对该评论的回复集合

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Recomment> getRecomments() {
        return recomments;
    }

    public void setRecomments(List<Recomment> recomments) {
        this.recomments = recomments;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", sender=" + sender +
                ", targetId=" + targetId +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", recomments=" + recomments +
                ", createTime=" + createTime +
                '}';
    }
}
