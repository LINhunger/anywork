package com.test.model;

import java.util.Date;
import java.util.Set;

/**
 * Created by hunger on 2016/11/5.
 */
public class Organization {
    private int organId;//组织ID
    private String organName;//组织名
    private Date createTime;//创建时间
    private String description;//描述
    private int organCount;//组织人数
    private Set<User> users;//用户列表

    public int getOrganId() {
        return organId;
    }

    public void setOrganId(int organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrganCount() {
        return organCount;
    }

    public void setOrganCount(int organCount) {
        this.organCount = organCount;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "organId=" + organId +
                ", organName='" + organName + '\'' +
                ", createTime=" + createTime +
                ", description='" + description + '\'' +
                ", organCount=" + organCount +
                ", users=" + users +
                '}';
    }
}
