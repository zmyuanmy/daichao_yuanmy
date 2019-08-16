package com.jbb.mgt.core.domain;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginPuv {
    private Date statisticDate;
    private String channelCode;
    private String channelName;
    private int price;
    private int appLoginCnt1;
    private int appLoginCnt7;
    private int appLoginCnt15;
    private int appLoginCnt30;
    private int pUv1;
    private int pUv7;
    private int pUv15;
    private int pUv30;
    private int registerCnt1;
    private int registerCnt7;
    private int registerCnt15;
    private int registerCnt30;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public int getAppLoginCnt1() {
        return appLoginCnt1;
    }

    public void setAppLoginCnt1(int appLoginCnt1) {
        this.appLoginCnt1 = appLoginCnt1;
    }

    public int getAppLoginCnt7() {
        return appLoginCnt7;
    }

    public void setAppLoginCnt7(int appLoginCnt7) {
        this.appLoginCnt7 = appLoginCnt7;
    }

    public int getAppLoginCnt15() {
        return appLoginCnt15;
    }

    public void setAppLoginCnt15(int appLoginCnt15) {
        this.appLoginCnt15 = appLoginCnt15;
    }

    public int getAppLoginCnt30() {
        return appLoginCnt30;
    }

    public void setAppLoginCnt30(int appLoginCnt30) {
        this.appLoginCnt30 = appLoginCnt30;
    }

    public int getpUv1() {
        return pUv1;
    }

    public void setpUv1(int pUv1) {
        this.pUv1 = pUv1;
    }

    public int getpUv7() {
        return pUv7;
    }

    public void setpUv7(int pUv7) {
        this.pUv7 = pUv7;
    }

    public int getpUv15() {
        return pUv15;
    }

    public void setpUv15(int pUv15) {
        this.pUv15 = pUv15;
    }

    public int getpUv30() {
        return pUv30;
    }

    public void setpUv30(int pUv30) {
        this.pUv30 = pUv30;
    }

    public int getRegisterCnt1() {
        return registerCnt1;
    }

    public void setRegisterCnt1(int registerCnt1) {
        this.registerCnt1 = registerCnt1;
    }

    public int getRegisterCnt7() {
        return registerCnt7;
    }

    public void setRegisterCnt7(int registerCnt7) {
        this.registerCnt7 = registerCnt7;
    }

    public int getRegisterCnt15() {
        return registerCnt15;
    }

    public void setRegisterCnt15(int registerCnt15) {
        this.registerCnt15 = registerCnt15;
    }

    public int getRegisterCnt30() {
        return registerCnt30;
    }

    public void setRegisterCnt30(int registerCnt30) {
        this.registerCnt30 = registerCnt30;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "UserLoginPuv [statisticDate=" + statisticDate + ", channelCode=" + channelCode + ", price=" + price
            + ", appLoginCnt1=" + appLoginCnt1 + ", appLoginCnt7=" + appLoginCnt7 + ", appLoginCnt15=" + appLoginCnt15
            + ", appLoginCnt30=" + appLoginCnt30 + ", pUv1=" + pUv1 + ", pUv7=" + pUv7 + ", pUv15=" + pUv15 + ", pUv30="
            + pUv30 + ", registerCnt1=" + registerCnt1 + ", registerCnt7=" + registerCnt7 + ", registerCnt15="
            + registerCnt15 + ", registerCnt30=" + registerCnt30 + "]";
    }

}
