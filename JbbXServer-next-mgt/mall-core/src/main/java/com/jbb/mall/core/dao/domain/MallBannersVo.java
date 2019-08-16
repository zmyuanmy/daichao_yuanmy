package com.jbb.mall.core.dao.domain;

public class MallBannersVo {

	private Integer Id;
	
	private Integer productId;
	
	private String adImg;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getAdImg() {
		return adImg;
	}

	public void setAdImg(String adImg) {
		this.adImg = adImg;
	}
	
	
}
