package com.jbb.mgt.core.domain;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;

public class JiGuangMessage {
    private Object platform;
    private Object audience;
    private String alert;
    private String title;
    private String platformId;
    private String platformName;
    private String platformUrl;

    public Object getPlatform() {
        return platform;
    }

    public void setPlatform(Object platform) {
        this.platform = platform;
    }

    public Object getAudience() {
        return audience;
    }

    public void setAudience(Object audience) {
        this.audience = audience;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformUrl() {
        return platformUrl;
    }

    public void setPlatformUrl(String platformUrl) {
        this.platformUrl = platformUrl;
    }

    @Override
    public String toString() {
        return "JiGuangMessage [platform=" + platform + ", audience=" + audience + ", alert=" + alert + ", title="
            + title + ", platformId=" + platformId + ", platformName=" + platformName + ", platformUrl=" + platformUrl
            + "]";
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Flag {

        private String[] tag;
        private String[] alias;
        private String[] registration_id;
        private String[] tag_and;

        public String[] getTag() {
            return tag;
        }

        public void setTag(String[] tag) {
            this.tag = tag;
        }

        public String[] getAlias() {
            return alias;
        }

        public void setAlias(String[] alias) {
            this.alias = alias;
        }

        public String[] getRegistration_id() {
            return registration_id;
        }

        public void setRegistration_id(String[] registration_id) {
            this.registration_id = registration_id;
        }

        public String[] getTag_and() {
            return tag_and;
        }

        public void setTag_and(String[] tag_and) {
            this.tag_and = tag_and;
        }

        @Override
        public String toString() {
            return "Flag [tag=" + Arrays.toString(tag) + ", alias=" + Arrays.toString(alias) + ", registration_id="
                + Arrays.toString(registration_id) + ", tag_and=" + Arrays.toString(tag_and) + "]";
        }

    }

}
