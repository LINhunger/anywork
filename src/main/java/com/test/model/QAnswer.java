package com.test.model;

import java.util.Date;

/**
 * Created by hunger on 2016/11/5.
 */
public class QAnswer {
    private int qAnswerId;//请求答案id
    private int questId;//请求id
    private User author;//用户对象
    private String mark;//请求内容
    private String note;//请求笔记
    private Date createTime;//发布时间

    public int getqAnswerId() {
        return qAnswerId;
    }

    public void setqAnswerId(int qAnswerId) {
        this.qAnswerId = qAnswerId;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "QAnswer{" +
                "qAnswerId=" + qAnswerId +
                ", questId=" + questId +
                ", author=" + author +
                ", mark='" + mark + '\'' +
                ", note='" + note + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
