package com.jbb.mgt.core.domain.jsonformat.gjj;

public class Pay_info {

    private int pay_status;
    private String gjj_no;
    private String pay_status_desc;
    private String corp_name;
    private String balance;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public String getGjj_no() {
        return gjj_no;
    }

    public void setGjj_no(String gjj_no) {
        this.gjj_no = gjj_no;
    }

    public String getPay_status_desc() {
        return pay_status_desc;
    }

    public void setPay_status_desc(String pay_status_desc) {
        this.pay_status_desc = pay_status_desc;
    }

    public String getCorp_name() {
        return corp_name;
    }

    public void setCorp_name(String corp_name) {
        this.corp_name = corp_name;
    }
}
