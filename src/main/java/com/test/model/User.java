package com.test.model;

import java.util.Date;
import java.util.Set;

/**
 * Created by hunger on 2016/11/5.
 */
public class User {
    private int userId;//用户id
    private String userName;//昵称
    private String email;//邮箱
    private String password;//密码
    private String phone;//电话
    private String school;//学校
    private int isWeChat;//是否关联微信
    private String openid;//微信凭证
    private Date createTime;//创建时间
    private Set<Organization> organs;//组织列表

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getIsWeChat() {
        return isWeChat;
    }

    public void setIsWeChat(int isWeChat) {
        this.isWeChat = isWeChat;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Set<Organization> getOrgans() {
        return organs;
    }

    public void setOrgans(Set<Organization> organs) {
        this.organs = organs;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", school='" + school + '\'' +
                ", isWeChat=" + isWeChat +
                ", openid='" + openid + '\'' +
                ", createTime=" + createTime +
                ", organs=" + organs +
                '}';
    }
}
