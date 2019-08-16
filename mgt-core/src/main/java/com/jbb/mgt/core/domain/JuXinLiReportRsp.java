package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JuXinLiReportRsp {
    public static final String SUCCESS_CODE = "API_DATA_PLATFORM_INVOKE_SUCCESS";
    private String code;
    private String message;
    private JuXinLiData data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JuXinLiData getData() {
        return data;
    }

    public void setData(JuXinLiData data) {
        this.data = data;
    }

}
