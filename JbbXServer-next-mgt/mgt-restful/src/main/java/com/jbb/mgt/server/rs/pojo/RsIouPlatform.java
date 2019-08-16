package com.jbb.mgt.server.rs.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.IouPlatform;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsIouPlatform {
    private Integer iouPlatformId;
    private String platformName;
    private String description;

    public RsIouPlatform() {
        super();
    }

    public RsIouPlatform(IouPlatform IouPlatform) {
        this.iouPlatformId = IouPlatform.getIouPlatformId();
        this.platformName = IouPlatform.getPlatformName();
        this.description = IouPlatform.getDescription();
    }

    public Integer getIouPlatformId() {
        return iouPlatformId;
    }

    public void setIouPlatformId(Integer iouPlatformId) {
        this.iouPlatformId = iouPlatformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
