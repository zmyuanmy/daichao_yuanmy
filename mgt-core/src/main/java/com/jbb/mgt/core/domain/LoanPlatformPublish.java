package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class LoanPlatformPublish {
    private int id;
    private int platformId;
    private Timestamp publishDate;
    private Timestamp creationDate;
    private boolean isDeleted;
    private Platform platform;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "LoanPlatformPublish [id=" + id + ", platformId=" + platformId + ", publishDate=" + publishDate
            + ", creationDate=" + creationDate + ", isDeleted=" + isDeleted + ", platform=" + platform + "]";
    }

}
