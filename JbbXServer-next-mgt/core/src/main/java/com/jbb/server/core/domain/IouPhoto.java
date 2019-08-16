package com.jbb.server.core.domain;

import java.sql.Timestamp;

/**
 * Created by inspironsun on 2018/6/9
 */
public class IouPhoto {
    private int id;
    private String iouCode;
    private int userId;
    private boolean deleted;
    private Timestamp uploadDate;
    private String fileName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIouCode() {
        return iouCode;
    }

    public void setIouCode(String iouCode) {
        this.iouCode = iouCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "IouPhoto{" + "iouCode='" + iouCode + '\'' + ", userId=" + userId + ", deleted=" + deleted
            + ", uploadDate=" + uploadDate + ", fileName='" + fileName + '\'' + '}';
    }
}
