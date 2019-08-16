package com.jbb.mgt.core.domain;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author lishaojie
 * @title: ChannelAppFunnel
 * @projectName JBBXServer
 * @description: APP注册分析
 * @date 2019/5/11
 */
public class ChannelAppFunnel {
    private Date statisticDate;// '统计时间'
    private String channelCode;// '渠道编号 '
    private String channelName;// 渠道名称
    private Integer loginNum;// 总登录数
    private Integer loginNumNew;// 新用户登录数
    private Integer loginNumOld;// 老用户登录数
    private Integer uvCnt;// UV数
    private Integer uvCntNew;// 新用户UV数
    private Integer uvCntOld;// 老用户UV数
    private Integer puvCnt;// 老用户登录数
    private Integer puvCntNew;// 新用户产品UV数
    private Integer puvCntOld;// 老用户产品UV数
    private Account salesAccount;// 商务信息

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

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

    public Integer getLoginNumNew() {
        return loginNumNew;
    }

    public void setLoginNumNew(Integer loginNumNew) {
        this.loginNumNew = loginNumNew;
    }

    public Integer getLoginNumOld() {
        return loginNumOld;
    }

    public void setLoginNumOld(Integer loginNumOld) {
        this.loginNumOld = loginNumOld;
    }

    public Integer getUvCnt() {
        return uvCnt;
    }

    public void setUvCnt(Integer uvCnt) {
        this.uvCnt = uvCnt;
    }

    public Integer getUvCntNew() {
        return uvCntNew;
    }

    public void setUvCntNew(Integer uvCntNew) {
        this.uvCntNew = uvCntNew;
    }

    public Integer getUvCntOld() {
        return uvCntOld;
    }

    public void setUvCntOld(Integer uvCntOld) {
        this.uvCntOld = uvCntOld;
    }

    public Integer getPuvCnt() {
        return puvCnt;
    }

    public void setPuvCnt(Integer puvCnt) {
        this.puvCnt = puvCnt;
    }

    public Integer getPuvCntNew() {
        return puvCntNew;
    }

    public void setPuvCntNew(Integer puvCntNew) {
        this.puvCntNew = puvCntNew;
    }

    public Integer getPuvCntOld() {
        return puvCntOld;
    }

    public void setPuvCntOld(Integer puvCntOld) {
        this.puvCntOld = puvCntOld;
    }

    public Account getSalesAccount() {
        return salesAccount;
    }

    public void setSalesAccount(Account salesAccount) {
        this.salesAccount = salesAccount;
    }

    @Override
    public String toString() {
        return "ChannelAppFunnel{" + "statisticDate=" + statisticDate + ", channelCode='" + channelCode + '\''
            + ", channelName='" + channelName + '\'' + ", loginNum=" + loginNum + ", loginNumNew=" + loginNumNew
            + ", loginNumOld=" + loginNumOld + ", uvCnt=" + uvCnt + ", uvCntNew=" + uvCntNew + ", uvCntOld=" + uvCntOld
            + ", puvCnt=" + puvCnt + ", puvCntNew=" + puvCntNew + ", puvCntOld=" + puvCntOld + ", salesAccount="
            + salesAccount + '}';
    }
}
