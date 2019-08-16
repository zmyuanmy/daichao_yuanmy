package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 渠道推广统计总数
 * 
 * @author wyq
 * @date 2018-12-11 14:29:56
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelPromotion {

    private String channelCode;
    /** 注册数 */
    private int registerCnt;
    /** 进件数 */
    private int intoCnt;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public int getRegisterCnt() {
        return registerCnt;
    }

    public void setRegisterCnt(int registerCnt) {
        this.registerCnt = registerCnt;
    }

    public int getIntoCnt() {
        return intoCnt;
    }

    public void setIntoCnt(int intoCnt) {
        this.intoCnt = intoCnt;
    }

    public ChannelPromotion() {
        super();
    }

    public ChannelPromotion(String channelCode, int registerCnt, int intoCnt) {
        this.channelCode = channelCode;
        this.registerCnt = registerCnt;
        this.intoCnt = intoCnt;
    }

    @Override
    public String toString() {
        return "ChannelPromotion{" + "channelCode='" + channelCode + '\'' + ", registerCnt=" + registerCnt
            + ", intoCnt=" + intoCnt + '}';
    }
}