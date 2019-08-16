package com.jbb.server.core.domain;

import java.sql.Timestamp;

/**
 * Created by inspironsun on 2018/6/9
 */
public class UserPhotoFile {

    private int fileId;
    private int userId;
    private Timestamp uploadDate;
    private String fileName;
    private String fileUrl;
    private String userType;

    public UserPhotoFile(int fileId, int userId, Timestamp uploadDate, String fileName, String fileUrl, String userType) {
        this.fileId = fileId;
        this.userId = userId;
        this.uploadDate = uploadDate;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.userType = userType;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserPhotoFile{" + "fileId=" + fileId + ", userId=" + userId + ", uploadDate=" + uploadDate
            + ", fileName='" + fileName + '\'' + ", fileUrl='" + fileUrl + '\'' + ", userType='" + userType + '\''
            + '}';
    }
}
