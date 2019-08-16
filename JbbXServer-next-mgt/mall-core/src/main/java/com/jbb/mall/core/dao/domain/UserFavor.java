package com.jbb.mall.core.dao.domain;

import java.sql.Timestamp;

/**
 * 用户收藏列表
 *
 * @author wyq
 * @date 2019/1/17 18:00
 */
public class UserFavor {
    private Integer userId;
    private Integer productId;
    private Timestamp creationDate;
    private Integer status;
    private Timestamp removeDate;

    public UserFavor() {
        super();
    }

    public UserFavor(Integer userId, Integer productId, Timestamp creationDate, Integer status,
        Timestamp removeDate) {
        this.userId = userId;
        this.productId = productId;
        this.creationDate = creationDate;
        this.status = status;
        this.removeDate = removeDate;
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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(Timestamp removeDate) {
        this.removeDate = removeDate;
    }

    @Override
    public String toString() {
        return "UserFavorites{" + "userId=" + userId + ", productId=" + productId + ", creationDate=" + creationDate
            + ", status=" + status + ", removeDate=" + removeDate + '}';
    }
}
