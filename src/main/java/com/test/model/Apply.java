package com.test.model;

import java.util.Date;

/**
 * Created by hunger on 2016/11/5.
 */
public class Apply {
    private int applyId;//申请id
    private User sender;//发送者对象
    private int organId;//组织id
    private Date createTime;//创建时间
    private int status;//处理状态

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public int getOrganId() {
        return organId;
    }

    public void setOrganId(int organId) {
        this.organId = organId;
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
        return "Apply{" +
                "applyId=" + applyId +
                ", sender=" + sender +
                ", organId=" + organId +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }
}
