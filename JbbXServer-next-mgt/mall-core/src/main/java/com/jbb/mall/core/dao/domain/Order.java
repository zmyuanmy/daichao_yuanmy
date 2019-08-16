package com.jbb.mall.core.dao.domain;

import java.sql.Timestamp;

/**
 * 商品订单表
 *
 * @author wyq
 * @date 2019/1/17 18:04
 */
public class Order {
    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private Integer price;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private Timestamp deliveryDate;
    private Integer status;
    private String address;
    private String customeName;
    private String cardMsg;
    private String comment;
    private Boolean isDeleted;
    private Product product;
    private ProductImg productImg;

    public Order() {
        super();
    }

    public Order(Integer orderId, Integer userId, Integer productId, Integer price, Timestamp creationDate,
        Timestamp updateDate, Timestamp deliveryDate, Integer status, String address, String customeName,
        String cardMsg, String comment, Boolean isDeleted) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.price = price;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.address = address;
        this.customeName = customeName;
        this.cardMsg = cardMsg;
        this.comment = comment;
        this.isDeleted = isDeleted;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomeName() {
        return customeName;
    }

    public void setCustomeName(String customeName) {
        this.customeName = customeName;
    }

    public String getCardMsg() {
        return cardMsg;
    }

    public void setCardMsg(String cardMsg) {
        this.cardMsg = cardMsg;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductImg getProductImg() {
        return productImg;
    }

    public void setProductImg(ProductImg productImg) {
        this.productImg = productImg;
    }

    @Override
    public String toString() {
        return "Orders{" + "orderId=" + orderId + ", userId=" + userId + ", productId=" + productId + ", price=" + price
            + ", creationDate=" + creationDate + ", updateDate=" + updateDate + ", deliveryDate=" + deliveryDate
            + ", status=" + status + ", address='" + address + '\'' + ", customeName='" + customeName + '\''
            + ", cardMsg='" + cardMsg + '\'' + ", comment='" + comment + '\'' + ", isDeleted='" + isDeleted + '\''
            + '}';
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
