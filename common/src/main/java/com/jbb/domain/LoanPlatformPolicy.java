package com.jbb.domain;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanPlatformPolicy {

    private String version;
    private int recommandCnt;

    private LimitPolicy[] limitPolicy;
    private SourcePolicy[] sourcePolicy;
    private RecommandCntPolicy[] recommandCntPolicy;

    private String[] groups;

    public LimitPolicy[] getLimitPolicy() {
        return limitPolicy;
    }

    public void setLimitPolicy(LimitPolicy[] limitPolicy) {
        this.limitPolicy = limitPolicy;
    }

    public SourcePolicy[] getSourcePolicy() {
        return sourcePolicy;
    }

    public void setSourcePolicy(SourcePolicy[] sourcePolicy) {
        this.sourcePolicy = sourcePolicy;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getRecommandCnt() {
        return recommandCnt;
    }

    public void setRecommandCnt(int recommandCnt) {
        this.recommandCnt = recommandCnt;
    }

    public RecommandCntPolicy[] getRecommandCntPolicy() {
        return recommandCntPolicy;
    }

    public void setRecommandCntPolicy(RecommandCntPolicy[] recommandCntPolicy) {
        this.recommandCntPolicy = recommandCntPolicy;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "LoanPlatformPolicy [version=" + version + ", recommandCnt=" + recommandCnt + ", limitPolicy="
            + Arrays.toString(limitPolicy) + ", sourcePolicy=" + Arrays.toString(sourcePolicy) + ", recommandCntPolicy="
            + Arrays.toString(recommandCntPolicy) + ", groups=" + Arrays.toString(groups) + "]";
    }

}
