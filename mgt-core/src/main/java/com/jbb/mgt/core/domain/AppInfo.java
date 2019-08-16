package com.jbb.mgt.core.domain;

public class AppInfo {

    private String appId;
    // private String appName;
    private String defaultUrl;
    private String iosUrl;
    private String androidUrl;

    // private String vivoUrl;
    // private String xiaomiUrl;
    // private String oppoUrl;
    // private String huaweiUrl;
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

}
