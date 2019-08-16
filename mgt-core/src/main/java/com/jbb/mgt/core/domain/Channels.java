package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Channels {
    private Channel channel;
    private ChannelStatisticDaily channelStatisticDaily;
    private ChannelAccountInfo channelAccountInfo;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ChannelStatisticDaily getChannelStatisticDaily() {
        return channelStatisticDaily;
    }

    public void setChannelStatisticDaily(ChannelStatisticDaily channelStatisticDaily) {
        this.channelStatisticDaily = channelStatisticDaily;
    }

    public ChannelAccountInfo getChannelAccountInfo() {
        return channelAccountInfo;
    }

    public void setChannelAccountInfo(ChannelAccountInfo channelAccountInfo) {
        this.channelAccountInfo = channelAccountInfo;
    }

    @Override
    public String toString() {
        return "Channels [channel=" + channel + ", channelStatisticDaily=" + channelStatisticDaily
            + ", channelAccountInfo=" + channelAccountInfo + "]";
    }

}
