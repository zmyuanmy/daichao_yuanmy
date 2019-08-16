package com.jbb.mgt.core.domain.jsonformat.yys;

import java.util.List;

public class Sms_info {
    private String total_msg_cost;
    private String total_msg_count;
    private List<Sms_record> sms_record;
    private String msg_cycle;

    public String getTotal_msg_cost() {
        return total_msg_cost;
    }

    public void setTotal_msg_cost(String total_msg_cost) {
        this.total_msg_cost = total_msg_cost;
    }

    public String getTotal_msg_count() {
        return total_msg_count;
    }

    public void setTotal_msg_count(String total_msg_count) {
        this.total_msg_count = total_msg_count;
    }

    public String getMsg_cycle() {
        return msg_cycle;
    }

    public void setMsg_cycle(String msg_cycle) {
        this.msg_cycle = msg_cycle;
    }

    public List<Sms_record> getSms_record() {
        return sms_record;
    }

    public void setSms_record(List<Sms_record> sms_record) {
        this.sms_record = sms_record;
    }
}
