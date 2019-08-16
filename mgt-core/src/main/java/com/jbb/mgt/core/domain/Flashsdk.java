package com.jbb.mgt.core.domain;

public class Flashsdk {
    private String mobileName;
    private String tradeNo;

    public String getMobileName() {
        return mobileName;
    }

    public void setMobileName(String mobileName) {
        this.mobileName = mobileName;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Override
    public String toString() {
        return "Flashsdk [mobileName=" + mobileName + ", tradeNo=" + tradeNo + "]";
    }
}
