package com.jbb.mgt.core.domain;

/**
 * @author ThinkPad
 * @date 2019/03/14
 */
public class PlatformUv {

    private Integer platformId;
    private String platformName;
    private String groupName;
    private Account sales;
    private int clickCnt;
    private int puCnt;
    private int estimatedUvCnt;

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Account getSales() {
        return sales;
    }

    public void setSales(Account sales) {
        this.sales = sales;
    }

    public int getClickCnt() {
        return clickCnt;
    }

    public void setClickCnt(int clickCnt) {
        this.clickCnt = clickCnt;
    }

    public int getPuCnt() {
        return puCnt;
    }

    public void setPuCnt(int puCnt) {
        this.puCnt = puCnt;
    }

    public int getEstimatedUvCnt() {
        return estimatedUvCnt;
    }

    public void setEstimatedUvCnt(int estimatedUvCnt) {
        this.estimatedUvCnt = estimatedUvCnt;
    }

    @Override
    public String toString() {
        return "PlatformUv{" + "platformId=" + platformId + ", platformName='" + platformName + '\'' + ", groupName='"
            + groupName + '\'' + ", sales=" + sales + ", clickCnt=" + clickCnt + ", puCnt=" + puCnt
            + ", estimatedUvCnt=" + estimatedUvCnt + '}';
    }

}
