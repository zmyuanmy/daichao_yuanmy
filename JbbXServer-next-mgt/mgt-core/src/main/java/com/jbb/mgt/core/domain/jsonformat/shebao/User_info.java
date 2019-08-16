package com.jbb.mgt.core.domain.jsonformat.shebao;

public class User_info {

    private String time_to_work;
    private String gender;
    private String company_name;
    private String name;
    private String certificate_number;
    private String type;

    public String getTime_to_work() {
        return time_to_work;
    }

    public void setTime_to_work(String time_to_work) {
        this.time_to_work = time_to_work;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificate_number() {
        return certificate_number;
    }

    public void setCertificate_number(String certificate_number) {
        this.certificate_number = certificate_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}