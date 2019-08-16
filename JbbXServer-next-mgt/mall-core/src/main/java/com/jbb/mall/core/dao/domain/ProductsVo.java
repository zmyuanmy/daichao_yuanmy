package com.jbb.mall.core.dao.domain;

import com.jbb.server.common.util.StringUtil;

public class ProductsVo {

	private Integer productId;

	private Integer price;

	private Integer discountPrice;

	private String productName;

	private String desc;

	private Integer status;
	
	private String adImg;
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getAdImg() {
		if(!StringUtil.isEmpty(adImg) && null!= adImg) {
			String [] imgArray = adImg.split(",");
			adImg = imgArray[0];
		}
		return adImg;
	}

	public void setAdImg(String adImg) {		
		this.adImg = adImg;
	}

}
