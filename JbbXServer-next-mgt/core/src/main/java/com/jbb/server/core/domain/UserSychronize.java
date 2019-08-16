package com.jbb.server.core.domain;

/**
 * Created by inspironsun on 2018/5/30
 */
public class UserSychronize{

    private int userId;
    private String userName;
    private String idCard;
    private String qq;
    private String wechat;
    private boolean realnameVerified;
    private long creationDate;

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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public boolean isRealnameVerified() {
        return realnameVerified;
    }

    public void setRealnameVerified(boolean realnameVerified) {
        this.realnameVerified = realnameVerified;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "UserSychronize{" + "userId=" + userId + ", userName='" + userName + '\'' + ", idCard='" + idCard + '\''
            + ", qq='" + qq + '\'' + ", wechat='" + wechat + '\'' + ", realnameVerified=" + realnameVerified
            + ", creationDate=" + creationDate + '}';
    }

}
