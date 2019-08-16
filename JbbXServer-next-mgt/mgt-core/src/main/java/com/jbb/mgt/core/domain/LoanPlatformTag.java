package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class LoanPlatformTag {
    private int platformId;
    private int tagId;
    private int pos;
    private LoanTag area;
    private Platform platform;
    private Timestamp creationDate;

    public LoanPlatformTag() {}

    public LoanPlatformTag(int platformId, int tagId, int pos) {
        super();
        this.platformId = platformId;
        this.tagId = tagId;
        this.pos = pos;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public LoanTag getArea() {
        return area;
    }

    public void setArea(LoanTag area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "LoanPlatformTag [platformId=" + platformId + ", tagId=" + tagId + ", pos=" + pos + ", platform="
            + platform + ", area=" + area + ", creationDate=" + creationDate + "]";
    }

}
