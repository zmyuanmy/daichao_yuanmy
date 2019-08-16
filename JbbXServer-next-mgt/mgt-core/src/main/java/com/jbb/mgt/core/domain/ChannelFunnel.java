package com.jbb.mgt.core.domain;

import org.springframework.security.access.method.P;

import java.math.BigDecimal;
import java.sql.Date;

public class ChannelFunnel {
    private Date statisticDate;// '统计时间'
    private String channelCode;// '渠道编号 '
    private String channelName;
    private Integer price;
    private Integer mode;
    private int pv;// PV
    private int uv;
    private int registerUv; // 'UV'
    private double registerP;
    private int addmoreSubmitCnt; // '资料提交数'
    private double addmoreSubmitP;
    private int successDownloadCnt;// '下载数'
    private double successDownloadP;
    private int pUv; // '产品UV'
    private int addmoreInitCnt;// '资料页PV'
    private int successInitCnt;// '成功页弹窗数'
    private int appLoginCnt;// '登录数'
    private int pClickCnt;// '贷超PV'
    private int pClickUv; // '贷超UV'
    private int channleSettlementCnt;
    private double pUvCost; // '产品UV成本'
    private BigDecimal registerUvP;
    private BigDecimal channleSettlementCntP;
    private BigDecimal successDownloadCntP;
    private BigDecimal appLoginCntP;
    private BigDecimal appLoginCntHideP;
    private BigDecimal pClickUvP;
    private BigDecimal pClickUvHideP;
    private BigDecimal pUvP;
    private BigDecimal pUvHideP;
    private Account salesAccount;
    private String style;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public int getRegisterUv() {
        return registerUv;
    }

    public void setRegisterUv(int registerUv) {
        this.registerUv = registerUv;
    }

    public double getRegisterP() {
        return registerP;
    }

    public void setRegisterP(double registerP) {
        this.registerP = registerP;
    }

    public int getAddmoreSubmitCnt() {
        return addmoreSubmitCnt;
    }

    public void setAddmoreSubmitCnt(int addmoreSubmitCnt) {
        this.addmoreSubmitCnt = addmoreSubmitCnt;
    }

    public double getAddmoreSubmitP() {
        return addmoreSubmitP;
    }

    public void setAddmoreSubmitP(double addmoreSubmitP) {
        this.addmoreSubmitP = addmoreSubmitP;
    }

    public int getSuccessDownloadCnt() {
        return successDownloadCnt;
    }

    public void setSuccessDownloadCnt(int successDownloadCnt) {
        this.successDownloadCnt = successDownloadCnt;
    }

    public double getSuccessDownloadP() {
        return successDownloadP;
    }

    public void setSuccessDownloadP(double successDownloadP) {
        this.successDownloadP = successDownloadP;
    }

    public int getpUv() {
        return pUv;
    }

    public void setpUv(int pUv) {
        this.pUv = pUv;
    }

    public int getAddmoreInitCnt() {
        return addmoreInitCnt;
    }

    public void setAddmoreInitCnt(int addmoreInitCnt) {
        this.addmoreInitCnt = addmoreInitCnt;
    }

    public int getSuccessInitCnt() {
        return successInitCnt;
    }

    public void setSuccessInitCnt(int successInitCnt) {
        this.successInitCnt = successInitCnt;
    }

    public int getAppLoginCnt() {
        return appLoginCnt;
    }

    public void setAppLoginCnt(int appLoginCnt) {
        this.appLoginCnt = appLoginCnt;
    }

    public int getpClickCnt() {
        return pClickCnt;
    }

    public void setpClickCnt(int pClickCnt) {
        this.pClickCnt = pClickCnt;
    }

    public int getpClickUv() {
        return pClickUv;
    }

    public void setpClickUv(int pClickUv) {
        this.pClickUv = pClickUv;
    }

    public int getChannleSettlementCnt() {
        return channleSettlementCnt;
    }

    public void setChannleSettlementCnt(int channleSettlementCnt) {
        this.channleSettlementCnt = channleSettlementCnt;
    }

    public double getpUvCost() {
        return pUvCost;
    }

