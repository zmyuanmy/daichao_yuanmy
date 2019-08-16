package com.jbb.mgt.core.domain;

import java.util.List;

public class QiFengUserData {
    private String reqId;// 唯一编码（返回参数会使用）客户ID
    private String name;// 客户名称(个人)
    private QiFengDefined defineds;
    private List<QiFengUserLink> links;

    public QiFengUserData() {}

    public QiFengUserData(String reqId, String name, List<QiFengUserLink> links) {
        super();
        this.reqId = reqId;
        this.name = name;
        this.links = links;
    }

    public QiFengUserData(String reqId) {
        super();
        this.reqId = reqId;

    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QiFengDefined getDefineds() {
        return defineds;
    }

    public void setDefineds(QiFengDefined defineds) {
        this.defineds = defineds;
    }

    public List<QiFengUserLink> getLinks() {
        return links;
    }

    public void setLinks(List<QiFengUserLink> links) {
        this.links = links;
    }

}
