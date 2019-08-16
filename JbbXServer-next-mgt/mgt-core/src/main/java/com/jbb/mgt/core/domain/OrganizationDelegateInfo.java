package com.jbb.mgt.core.domain;

public class OrganizationDelegateInfo {

    private Organization org;
    private Channel channel;

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "OrganizationDelegateInfo [org=" + org + ", channel=" + channel + "]";
    }

}
