package com.jbb.mgt.core.domain.jsonformat.gjj;

import java.util.List;

public class Task_data {

    private List<Loan_info> loan_info;
    private List<Pay_info> pay_info;
    private List<Bill_record> bill_record;
    private Base_info base_info;

    public List<Loan_info> getLoan_info() {
        return loan_info;
    }

    public void setLoan_info(List<Loan_info> loan_info) {
        this.loan_info = loan_info;
    }

    public List<Pay_info> getPay_info() {
        return pay_info;
    }

    public void setPay_info(List<Pay_info> pay_info) {
        this.pay_info = pay_info;
    }

    public List<Bill_record> getBill_record() {
        return bill_record;
    }

    public void setBill_record(List<Bill_record> bill_record) {
        this.bill_record = bill_record;
    }

    public Base_info getBase_info() {
        return base_info;
    }

    public void setBase_info(Base_info base_info) {
        this.base_info = base_info;
    }
}
