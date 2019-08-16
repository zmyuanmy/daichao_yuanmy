package com.jbb.mgt.core.domain;

public class ChannelSimpleInfo {
    private String channelCode;
    private String channelName;

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

    @Override
    public String toString() {
        return "ChannelSimpleInfo [channelCode=" + channelCode + ", channelName=" + channelName + "]";
    }

}
