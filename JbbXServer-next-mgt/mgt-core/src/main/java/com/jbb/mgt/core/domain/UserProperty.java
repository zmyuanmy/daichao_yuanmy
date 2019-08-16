package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

import com.jbb.server.common.util.DateUtil;

public class UserProperty {
    private Integer userId;
    private String name;
    private String value;
    private Boolean isHidden;
    private Timestamp updateDate;

    public UserProperty() {}

    public UserProperty(Integer userId, String name, String value, Boolean isHidden, Timestamp updateDate) {
        super();
        this.userId = userId;
        this.name = name;
        this.value = value;
        this.isHidden = isHidden;
        this.updateDate = updateDate;
    }

    public UserProperty(Integer userId, String name, String value) {
        super();
        this.userId = userId;
        this.name = name;
        this.value = value;
        this.isHidden = false;
        this.updateDate = DateUtil.getCurrentTimeStamp();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "UserProperty [userId=" + userId + ", name=" + name + ", value=" + value + ", isHidden=" + isHidden
            + ", updateDate=" + updateDate + "]";
    }

}
