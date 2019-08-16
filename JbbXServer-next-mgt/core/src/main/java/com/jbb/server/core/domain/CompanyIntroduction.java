package com.jbb.server.core.domain;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jbb.server.shared.rs.Util;

public class CompanyIntroduction {

    private String corporateName;
    private String introduce;
    private String aboutUs;
    private String qqGroup;
    @JsonIgnore
    private Timestamp establishmentTime;

    @JsonProperty("creationDate")
    private String creationDateStr;
    private Long creationDateMs;

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName == null ? null : corporateName.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs == null ? null : aboutUs.trim();
    }

    public String getQqGroup() {
        return qqGroup;
    }

    public void setQqGroup(String qqGroup) {
        this.qqGroup = qqGroup == null ? null : qqGroup.trim();
    }

    public Date getEstablishmentTime() {
        return establishmentTime;
    }

    public void setEstablishmentTime(Timestamp establishmentTime) {
        this.establishmentTime = establishmentTime;
        this.creationDateStr = Util.printDateTime(establishmentTime);
        this.creationDateMs = Util.getTimeMs(establishmentTime);
    }

    public String getCreationDateStr() {
        return creationDateStr;
    }

    public void setCreationDateStr(String creationDateStr) {
        this.creationDateStr = creationDateStr;
    }

    public Long getCreationDateMs() {
        return creationDateMs;
    }


    @Override
    public String toString() {
        return "CompanyIntroduction [corporateName=" + corporateName + ", introduce=" + introduce + ", aboutUs="
            + aboutUs + ", qqGroup=" + qqGroup + ", establishmentTime=" + establishmentTime + "]";
    }

}