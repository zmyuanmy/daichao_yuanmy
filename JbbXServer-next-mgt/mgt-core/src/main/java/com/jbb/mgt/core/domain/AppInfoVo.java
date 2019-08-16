package com.jbb.mgt.core.domain;

/**
 * App信息实体类
 *
 * @author wyq
 * @date 2019/1/5 18:04
 */
public class AppInfoVo {
    private String appName;
    private String appDesc;
    private String wechat;
    private String phoneNumber;
    private String contactName;
    private String companyName;
    private String companyDesc;
    private String qq;

    public AppInfoVo() {
        super();
    }

    public AppInfoVo(String appName, String appDesc, String wechat, String phoneNumber, String contactName,
        String companyName, String companyDesc, String qq) {
        this.appName = appName;
        this.appDesc = appDesc;
        this.wechat = wechat;
        this.phoneNumber = phoneNumber;
        this.contactName = contactName;
        this.companyName = companyName;
        this.companyDesc = companyDesc;
        this.qq = qq;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public String toString() {
        return "AppInfoVo{" + "appName='" + appName + '\'' + ", appDesc='" + appDesc + '\'' + ", wechat='" + wechat
            + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", contactName='" + contactName + '\'' + ", companyName='"
            + companyName + '\'' + ", companyDesc='" + companyDesc + '\'' + ", qq='" + qq + '\'' + '}';
    }
}