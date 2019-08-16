package com.jbb.mgt.core.domain;

public class RegeoRsp {

    public static int SUCCES_CODE = 1;

    private int status;
    private String info;
    private Regeocodes regeocode;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Regeocodes getRegeocode() {
        return regeocode;
    }

    public void setRegeocode(Regeocodes regeocode) {
        this.regeocode = regeocode;
    }

    @Override
    public String toString() {
        return "RegeoRsp [status=" + status + ", info=" + info + ", regeocode=" + regeocode + "]";
    }

}
