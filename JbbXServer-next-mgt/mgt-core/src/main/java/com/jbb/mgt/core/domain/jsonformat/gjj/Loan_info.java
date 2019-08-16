package com.jbb.mgt.core.domain.jsonformat.gjj;

import java.util.List;

public class Loan_info {
    private String end_date;
    private int overdue_capital;
    private int overdue_days;
    private String repay_type_desc;
    private int periods;
    private int overdue_penalty;
    private String start_date;
    private String bank_account;
    private String delegate_bank;
    private String contract_no;
    private double loan_interest_percent;
    private String sign_date;
    private long loan_amount;
    private long remain_amount;
    private int overdue_interest;
    private String bank_account_name;
    private List<String> repay_record_list;
    private double penalty_interest_percent;
    private int repay_type;
    private int deduct_day;
    private String house_address;
    private String status;

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getOverdue_capital() {
        return overdue_capital;
    }

    public void setOverdue_capital(int overdue_capital) {
        this.overdue_capital = overdue_capital;
    }

    public int getOverdue_days() {
        return overdue_days;
    }

    public void setOverdue_days(int overdue_days) {
        this.overdue_days = overdue_days;
    }

    public String getRepay_type_desc() {
        return repay_type_desc;
    }

    public void setRepay_type_desc(String repay_type_desc) {
        this.repay_type_desc = repay_type_desc;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public int getOverdue_penalty() {
        return overdue_penalty;
    }

    public void setOverdue_penalty(int overdue_penalty) {
        this.overdue_penalty = overdue_penalty;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public String getDelegate_bank() {
        return delegate_bank;
    }

    public void setDelegate_bank(String delegate_bank) {
        this.delegate_bank = delegate_bank;
    }

    public String getContract_no() {
        return contract_no;
    }

    public void setContract_no(String contract_no) {
        this.contract_no = contract_no;
    }

    public double getLoan_interest_percent() {
        return loan_interest_percent;
    }

    public void setLoan_interest_percent(double loan_interest_percent) {
        this.loan_interest_percent = loan_interest_percent;
    }

    public String getSign_date() {
        return sign_date;
    }

    public void setSign_date(String sign_date) {
        this.sign_date = sign_date;
    }

    public long getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(long loan_amount) {
        this.loan_amount = loan_amount;
    }

    public long getRemain_amount() {
        return remain_amount;
    }

    public void setRemain_amount(long remain_amount) {
        this.remain_amount = remain_amount;
    }

    public int getOverdue_interest() {
        return overdue_interest;
    }

    public void setOverdue_interest(int overdue_interest) {
        this.overdue_interest = overdue_interest;
    }

    public String getBank_account_name() {
        return bank_account_name;
    }

    public void setBank_account_name(String bank_account_name) {
        this.bank_account_name = bank_account_name;
    }

    public List<String> getRepay_record_list() {
        return repay_record_list;
    }

    public void setRepay_record_list(List<String> repay_record_list) {
        this.repay_record_list = repay_record_list;
    }

    public double getPenalty_interest_percent() {
        return penalty_interest_percent;
    }

    public void setPenalty_interest_percent(double penalty_interest_percent) {
        this.penalty_interest_percent = penalty_interest_percent;
    }

    public int getRepay_type() {
        return repay_type;
    }

    public void setRepay_type(int repay_type) {
        this.repay_type = repay_type;
    }

    public int getDeduct_day() {
        return deduct_day;
    }

    public void setDeduct_day(int deduct_day) {
        this.deduct_day = deduct_day;
    }

    public String getHouse_address() {
        return house_address;
    }

    public void setHouse_address(String house_address) {
        this.house_address = house_address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
