package com.jbb.mgt.core.domain;

import java.util.List;

public class QiFengUser {

    private String type;// 类型:1-资源，2客户，3签约客户
    private List<QiFengUserData> data;

    public QiFengUser() {}

    public QiFengUser(String type, List<QiFengUserData> data) {
        super();
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<QiFengUserData> getData() {
        return data;
    }

    public void setData(List<QiFengUserData> data) {
        this.data = data;
    }

}
