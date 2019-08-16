package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserContant {
    private Integer jbbUserId;
    private String phoneNumber;
    private String userName;
    private String vcard;
    private String jsonStr;

 

    public UserContant(Integer jbbUserId, String phoneNumber, String userName) {
        super();
        this.jbbUserId = jbbUserId;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
    }

    public Integer getUserId() {
        return jbbUserId;
    }

    public void setUserId(Integer jbbUserId) {
        this.jbbUserId = jbbUserId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVcard() {
        return vcard;
    }

    public void setVcard(String vcard) {
        this.vcard = vcard;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public UserContant(Integer jbbUserId, String phoneNumber, String userName, String vcard) {
        this.jbbUserId = jbbUserId;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.vcard = vcard;
    }

    public UserContant() {}
}
