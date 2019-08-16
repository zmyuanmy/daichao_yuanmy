package com.jbb.mgt.core.domain;

public class QiFengDefined {
    private String defined1;// 冗余字段，自定义1(企业)
    private String defined2;// 冗余字段，自定义2(企业)

    public String getDefined1() {
        return defined1;
    }

    public void setDefined1(String defined1) {
        this.defined1 = defined1;
    }

    public String getDefined2() {
        return defined2;
    }

    public void setDefined2(String defined2) {
        this.defined2 = defined2;
    }

    @Override
    public String toString() {
        return "QiFengDefined [defined1=" + defined1 + ", defined2=" + defined2 + "]";
    }

}
