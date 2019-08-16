package com.jbb.mgt.core.domain.jsonformat.gjj;

public class Bill_record {

    private long income;
    private int corp_income;
    private int cust_income;
    private long balance;
    private String deal_time;
    private String corp_name;
    private int outcome;
    private String desc;

    public long getIncome() {
        return income;
    }

    public void setIncome(long income) {
        this.income = income;
    }

    public int getCorp_income() {
        return corp_income;
    }

    public void setCorp_income(int corp_income) {
        this.corp_income = corp_income;
    }

    public int getCust_income() {
        return cust_income;
    }

    public void setCust_income(int cust_income) {
        this.cust_income = cust_income;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(String deal_time) {
        this.deal_time = deal_time;
    }

    public String getCorp_name() {
        return corp_name;
    }

    public void setCorp_name(String corp_name) {
        this.corp_name = corp_name;
    }

    public int getOutcome() {
        return outcome;
    }

    public void setOutcome(int outcome) {
        this.outcome = outcome;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
