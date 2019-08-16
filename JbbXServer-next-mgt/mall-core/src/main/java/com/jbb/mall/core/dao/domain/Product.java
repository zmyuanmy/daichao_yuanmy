package com.jbb.mall.core.dao.domain;

import java.sql.Timestamp;

/**
 * 商城商品
 *
 * @author wyq
 * @date 2019/1/17 17:49
 */
public class Product {
    private Integer productId;
    private String productType;
    private String title;
    private Timestamp creationDate;
    private Integer price;
    private Integer discountPrice;
    private String desc;
    private String content;
    private Integer favorStatus;
    private ProductImg productImg;

    public Product() {
        super();
    }

    public Product(Integer productId, String productType, String title, Timestamp creationDate, Integer price,
        Integer discountPrice, String desc, String content) {
        this.productId = productId;
        this.productType = productType;
        this.title = title;
        this.creationDate = creationDate;
        this.price = price;
        this.discountPrice = discountPrice;
        this.desc = desc;
        this.content = content;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFavorStatus() {
        return favorStatus;
    }

    public void setFavorStatus(Integer favorStatus) {
        this.favorStatus = favorStatus;
    }

    public ProductImg getProductImg() {
        return productImg;
    }

    public void setProductImg(ProductImg productImg) {
        this.productImg = productImg;
    }

    @Override
    public String toString() {
        return "Products{" + "productId=" + productId + ", productType='" + productType + '\'' + ", title='" + title
            + '\'' + ", creationDate=" + creationDate + ", price=" + price + ", discountPrice=" + discountPrice
            + ", desc='" + desc + '\'' + ", content='" + content + '\'' + '}';
    }
}
