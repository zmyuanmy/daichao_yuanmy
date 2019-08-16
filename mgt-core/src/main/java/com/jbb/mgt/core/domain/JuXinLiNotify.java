package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JuXinLiNotify {

    private String user_id;// 机构创建认证任务时传入的 USER_ID 如果没有传递，为 null

    private String state;

    private String token;// 授权认证流水号

    private String website;// 授权网站英文名称

    private String website_name;// 授权网站中文名称

    private String category;// 授权网站分类英文名称

    private String category_name;// 授权网站分类中文名称

    private String message;// 通知消息体

    private boolean success;// 通知类型（成功true、失败false）

    private long create_time;// 创建时间

    // private Map additional_info;// 预留的扩展字段（JSONObject）

    private SimpleAuthFlowEnum phase;// 当前所处阶段

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public SimpleAuthFlowEnum getPhase() {
        return phase;
    }

    public void setPhase(SimpleAuthFlowEnum phase) {
        this.phase = phase;
    }

    public enum SimpleAuthFlowEnum {
        CREATE, // 任务创建
        AUTHORIZE, // 认证
        COLLECT, // 采集
        PARSE, // 解析
        REPORT;// 报告
    }

    @Override
    public String toString() {
        return "JuXinLiNotify [user_id=" + user_id + ", token=" + token + ", website=" + website + ", website_name="
            + website_name + ", category=" + category + ", category_name=" + category_name + ", message=" + message
            + ", success=" + success + ", create_time=" + create_time + ", phase=" + phase + "]";
    }

}
