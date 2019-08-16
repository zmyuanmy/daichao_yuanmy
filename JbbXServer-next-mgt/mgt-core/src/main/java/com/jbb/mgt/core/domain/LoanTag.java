package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

/**
 * @author wyq
 * @date 2018/8/30 16:13
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanTag {
    private Integer areaTagId;
    private String tagName;
    private Integer areaId;
    private Integer pos;
    private String logoUrl;
    private Timestamp creationDate;
    private Boolean freeze;
    private LoanPlatformTag list;

    public LoanTag() {
        super();
    }

    public LoanTag(Integer areaTagId, String tagName, Integer areaId, Integer pos, String logoUrl,
        Timestamp creationDate) {
        this.areaTagId = areaTagId;
        this.tagName = tagName;
        this.areaId = areaId;
        this.pos = pos;
        this.logoUrl = logoUrl;
        this.creationDate = creationDate;
    }

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

    public LoanPlatformTag getList() {
        return list;
    }

    public void setList(LoanPlatformTag list) {
        this.list = list;
    }

    public Boolean getFreeze() {
        return freeze;
    }

    public void setFreeze(Boolean freeze) {
        this.freeze = freeze;
    }

    @Override
    public String toString() {
        return "LoanTag{" + "areaTagId=" + areaTagId + ", tagName='" + tagName + '\'' + ", areaId=" + areaId + ", pos="
            + pos + ", logoUrl='" + logoUrl + '\'' + ", creationDate=" + creationDate + ", freeze=" + freeze + ", list="
            + list + '}';
    }
}
