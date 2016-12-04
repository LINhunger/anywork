package com.test.model;

import java.util.Date;
import java.util.List;

/**
 * Created by hunger on 2016/11/24.
 */
public class Textpaper {
    private int textpaperId;
    private String textpaperTitle;
    private String userName;
    private int authorId;
    private Date createTime;
    private Date endingTime;
    private int textpaperScore;
    private int textpaperType;
    private String name;
    private int textpaperTime;
    private int difficulty;
    private List<Topic> topics;

    public int getTextpaperId() {
        return textpaperId;
    }

    public void setTextpaperId(int textpaperId) {
        this.textpaperId = textpaperId;
    }

    public String getTextpaperTitle() {
        return textpaperTitle;
    }

    public void setTextpaperTitle(String textpaperTitle) {
        this.textpaperTitle = textpaperTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
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

    public int getTextpaperScore() {
        return textpaperScore;
    }

    public void setTextpaperScore(int textpaperScore) {
        this.textpaperScore = textpaperScore;
    }

    public int getTextpaperType() {
        return textpaperType;
    }

    public void setTextpaperType(int textpaperType) {
        this.textpaperType = textpaperType;
        if (textpaperType==3){
            this.name="practise";
        }else if(textpaperType==2) {
            this.name="prepare";
        }else {
            this.name="test";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTextpaperTime() {
        return textpaperTime;
    }

    public void setTextpaperTime(int textpaperTime) {
        this.textpaperTime = textpaperTime;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return "Textpaper{" +
                "textpaperId=" + textpaperId +
                ", textpaperTitle='" + textpaperTitle + '\'' +
                ", userName='" + userName + '\'' +
                ", authorId=" + authorId +
                ", createTime=" + createTime +
                ", endingTime=" + endingTime +
                ", textpaperScore=" + textpaperScore +
                ", textpaperType=" + textpaperType +
                ", name='" + name + '\'' +
                ", textpaperTime=" + textpaperTime +
                ", difficulty=" + difficulty +
                ", topics=" + topics +
                '}';
    }
}
