package com.jbb.mall.core.dao.domain;

import java.util.List;


public class FlowerProduct {
	
	private Integer typeId;
		
	private String title;
	
	private String desc;
	
	private String adImg;
	
	private String moreImg;
	
	private List<ProductsVo> products;

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
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

	public String getAdImg() {
		return adImg;
	}

	public void setAdImg(String adImg) {
		this.adImg = adImg;
	}

	public String getMoreImg() {
		return moreImg;
	}

	public void setMoreImg(String moreImg) {
		this.moreImg = moreImg;
	}

	public List<ProductsVo> getProducts() {
		return products;
	}

	public void setProducts(List<ProductsVo> products) {
		this.products = products;
	}
	
	
	
	
 
}
