package com.jbb.mgt.core.domain;

public class EosTokenRefReward {
    private String token;// token
    private Double refPercent;// 推荐人账号返EOS百分比
    private Double tokenPercent;// 挖矿账号返币百分比
    private Double refTokenPercent;// 推荐人账号返币百分比

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getRefPercent() {
        return refPercent;
    }

    public void setRefPercent(Double refPercent) {
        this.refPercent = refPercent;
    }

    public Double getTokenPercent() {
        return tokenPercent;
    }

    public void setTokenPercent(Double tokenPercent) {
        this.tokenPercent = tokenPercent;
    }

    public Double getRefTokenPercent() {
        return refTokenPercent;
    }

    public void setRefTokenPercent(Double refTokenPercent) {
        this.refTokenPercent = refTokenPercent;
    }

    @Override
    public String toString() {
        return "EosTokenRefReward [token=" + token + ", refPercent=" + refPercent + ", tokenPercent=" + tokenPercent
            + ", refTokenPercent=" + refTokenPercent + "]";
    }

}
