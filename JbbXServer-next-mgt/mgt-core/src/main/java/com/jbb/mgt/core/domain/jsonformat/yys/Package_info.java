package com.jbb.mgt.core.domain.jsonformat.yys;

import java.util.List;

public class Package_info {

    private String brand_name;
    private String pay_type;
    private List<String> package_detail;

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public List<String> getPackage_detail() {
        return package_detail;
    }

    public void setPackage_detail(List<String> package_detail) {
        this.package_detail = package_detail;
    }
}
