package com.jbb.mgt.core.domain;

public class EducationBase {

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
        return "EducationBase{" + "id=" + id + ", des='" + des + '\'' + '}';
    }
}
