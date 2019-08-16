package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

/**
 * 商家相关实体类
 *
 * @author wyq
 * @date 2018/7/20 19:05
 */
public class H5Merchant {
    private Integer merchantId;
    private String name;
    private String shortName;
    private String url;
    private String logo;
    private String desc1;
    private String desc2;
    private String desc3;
    private Integer creator;
    private Timestamp creationDate;

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getDesc3() {
        return desc3;
    }

    public void setDesc3(String desc3) {
        this.desc3 = desc3;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public H5Merchant() {
        super();
    }

    public H5Merchant(Integer merchantId, String name, String shortName, String url, String logo, String desc1,
        String desc2, String desc3, Integer creator, Timestamp creationDate) {
        this.merchantId = merchantId;
        this.name = name;
        this.shortName = shortName;
        this.url = url;
        this.logo = logo;
        this.desc1 = desc1;
        this.desc2 = desc2;
        this.desc3 = desc3;
        this.creator = creator;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "H5Merchant{" + "merchantId=" + merchantId + ", name='" + name + '\'' + ", shortName='" + shortName
            + '\'' + ", url='" + url + '\'' + ", logo='" + logo + '\'' + ", desc1='" + desc1 + '\'' + ", desc2='"
            + desc2 + '\'' + ", desc3='" + desc3 + '\'' + ", creator=" + creator + ", creationDate=" + creationDate
            + '}';
    }
}
