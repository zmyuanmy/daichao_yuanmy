package com.jbb.mgt.core.domain.jsonformat.shebao;

public class Unemployment_insurance {
    private int month_count;
    private String month;
    private String company_name;
    private String company_percentage;
    private long base_number;
    private int monthly_company_income;
    private String type;
    private int monthly_personal_income;
    private String personal_percentage;
    private String last_pay_date;

    public String getLast_pay_date() {
        return last_pay_date;
    }

    public void setLast_pay_date(String last_pay_date) {
        this.last_pay_date = last_pay_date;
    }

    public int getMonth_count() {
        return month_count;
    }

    public void setMonth_count(int month_count) {
        this.month_count = month_count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_percentage() {
        return company_percentage;
    }

    public void setCompany_percentage(String company_percentage) {
        this.company_percentage = company_percentage;
    }

    public long getBase_number() {
        return base_number;
    }

    public void setBase_number(long base_number) {
        this.base_number = base_number;
    }

    public int getMonthly_company_income() {
        return monthly_company_income;
    }

    public void setMonthly_company_income(int monthly_company_income) {
        this.monthly_company_income = monthly_company_income;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMonthly_personal_income() {
        return monthly_personal_income;
    }

    public void setMonthly_personal_income(int monthly_personal_income) {
        this.monthly_personal_income = monthly_personal_income;
    }

    public String getPersonal_percentage() {
        return personal_percentage;
    }

    public void setPersonal_percentage(String personal_percentage) {
        this.personal_percentage = personal_percentage;
    }
}
