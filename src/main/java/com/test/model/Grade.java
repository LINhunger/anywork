package com.test.model;

/**
 * Created by hunger on 2016/11/5.
 */
public class Grade {
    private int gradeId;//id
    private int homeworkId;//作业id
    private int answerId;//作业答案id
    private int userId;//用户id
    private int grade;//分数

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gradeId=" + gradeId +
                ", homeworkId=" + homeworkId +
                ", answerId=" + answerId +
                ", userId=" + userId +
                ", grade=" + grade +
                '}';
    }
}
