package com.jbb.mall.core.dao.domain;

import java.security.Timestamp;

/**
 * 商城商品图片
 *
 * @author wyq
 * @date 2019/1/17 17:53
 */
public class ProductImg {
    private Integer productId;
    private String imgUrl;
    private Integer indexNo;
    private Timestamp creationDate;

    public ProductImg() {
        super();
    }

    public ProductImg(Integer productId, String imgUrl, Integer indexNo, Timestamp creationDate) {
        this.productId = productId;
        this.imgUrl = imgUrl;
        this.indexNo = indexNo;
        this.creationDate = creationDate;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "ProductImgs{" + "productId=" + productId + ", imgUrl='" + imgUrl + '\'' + ", indexNo=" + indexNo
            + ", creationDate=" + creationDate + '}';
    }
}
