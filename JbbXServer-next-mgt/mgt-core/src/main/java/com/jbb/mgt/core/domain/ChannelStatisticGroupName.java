package com.jbb.mgt.core.domain;

import java.sql.Date;
import java.util.List;

public class ChannelStatisticGroupName {
    private Date statisticDate;
    private List<ChannelStatisticDaily> channelStatisticDailies;

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public List<ChannelStatisticDaily> getChannelStatisticDailies() {
        return channelStatisticDailies;
    }

    public void setChannelStatisticDailies(List<ChannelStatisticDaily> channelStatisticDailies) {
        this.channelStatisticDailies = channelStatisticDailies;
    }
}
