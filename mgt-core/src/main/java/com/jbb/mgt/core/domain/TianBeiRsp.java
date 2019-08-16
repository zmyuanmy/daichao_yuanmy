package com.jbb.mgt.core.domain;

public class TianBeiRsp {

    public static int SUCCES_CODE = 0;

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "TianBeiRsp [code=" + code + ", msg=" + msg + "]";
    }
    
    

}
