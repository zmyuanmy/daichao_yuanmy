package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JuXinLiData {
    private String token;
    private String website;
    private String website_name;
    private String category;
    private String category_name;
    private String status;
    private String message;
    private String create_time;
    private String report_version;
    private Object basic_version;
    private Object enhanced_version;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite_name() {
        return website_name;
    }

    public void setWebsite_name(String website_name) {
        this.website_name = website_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getReport_version() {
        return report_version;
    }

    public void setReport_version(String report_version) {
        this.report_version = report_version;
    }

    public Object getBasic_version() {
        return basic_version;
    }

    public void setBasic_version(Object basic_version) {
        this.basic_version = basic_version;
    }

    public Object getEnhanced_version() {
        return enhanced_version;
    }

    public void setEnhanced_version(Object enhanced_version) {
        this.enhanced_version = enhanced_version;
    }

    @Override
    public String toString() {
        return "JuXinLiData [token=" + token + ", website=" + website + ", website_name=" + website_name + ", category="
            + category + ", category_name=" + category_name + ", status=" + status + ", message=" + message
            + ", create_time=" + create_time + ", report_version=" + report_version + ", basic_version=" + basic_version
            + ", enhanced_version=" + enhanced_version + "]";
    }

}