    public void setpUvCost(double pUvCost) {
        this.pUvCost = pUvCost;
    }

    public BigDecimal getRegisterUvP() {
        BigDecimal decimal = new BigDecimal(String.valueOf(uv == 0 ? 0.00 : ((double)registerUv / (double)uv) * 100));
        BigDecimal setScale = decimal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return setScale;
    }

    public void setRegisterUvP(BigDecimal registerUvP) {
        this.registerUvP = registerUvP;
    }

    public BigDecimal getChannleSettlementCntP() {
        BigDecimal decimal
            = new BigDecimal(String.valueOf(uv == 0 ? 0.00 : ((double)channleSettlementCnt / (double)uv) * 100));
        BigDecimal setScale = decimal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return setScale;
    }

    public void setChannleSettlementCntP(BigDecimal channleSettlementCntP) {
        this.channleSettlementCntP = channleSettlementCntP;
    }

    public BigDecimal getSuccessDownloadCntP() {
        BigDecimal decimal = new BigDecimal(
            String.valueOf(registerUv == 0 ? 0.00 : ((double)successDownloadCnt / (double)registerUv) * 100));
        BigDecimal setScale = decimal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return setScale;
    }

    public void setSuccessDownloadCntP(BigDecimal successDownloadCntP) {
        this.successDownloadCntP = successDownloadCntP;
    }

    public BigDecimal getAppLoginCntP() {
        BigDecimal decimal = new BigDecimal(
            String.valueOf(successDownloadCnt == 0 ? 0.00 : ((double)appLoginCnt / (double)successDownloadCnt) * 100));
        BigDecimal setScale = decimal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return setScale;
    }

    public void setAppLoginCntP(BigDecimal appLoginCntP) {
        this.appLoginCntP = appLoginCntP;
    }

    public BigDecimal getAppLoginCntHideP() {
        BigDecimal decimal = new BigDecimal(String
            .valueOf(channleSettlementCnt == 0 ? 0.00 : ((double)appLoginCnt / (double)channleSettlementCnt) * 100));
        BigDecimal setScale = decimal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return setScale;
    }

    public void setAppLoginCntHideP(BigDecimal appLoginCntHideP) {
        this.appLoginCntHideP = appLoginCntHideP;
    }

    public BigDecimal getpClickUvP() {
        BigDecimal decimal
            = new BigDecimal(String.valueOf(appLoginCnt == 0 ? 0.00 : ((double)pClickUv / (double)appLoginCnt) * 100));
        BigDecimal setScale = decimal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return setScale;
    }

    public void setpClickUvP(BigDecimal pClickUvP) {
        this.pClickUvP = pClickUvP;
    }

    public BigDecimal getpClickUvHideP() {
        BigDecimal decimal = new BigDecimal(
            String.valueOf(channleSettlementCnt == 0 ? 0.00 : ((double)pClickUv / (double)channleSettlementCnt) * 100));
        BigDecimal setScale = decimal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return setScale;
    }

    public void setpClickUvHideP(BigDecimal pClickUvHideP) {
        this.pClickUvHideP = pClickUvHideP;
    }

    public BigDecimal getpUvP() {
        BigDecimal decimal
            = new BigDecimal(String.valueOf(pClickUv == 0 ? 0.00 : ((double)pUv / (double)pClickUv) * 100));
        BigDecimal setScale = decimal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return setScale;
    }

    public void setpUvP(BigDecimal pUvP) {
        this.pUvP = pUvP;
    }

    public BigDecimal getpUvHideP() {
        BigDecimal decimal = new BigDecimal(
            String.valueOf(channleSettlementCnt == 0 ? 0.00 : ((double)pUv / (double)channleSettlementCnt) * 100));
        BigDecimal setScale = decimal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return setScale;
    }

    public void setpUvHideP(BigDecimal pUvHideP) {
        this.pUvHideP = pUvHideP;
    }

    public Account getSalesAccount() {
        return salesAccount;
    }

    public void setSalesAccount(Account salesAccount) {
        this.salesAccount = salesAccount;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
