package com.jbb.mgt.core.domain.jsonformat.yys;

import java.util.List;

public class Task_data {

    private List<Bill_info> bill_info;
    private List<String> family_info;
    private List<Sms_info> sms_info;
    private Account_info account_info;
    private Point_info point_info;
    private Base_info base_info;
    private List<Payment_info> payment_info;
    private Package_info package_info;
    private List<Call_info> call_info;
    private List<Data_info> data_info;

    public List<Bill_info> getBill_info() {
        return bill_info;
    }

    public void setBill_info(List<Bill_info> bill_info) {
        this.bill_info = bill_info;
    }

    public List<String> getFamily_info() {
        return family_info;
    }

    public void setFamily_info(List<String> family_info) {
        this.family_info = family_info;
    }

    public List<Sms_info> getSms_info() {
        return sms_info;
    }

    public void setSms_info(List<Sms_info> sms_info) {
        this.sms_info = sms_info;
    }

    public Account_info getAccount_info() {
        return account_info;
    }

    public void setAccount_info(Account_info account_info) {
        this.account_info = account_info;
    }

    public Point_info getPoint_info() {
        return point_info;
    }

    public void setPoint_info(Point_info point_info) {
        this.point_info = point_info;
    }

    public Base_info getBase_info() {
        return base_info;
    }

    public void setBase_info(Base_info base_info) {
        this.base_info = base_info;
    }

    public List<Payment_info> getPayment_info() {
        return payment_info;
    }

    public void setPayment_info(List<Payment_info> payment_info) {
        this.payment_info = payment_info;
    }

    public Package_info getPackage_info() {
        return package_info;
    }

    public void setPackage_info(Package_info package_info) {
        this.package_info = package_info;
    }

    public List<Call_info> getCall_info() {
        return call_info;
    }

    public void setCall_info(List<Call_info> call_info) {
        this.call_info = call_info;
    }

    public List<Data_info> getData_info() {
        return data_info;
    }

    public void setData_info(List<Data_info> data_info) {
        this.data_info = data_info;
    }
}
