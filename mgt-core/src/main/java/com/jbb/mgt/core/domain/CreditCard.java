package com.jbb.mgt.core.domain;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author xiaoeach
 * @date 2018/12/27
 */
public class CreditCard {
    private int creditId;// 自增 pk
    private String bankCode;// 银行编码缩写
    private String bankName;// 银行名称
    private String bankUrl;// 银行信用卡资料填写地址
    private String cardImgUrl;// 卡片图标地址
    private String tagName;// 标签
    private String tagColor;// 颜色
    private String creditShortName;// 卡片短名字
    private String creditName;// 卡片完整名字
    private String creditDesc;// 简要描述
    private String giftLogo;// 礼物logo地址
    private String giftDesc;// 礼物说明
    private String detailedDesc;// 详细说明
    private Integer weight;// 权重 -- 用于卡片筛选时排序，运营子系统可调节
    private Boolean isDeleted;// 是否删除
    private Timestamp creationDate;// 创建日期
    private Integer applyCnt;// 申请人数
    private List<CreditCardCategorie> cardCategories;
    private List<City> citys;

    public int getCreditId() {
        return creditId;
    }

    public void setCreditId(int creditId) {
        this.creditId = creditId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl;
    }

    public String getCardImgUrl() {
        return cardImgUrl;
    }

    public void setCardImgUrl(String cardImgUrl) {
        this.cardImgUrl = cardImgUrl;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }

    public String getCreditShortName() {
        return creditShortName;
    }

    public void setCreditShortName(String creditShortName) {
        this.creditShortName = creditShortName;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public String getCreditDesc() {
        return creditDesc;
    }

    public void setCreditDesc(String creditDesc) {
        this.creditDesc = creditDesc;
    }

    public String getGiftLogo() {
        return giftLogo;
    }

    public void setGiftLogo(String giftLogo) {
        this.giftLogo = giftLogo;
    }

    public String getGiftDesc() {
        return giftDesc;
    }

    public void setGiftDesc(String giftDesc) {
        this.giftDesc = giftDesc;
    }

    public String getDetailedDesc() {
        return detailedDesc;
    }

    public void setDetailedDesc(String detailedDesc) {
        this.detailedDesc = detailedDesc;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getApplyCnt() {
        return applyCnt;
    }

    public void setApplyCnt(Integer applyCnt) {
        this.applyCnt = applyCnt;
    }

    public List<CreditCardCategorie> getCardCategories() {
        return cardCategories;
    }

    public void setCardCategories(List<CreditCardCategorie> cardCategories) {
        this.cardCategories = cardCategories;
    }

    public List<City> getCitys() {
        return citys;
    }

    public void setCitys(List<City> citys) {
        this.citys = citys;
    }

    @Override
    public String toString() {
        return "CreditCard [creditId=" + creditId + ", bankCode=" + bankCode + ", bankName=" + bankName + ", bankUrl="
            + bankUrl + ", cardImgUrl=" + cardImgUrl + ", tagName=" + tagName + ", tagColor=" + tagColor
            + ", creditShortName=" + creditShortName + ", creditName=" + creditName + ", creditDesc=" + creditDesc
            + ", giftLogo=" + giftLogo + ", giftDesc=" + giftDesc + ", detailedDesc=" + detailedDesc + ", weight="
            + weight + ", isDeleted=" + isDeleted + ", creationDate=" + creationDate + "]";
    }

}
