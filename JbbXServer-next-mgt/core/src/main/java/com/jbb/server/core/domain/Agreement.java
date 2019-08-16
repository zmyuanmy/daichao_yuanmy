package com.jbb.server.core.domain;

import java.sql.Timestamp;

public class Agreement {
    private int userId;
    private String agreement;
    private String version;
    private Timestamp readDate;

    public Agreement() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Timestamp getReadDate() {
        return readDate;
    }

    public void setReadDate(Timestamp readDate) {
        this.readDate = readDate;
    }

    @Override
    public String toString() {
        return "Agreement [userId=" + userId + ", agreement=" + agreement + ", version=" + version + ", readDate="
            + readDate + "]";
    }
}
