package com.jbb.mall.core.dao.domain;

import java.util.List;

/**
 * 分类方式
 * 
 * @author wyq
 * @date 2019/1/17 17:41
 */
public class Classification {
    private String classification;
    private String desc;
    private String imgUrl;
    private List<Categorie> categories;

    public Classification() {
        super();
    }

    public Classification(String classification, String desc, String imgUrl, List<Categorie> categories) {
        this.classification = classification;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.categories = categories;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Classification{" + "classification='" + classification + '\'' + ", desc='" + desc + '\'' + ", imgUrl='"
            + imgUrl + '\'' + ", categories=" + categories + '}';
    }
}
