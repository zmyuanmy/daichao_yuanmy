package com.jbb.mall.core.dao.domain;

import java.sql.Timestamp;

/**
 * 广告轮播图
 *
 * @author wyq
 * @date 2019/1/17 17:38
 */
public class Banner {
    private Integer bannnerId;
    private Integer indexNo;
    private String adImg;
    private Timestamp creationDate;
    private String productType;
    private Integer productId;

    public Banner() {
        super();
    }

    public Banner(Integer bannnerId, Integer indexNo, String adImg, Timestamp creationDate, String productType,
        Integer productId) {
        this.bannnerId = bannnerId;
        this.indexNo = indexNo;
        this.adImg = adImg;
        this.creationDate = creationDate;
        this.productType = productType;
        this.productId = productId;
    }

    public Integer getBannnerId() {
        return bannnerId;
    }

    public void setBannnerId(Integer bannnerId) {
        this.bannnerId = bannnerId;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getAdImg() {
        return adImg;
    }

    public void setAdImg(String adImg) {
        this.adImg = adImg;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Banners{" + "bannnerId=" + bannnerId + ", indexNo=" + indexNo + ", adImg='" + adImg + '\''
            + ", creationDate=" + creationDate + ", productType='" + productType + '\'' + ", productId=" + productId
            + '}';
    }
}
