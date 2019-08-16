package com.jbb.mgt.core.domain;

import java.sql.Date;

/**
 * @author lishaojie
 * @title: IpAddressStatistic
 * @projectName JBBXServer
 * @description: IP信息
 * @date 2019/5/28
 */
public class IpAddressStatistic {

    private Date statisticDate;// '统计时间'
    private String ipAddress;// IP地址
    private int uvCnt;// UV
    private int registerCnt;// 注册数
    private int appLoginCnt;// APP登录数
    private int downloadClickCnt;// 下载点击数
    private int pUvCnt;// 产品用户Uv数
    private int pClickCnt;// 产品点击pv数
    private String channelSale;// 涉及渠道和商务

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getUvCnt() {
        return uvCnt;
    }

    public void setUvCnt(int uvCnt) {
        this.uvCnt = uvCnt;
    }

    public int getRegisterCnt() {
        return registerCnt;
    }

    public void setRegisterCnt(int registerCnt) {
        this.registerCnt = registerCnt;
    }

    public int getAppLoginCnt() {
        return appLoginCnt;
    }

    public void setAppLoginCnt(int appLoginCnt) {
        this.appLoginCnt = appLoginCnt;
    }

    public int getDownloadClickCnt() {
        return downloadClickCnt;
    }

    public void setDownloadClickCnt(int downloadClickCnt) {
        this.downloadClickCnt = downloadClickCnt;
    }

    public int getpUvCnt() {
        return pUvCnt;
    }

    public void setpUvCnt(int pUvCnt) {
        this.pUvCnt = pUvCnt;
    }

    public int getpClickCnt() {
        return pClickCnt;
    }

    public void setpClickCnt(int pClickCnt) {
        this.pClickCnt = pClickCnt;
    }

    public String getChannelSale() {
        return channelSale;
    }

    public void setChannelSale(String channelSale) {
        this.channelSale = channelSale;
    }

    @Override
    public String toString() {
        return "IpAddressStatistic{" + "statisticDate=" + statisticDate + ", ipAddress='" + ipAddress + '\''
            + ", uvCnt=" + uvCnt + ", registerCnt=" + registerCnt + ", appLoginCnt=" + appLoginCnt
            + ", downloadClickCnt=" + downloadClickCnt + ", pUvCnt=" + pUvCnt + ", pClickCnt=" + pClickCnt
            + ", channelSale='" + channelSale + '\'' + '}';
    }
}
