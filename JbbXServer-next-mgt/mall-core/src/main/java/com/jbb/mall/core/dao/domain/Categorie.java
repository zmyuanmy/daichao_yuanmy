package com.jbb.mall.core.dao.domain;

import java.sql.Timestamp;

/**
 * 商城商品类别
 * 
 * @author wyq
 * @date 2019/1/17 17:43
 */
public class Categorie {
    private Integer categoryId;
    private String classification;
    private Integer indexNo;
    private String title;
    private String desc;
    private Timestamp creationDate;
    private String productType;
    private String adImg;

    public Categorie() {
        super();
    }

    public Categorie(Integer categoryId, String classification, Integer indexNo, String title, String desc,
        Timestamp creationDate, String productType, String adImg) {
        this.categoryId = categoryId;
        this.classification = classification;
        this.indexNo = indexNo;
        this.title = title;
        this.desc = desc;
        this.creationDate = creationDate;
        this.productType = productType;
        this.adImg = adImg;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getAdImg() {
        return adImg;
    }

    public void setAdImg(String adImg) {
        this.adImg = adImg;
    }

    @Override
    public String toString() {
        return "Categories{" + "categoryId=" + categoryId + ", classification='" + classification + '\'' + ", indexNo="
            + indexNo + ", title='" + title + '\'' + ", desc='" + desc + '\'' + ", creationDate=" + creationDate
            + ", productType='" + productType + '\'' + ", adImg='" + adImg + '\'' + '}';
    }
}
