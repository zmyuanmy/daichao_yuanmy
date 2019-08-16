package com.jbb.mgt.core.domain;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class  AreaPlatformVo {
    private Integer areaTagId;
    private String tagName;
    private Integer areaId;
    private Integer pos;
    private String logoUrl;
    private Timestamp creationDate;
    private List<Platform> platforms;

    public AreaPlatformVo() {}

    

    public Integer getAreaTagId() {
        return areaTagId;
    }

    public void setAreaTagId(Integer areaTagId) {
        this.areaTagId = areaTagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    @Override
    public String toString() {
        return "AreaPlatformVo [areaTagId=" + areaTagId + ", tagName=" + tagName + ", areaId=" + areaId + ", pos=" + pos
            + ", logoUrl=" + logoUrl + ", creationDate=" + creationDate + ", platforms=" + platforms + "]";
    }

}
