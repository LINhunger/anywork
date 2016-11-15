package com.test.model;

import java.util.Date;

/**
 * Created by hunger on 2016/11/5.
 */
public class Homework {

    private int homeworkId;//作业id
    private String name="homework";//对象名字
    private int organId;//组织id
    private String homeworkTitle;//标题
    private  String mark;//内容
    private User author;//发布者对象
    private Date createTime;//发布时间
    private Date endingTime;//结束时间
    private int submitCount;//作业提交人数
    private String status;//作业提交名单

    public int getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
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

    public String getHomeworkTitle() {
        return homeworkTitle;
    }

    public void setHomeworkTitle(String homeworkTitle) {
        this.homeworkTitle = homeworkTitle;
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

    public Date getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(Date endingTime) {
        this.endingTime = endingTime;
    }

    public int getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(int submitCount) {
        this.submitCount = submitCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Homework{" +
                "homeworkId=" + homeworkId +
                ", name='" + name + '\'' +
                ", organId=" + organId +
                ", homeworkTitle='" + homeworkTitle + '\'' +
                ", mark='" + mark + '\'' +
                ", author=" + author +
                ", createTime=" + createTime +
                ", endingTime=" + endingTime +
                ", submitCount=" + submitCount +
                ", status='" + status + '\'' +
                '}';
    }
}
