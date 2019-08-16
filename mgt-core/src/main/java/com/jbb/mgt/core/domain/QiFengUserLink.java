package com.jbb.mgt.core.domain;

public class QiFengUserLink {
    private String linkSex;
    private String linkTelphone;
    private String linkWork;// 职务(联系人) 定位存放:创建时间

    public QiFengUserLink() {}

    public QiFengUserLink(String linkSex, String linkTelphone, String linkWork) {
        super();
        this.linkSex = linkSex;
        this.linkTelphone = linkTelphone;
        this.linkWork = linkWork;
    }

    public String getLinkSex() {
        return linkSex;
    }

    public void setLinkSex(String linkSex) {
        this.linkSex = linkSex;
    }

    public String getLinkTelphone() {
        return linkTelphone;
    }

    public void setLinkTelphone(String linkTelphone) {
        this.linkTelphone = linkTelphone;
    }

    public String getLinkWork() {
        return linkWork;
    }

    public void setLinkWork(String linkWork) {
        this.linkWork = linkWork;
    }
}
