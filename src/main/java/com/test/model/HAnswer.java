package com.test.model;

import java.util.Date;

/**
 * Created by hunger on 2016/11/5.
 */
public class HAnswer {
    private int hAnswerId;//作业答案id
    private int homeworkId;//作业id
    private User author;//用户对象
    private String mark;//内容
    private int grade5;//星级5人数
    private int grade4;//星级4人数
    private int grade3;//星级3及以下人数
    private String note;//笔记内容
    private Date createTime;//发布时间
    private int status;//是否已评分，为0则未评分，1为已评分

    public int gethAnswerId() {
        return hAnswerId;
    }

    public void sethAnswerId(int hAnswerId) {
        this.hAnswerId = hAnswerId;
    }

    public int getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
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

    public int getGrade5() {
        return grade5;
    }

    public void setGrade5(int grade5) {
        this.grade5 = grade5;
    }

    public int getGrade4() {
        return grade4;
    }

    public void setGrade4(int grade4) {
        this.grade4 = grade4;
    }

    public int getGrade3() {
        return grade3;
    }

    public void setGrade3(int grade3) {
        this.grade3 = grade3;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HAnswer{" +
                "hAnswerId=" + hAnswerId +
                ", homeworkId=" + homeworkId +
                ", author=" + author +
                ", mark='" + mark + '\'' +
                ", grade5=" + grade5 +
                ", grade4=" + grade4 +
                ", grade3=" + grade3 +
                ", note='" + note + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }
}
