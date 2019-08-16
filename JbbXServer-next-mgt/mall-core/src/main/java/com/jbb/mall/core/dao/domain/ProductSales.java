package com.jbb.mall.core.dao.domain;

/**
 * 销售统计表 (用于按销量排序)
 *
 * @author wyq
 * @date 2019/1/17 17:58
 */
public class ProductSales {
    private Integer productId;
    private Integer saleCount;

    public ProductSales() {
        super();
    }

    public ProductSales(Integer productId, Integer saleCount) {
        this.productId = productId;
        this.saleCount = saleCount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    @Override
    public String toString() {
        return "ProductSales{" + "productId=" + productId + ", saleCount=" + saleCount + '}';
    }
}
