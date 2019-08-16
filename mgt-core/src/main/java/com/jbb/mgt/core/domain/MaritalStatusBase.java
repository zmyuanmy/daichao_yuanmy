package com.jbb.mgt.core.domain;

public class MaritalStatusBase {

    private int id;
    private String des;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "MaritalStatusBase{" + "id=" + id + ", des='" + des + '\'' + '}';
    }
}
