package com.jbb.mgt.core.domain.jsonformat.yys;

import java.util.List;

public class Bill_info {
    private String bill_discount;
    private String bill_fee;
    private List<Usage_detail> usage_detail;
    private List<Bill_record> bill_record;
    private String bill_cycle;
    private String paid_amount;
    private String unpaid_amount;
    private String breach_amount;
    private String bill_total;

    public String getBill_discount() {
        return bill_discount;
    }

    public void setBill_discount(String bill_discount) {
        this.bill_discount = bill_discount;
    }

    public String getBill_fee() {
        return bill_fee;
    }

    public void setBill_fee(String bill_fee) {
        this.bill_fee = bill_fee;
    }

    public List<Usage_detail> getUsage_detail() {
        return usage_detail;
    }

    public void setUsage_detail(List<Usage_detail> usage_detail) {
        this.usage_detail = usage_detail;
    }

    public List<Bill_record> getBill_record() {
        return bill_record;
    }

    public void setBill_record(List<Bill_record> bill_record) {
        this.bill_record = bill_record;
    }

    public String getBill_cycle() {
        return bill_cycle;
    }

    public void setBill_cycle(String bill_cycle) {
        this.bill_cycle = bill_cycle;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getUnpaid_amount() {
        return unpaid_amount;
    }

    public void setUnpaid_amount(String unpaid_amount) {
        this.unpaid_amount = unpaid_amount;
    }

    public String getBreach_amount() {
        return breach_amount;
    }

    public void setBreach_amount(String breach_amount) {
        this.breach_amount = breach_amount;
    }

    public String getBill_total() {
        return bill_total;
    }

    public void setBill_total(String bill_total) {
        this.bill_total = bill_total;
    }
}
