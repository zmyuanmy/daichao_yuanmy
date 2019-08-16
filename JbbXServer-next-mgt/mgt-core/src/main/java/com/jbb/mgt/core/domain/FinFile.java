package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

/**
 * Created by inspironsun on 2018/7/20
 */
public class FinFile {

    private int fileId;
    private Integer orgId;
    private Integer merchantId;
    private Integer platformId;
    private String fileName;
    private Integer fileType;
    private int creator;
    private Timestamp creationDate;
    private Timestamp fileDate;
    private boolean isDeleted;

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getFileDate() {
        return fileDate;
    }

    public void setFileDate(Timestamp fileDate) {
        this.fileDate = fileDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    @Override
    public String toString() {
        return "FinFile{" + "fileId=" + fileId + ", orgId=" + orgId + ", merchantId=" + merchantId + ", platformId="
            + platformId + ", fileName='" + fileName + '\'' + ", fileType=" + fileType + ", creator=" + creator
            + ", creationDate=" + creationDate + ", fileDate=" + fileDate + ", isDeleted=" + isDeleted + '}';
    }
}
