package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JiGuangNotification {
    private Object platform;
    private Object audience;
    private Notification notification;

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

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    @Override
    public String toString() {
        return "JiGuangNotification [platform=" + platform + ", audience=" + audience + ", notification=" + notification
            + "]";
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Notification {

        private android android;
        private ios ios;

        public android getAndroid() {
            return android;
        }

        public void setAndroid(android android) {
            this.android = android;
        }

        public ios getIos() {
            return ios;
        }

        public void setIos(ios ios) {
            this.ios = ios;
        }

    }

    public static class android {

        private String alert;
        private String title;
        private Object extras;

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

        public Object getExtras() {
            return extras;
        }

        public void setExtras(Object extras) {
            this.extras = extras;
        }

        @Override
        public String toString() {
            return "android [alert=" + alert + ", title=" + title + ", extras=" + extras + "]";
        }

    }

    public static class ios {

        private String alert;
        private String sound;
        private int badge;
        private Object extras;

        public String getAlert() {
            return alert;
        }

        public void setAlert(String alert) {
            this.alert = alert;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public int getBadge() {
            return badge;
        }

        public void setBadge(int badge) {
            this.badge = badge;
        }

        public Object getExtras() {
            return extras;
        }

        public void setExtras(Object extras) {
            this.extras = extras;
        }

        @Override
        public String toString() {
            return "ios [alert=" + alert + ", sound=" + sound + ", badge=" + badge + ", extras=" + extras + "]";
        }

    }

    public static class extras {
        private String platformId;
        private String platformName;
        private String platformUrl;

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
            return "extras [platformId=" + platformId + ", platformName=" + platformName + ", platformUrl="
                + platformUrl + "]";
        }

    }
}