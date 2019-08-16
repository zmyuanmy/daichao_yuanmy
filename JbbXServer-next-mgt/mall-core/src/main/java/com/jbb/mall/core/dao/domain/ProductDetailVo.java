package com.jbb.mall.core.dao.domain;

import java.sql.Timestamp;
import java.util.List;

public class ProductDetailVo {

	private Integer productId;
	private String productType;
	private String title;
	private Timestamp creationDate;
	private Integer price;
	private Integer discountPrice;
	private String desc;
	private String content;
	private Integer favorStatus;
	private List<String> productImg;

	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public List<String> getProductImg() {
		return productImg;
	}

	public void setProductImg(List<String> productImg) {
		this.productImg = productImg;
	}

}
