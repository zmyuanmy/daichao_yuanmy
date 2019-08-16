package com.jbb.mgt.core.domain;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 渠道质量反馈表
 *
 * @author wyq
 * @date 2018/7/31 11:33
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticChannelDaily {
    private Date statisticDate;
    private String channelCode;
    private int price;
    private String channelName;
    private int userType;
    private int clickCnt;
    private int uvCnt;
    private int totalRegisterCnt;
    private int hiddenRegisterCnt;
    private int entryCnt;
    private int hiddenEntryCnt;
    private int sellEntryCnt;
    private int hiddenSellEntryCnt;
    private int h5ClickCnt;
    private int successPageCnt;
    private int downloadClickCnt;
    private int level1Cnt;
    private int level2Cnt;
    private int zhima1Cnt;
    private int age1Cnt;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getClickCnt() {
        return clickCnt;
    }

    public void setClickCnt(int clickCnt) {
        this.clickCnt = clickCnt;
    }

    public int getUvCnt() {
        return uvCnt;
    }

    public void setUvCnt(int uvCnt) {
        this.uvCnt = uvCnt;
    }

    public int getTotalRegisterCnt() {
        return totalRegisterCnt;
    }

    public void setTotalRegisterCnt(int totalRegisterCnt) {
        this.totalRegisterCnt = totalRegisterCnt;
    }

    public int getHiddenRegisterCnt() {
        return hiddenRegisterCnt;
    }

    public void setHiddenRegisterCnt(int hiddenRegisterCnt) {
        this.hiddenRegisterCnt = hiddenRegisterCnt;
    }

    public int getEntryCnt() {
        return entryCnt;
    }

    public void setEntryCnt(int entryCnt) {
        this.entryCnt = entryCnt;
    }

    public int getHiddenEntryCnt() {
        return hiddenEntryCnt;
    }

    public void setHiddenEntryCnt(int hiddenEntryCnt) {
        this.hiddenEntryCnt = hiddenEntryCnt;
    }

    public int getSellEntryCnt() {
        return sellEntryCnt;
    }

    public void setSellEntryCnt(int sellEntryCnt) {
        this.sellEntryCnt = sellEntryCnt;
    }

    public int getHiddenSellEntryCnt() {
        return hiddenSellEntryCnt;
    }

    public void setHiddenSellEntryCnt(int hiddenSellEntryCnt) {
        this.hiddenSellEntryCnt = hiddenSellEntryCnt;
    }

    public int getH5ClickCnt() {
        return h5ClickCnt;
    }

    public void setH5ClickCnt(int h5ClickCnt) {
        this.h5ClickCnt = h5ClickCnt;
    }

    public int getSuccessPageCnt() {
        return successPageCnt;
    }

    public void setSuccessPageCnt(int successPageCnt) {
        this.successPageCnt = successPageCnt;
    }

    public int getDownloadClickCnt() {
        return downloadClickCnt;
    }

    public void setDownloadClickCnt(int downloadClickCnt) {
        this.downloadClickCnt = downloadClickCnt;
    }

    public int getLevel1Cnt() {
        return level1Cnt;
    }

    public void setLevel1Cnt(int level1Cnt) {
        this.level1Cnt = level1Cnt;
    }

    public int getLevel2Cnt() {
        return level2Cnt;
    }

    public void setLevel2Cnt(int level2Cnt) {
        this.level2Cnt = level2Cnt;
    }

    public int getZhima1Cnt() {
        return zhima1Cnt;
    }

    public void setZhima1Cnt(int zhima1Cnt) {
        this.zhima1Cnt = zhima1Cnt;
    }

    public int getAge1Cnt() {
        return age1Cnt;
    }

    public void setAge1Cnt(int age1Cnt) {
        this.age1Cnt = age1Cnt;
    }

    public StatisticChannelDaily() {
        super();
    }

    public StatisticChannelDaily(Date statisticDate, String channelCode, int price, String channelName, int userType,
        int clickCnt, int uvCnt, int totalRegisterCnt, int hiddenRegisterCnt, int entryCnt, int hiddenEntryCnt,
        int sellEntryCnt, int hiddenSellEntryCnt, int h5ClickCnt, int successPageCnt, int downloadClickCnt,
        int level1Cnt, int level2Cnt, int zhima1Cnt, int age1Cnt) {
        this.statisticDate = statisticDate;
        this.channelCode = channelCode;
        this.price = price;
        this.channelName = channelName;
        this.userType = userType;
        this.clickCnt = clickCnt;
        this.uvCnt = uvCnt;
        this.totalRegisterCnt = totalRegisterCnt;
        this.hiddenRegisterCnt = hiddenRegisterCnt;
        this.entryCnt = entryCnt;
        this.hiddenEntryCnt = hiddenEntryCnt;
        this.sellEntryCnt = sellEntryCnt;
        this.hiddenSellEntryCnt = hiddenSellEntryCnt;
        this.h5ClickCnt = h5ClickCnt;
        this.successPageCnt = successPageCnt;
        this.downloadClickCnt = downloadClickCnt;
        this.level1Cnt = level1Cnt;
        this.level2Cnt = level2Cnt;
        this.zhima1Cnt = zhima1Cnt;
        this.age1Cnt = age1Cnt;
    }

    @Override
    public String toString() {
        return "StatisticChannelDaily{" + "statisticDate=" + statisticDate + ", channelCode='" + channelCode + '\''
            + ", price=" + price + ", channelName='" + channelName + '\'' + ", userType=" + userType + ", clickCnt="
            + clickCnt + ", uvCnt=" + uvCnt + ", totalRegisterCnt=" + totalRegisterCnt + ", hiddenRegisterCnt="
            + hiddenRegisterCnt + ", entryCnt=" + entryCnt + ", hiddenEntryCnt=" + hiddenEntryCnt + ", sellEntryCnt="
            + sellEntryCnt + ", hiddenSellEntryCnt=" + hiddenSellEntryCnt + ", h5ClickCnt=" + h5ClickCnt
            + ", successPageCnt=" + successPageCnt + ", downloadClickCnt=" + downloadClickCnt + ", level1Cnt="
            + level1Cnt + ", level2Cnt=" + level2Cnt + ", zhima1Cnt=" + zhima1Cnt + ", age1Cnt=" + age1Cnt + '}';
    }
}