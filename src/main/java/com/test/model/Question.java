package com.test.model;

import java.util.Date;

/**
 * Created by hunger on 2016/11/5.
 */
public class Question {

    private int questId;//请求id
    private String name="question";//对象名字
    private int organId;//组织id
    private String questTitle;//请求标题
    private String mark;//内容
    private User author;//发布者
    private Date createTime;//发布时间
    private QAnswer qAnswer;//最佳答案

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getOrganId() {
        return organId;
    }

    public void setOrganId(int organId) {
        this.organId = organId;
    }

    public String getQuestTitle() {
        return questTitle;
    }

    public void setQuestTitle(String questTitle) {
        this.questTitle = questTitle;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public QAnswer getqAnswer() {
        return qAnswer;
    }

    public void setqAnswer(QAnswer qAnswer) {
        this.qAnswer = qAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questId=" + questId +
                ", name='" + name + '\'' +
                ", organId=" + organId +
                ", questTitle='" + questTitle + '\'' +
                ", mark='" + mark + '\'' +
                ", author=" + author +
                ", createTime=" + createTime +
                ", qAnswer=" + qAnswer +
                '}';
    }
}
