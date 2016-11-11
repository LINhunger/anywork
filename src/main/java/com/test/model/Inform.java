package com.test.model;

import java.util.Date;

/**
 * Created by hunger on 2016/11/5.
 */
public class Inform {
    private int informId;//公告id
    private String name="inform";//对象名字
    private User author;//用户对象
    private Date createTime;//发布时间
    private String informTitle;//公告标题
    private String mark;//内容
    private int organId;//组织id

    public int getInformId() {
        return informId;
    }

    public void setInformId(int informId) {
        this.informId = informId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getInformTitle() {
        return informTitle;
    }

    public void setInformTitle(String informTitle) {
        this.informTitle = informTitle;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getOrganId() {
        return organId;
    }

    public void setOrganId(int organId) {
        this.organId = organId;
    }

    @Override
    public String toString() {
        return "Inform{" +
                "informId=" + informId +
                ", name='" + name + '\'' +
                ", author=" + author +
                ", createTime=" + createTime +
                ", informTitle='" + informTitle + '\'' +
                ", mark='" + mark + '\'' +
                ", organId=" + organId +
                '}';
    }
}
