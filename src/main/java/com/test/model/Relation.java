package com.test.model;

import java.util.Date;

/**
 * Created by hunger on 2016/11/5.
 */
public class Relation {
    private int relationId;//id
    private User user;//用户对象
    private int organId;//组织id
    private String openid;//微信凭证
    private int role;//角色
    private Date createTime;//加入时间

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOrganId() {
        return organId;
    }

    public void setOrganId(int organId) {
        this.organId = organId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "relationId=" + relationId +
                ", user=" + user +
                ", organId=" + organId +
                ", openid='" + openid + '\'' +
                ", role=" + role +
                ", createTime=" + createTime +
                '}';
    }
}
