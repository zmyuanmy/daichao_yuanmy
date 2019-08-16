package com.jbb.mgt.core.domain;

/**
 * @author xiaoeach
 * @date 2018/12/27
 */
public class CreditCardCategorie {
    private Integer categoryId;// 分类ID
    private String name;// 类名
    private String desc;// 简要描述
    private String detailMessage;// 详细描述方案
    private String iconUrl;// 图标地址
    private String descColor;// 简要描述颜色，如#556655

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDescColor() {
        return descColor;
    }

    public void setDescColor(String descColor) {
        this.descColor = descColor;
    }

    @Override
    public String toString() {
        return "CreditCardCategorie [categoryId=" + categoryId + ", name=" + name + ", desc=" + desc
            + ", detailMessage=" + detailMessage + ", iconUrl=" + iconUrl + ", descColor=" + descColor + "]";
    }

}
