package com.jbb.mgt.core.domain.jsonformat.yys;

import java.util.List;

public class Call_info {

    private String total_call_count;
    private String total_call_time;
    private String total_fee;
    private String call_cycle;
    private List<Call_record> call_record;

    public String getTotal_call_count() {
        return total_call_count;
    }

    public void setTotal_call_count(String total_call_count) {
        this.total_call_count = total_call_count;
    }

    public String getTotal_call_time() {
        return total_call_time;
    }

    public void setTotal_call_time(String total_call_time) {
        this.total_call_time = total_call_time;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getCall_cycle() {
        return call_cycle;
    }

    public void setCall_cycle(String call_cycle) {
        this.call_cycle = call_cycle;
    }

    public List<Call_record> getCall_record() {
        return call_record;
    }

    public void setCall_record(List<Call_record> call_record) {
        this.call_record = call_record;
    }
}
