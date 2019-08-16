package com.jbb.mgt.core.domain;

import java.sql.Date;

public class LoanPlatformDetailStatistic {

    private int id;
    private Date statisticDate;
    private int platformId;
    private int productPlatform;// 区域id
    private int categoryId;// 分区id
    private String categoryName;// 分区名称
    private int categoryPos;// 分区位置
    private int pos;// 平台位置
    private String type;// 类型 android ios
    private int clickCnt;
    private int uvCnt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryPos() {
        return categoryPos;
    }

    public void setCategoryPos(int categoryPos) {
        this.categoryPos = categoryPos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getClickCnt() {
        return clickCnt;
    }

    public void setClickCnt(int clickCnt) {
        this.clickCnt = clickCnt;
    }

    public int getUvCnt() {
        return uvCnt;
    }

    public void setUvCnt(int uvCnt) {
        this.uvCnt = uvCnt;
    }

    public int getProductPlatform() {
        return productPlatform;
    }

    public void setProductPlatform(int productPlatform) {
        this.productPlatform = productPlatform;
    }

    @Override
    public String toString() {
        return "LoanPlatformDetailStatistic{" + "id=" + id + ", statisticDate=" + statisticDate + ", platformId="
            + platformId + ", categoryId=" + categoryId + ", categoryName='" + categoryName + '\'' + ", categoryPos="
            + categoryPos + ", pos=" + pos + ", type='" + type + '\'' + ", clickCnt=" + clickCnt + ", uvCnt=" + uvCnt
            + '}';
    }
}
