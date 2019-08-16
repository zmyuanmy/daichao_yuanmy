package com.jbb.mgt.core.domain;

public class FlashsdkLoginRsp {

    public static String SUCCES_CODE = "200000";
    private String code;
    private String message;
    private Flashsdk data;

    public static String getSUCCES_CODE() {
        return SUCCES_CODE;
    }

    public static void setSUCCES_CODE(String sUCCES_CODE) {
        SUCCES_CODE = sUCCES_CODE;
    }

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

    public Flashsdk getData() {
        return data;
    }

    public void setData(Flashsdk data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FlashsdkLoginRsp [code=" + code + ", message=" + message + ", data=" + data + "]";
    }

}
