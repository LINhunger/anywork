package com.test.model;

/**
 * Created by hunger on 2016/11/24.
 */
public class Topic {
    private int topicId;
    private  int type;
    private  String A;
    private  String B;
    private  String C;
    private  String D;
    private  int isTrue;
    private String content;
    private String key;
    private int score;
    private int textpaperId;

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public int getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(int isTrue) {
        this.isTrue = isTrue;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTextpaperId() {
        return textpaperId;
    }

    public void setTextpaperId(int textpaperId) {
        this.textpaperId = textpaperId;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", type=" + type +
                ", A='" + A + '\'' +
                ", B='" + B + '\'' +
                ", C='" + C + '\'' +
                ", D='" + D + '\'' +
                ", isTrue=" + isTrue +
                ", content='" + content + '\'' +
                ", key='" + key + '\'' +
                ", score=" + score +
                ", textpaperId=" + textpaperId +
                '}';
    }
}
