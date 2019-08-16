package com.jbb.mgt.server.rs.pojo.request;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

public class ReFinFile {
    private Integer orgId;
    private Integer merchantId;
    private Integer fileType;
    private String fileDateTs; //绑定时间 可以为空
    private String fileName;
    private byte[] fileDate;


    public ReFinFile() {
    }

    public Integer getOrgId() {
        return orgId;
    }

    @FormParam("orgId")
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    @FormParam("merchantId")
    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getFileType() {
        return fileType;
    }

    @FormParam("fileType")
    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getFileDateTs() {
        return fileDateTs;
    }

    @FormParam("fileDateTs")
    public void setFileDateTs(String fileDateTs) {
        this.fileDateTs = fileDateTs;
    }

    public String getFileName() {
        return fileName;
    }

    @FormParam("fileName")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileDate() {
        return fileDate;
    }

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public void setFileDate(byte[] fileDate) {
        this.fileDate = fileDate;
    }
}
