package com.jbb.mgt.core.domain.mq;

public class RegisterEvent {

    private int step;
    private int userId;
    private int userType;
    private String channelCode;

    private int specialRecommandOrgId;
    private int delay;

    public RegisterEvent() {
        super();
    }

    public RegisterEvent(int step, int userId, String channelCode, int userType) {
        super();
        this.step = step;
        this.userId = userId;
        this.channelCode = channelCode;
        this.userType = userType;
    }

    public RegisterEvent(int step, int userId, String channelCode, int specialRecommandOrgId, int delay) {
        super();
        this.step = step;
        this.userId = userId;
        this.channelCode = channelCode;
        this.specialRecommandOrgId = specialRecommandOrgId;
        this.delay = delay;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getSpecialRecommandOrgId() {
        return specialRecommandOrgId;
    }

    public void setSpecialRecommandOrgId(int specialRecommandOrgId) {
        this.specialRecommandOrgId = specialRecommandOrgId;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "RegisterEvent [step=" + step + ", userId=" + userId + ", userType=" + userType + ", channelCode="
            + channelCode + ", specialRecommandOrgId=" + specialRecommandOrgId + ", delay=" + delay + "]";
    }

}
