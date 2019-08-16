package com.jbb.mgt.core.domain;

public class AppInfo {

    private String appId;
    private String appName;
    private String defaultUrl;
    private String iosUrl;
    private String androidUrl;
    private String vivoUrl;
    private String xiaomiUrl;
    private String oppoUrl;
    private String huaweiUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public String getIosUrl() {
        return iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public String getVivoUrl() {
        return vivoUrl;
    }

    public void setVivoUrl(String vivoUrl) {
        this.vivoUrl = vivoUrl;
    }

    public String getXiaomiUrl() {
        return xiaomiUrl;
    }

    public void setXiaomiUrl(String xiaomiUrl) {
        this.xiaomiUrl = xiaomiUrl;
    }

    public String getOppoUrl() {
        return oppoUrl;
    }

    public void setOppoUrl(String oppoUrl) {
        this.oppoUrl = oppoUrl;
    }

    public String getHuaweiUrl() {
        return huaweiUrl;
    }

    public void setHuaweiUrl(String huaweiUrl) {
        this.huaweiUrl = huaweiUrl;
    }

    @Override
    public String toString() {
        return "AppInfo [appName=" + appName + ", defaultUrl=" + defaultUrl + ", iosUrl=" + iosUrl + ", androidUrl="
            + androidUrl + ", vivoUrl=" + vivoUrl + ", xiaomiUrl=" + xiaomiUrl + ", oppoUrl=" + oppoUrl + ", huaweiUrl="
            + huaweiUrl + "]";
    }

}
