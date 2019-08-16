package com.jbb.mgt.core.domain.jsonformat.yys;

public class Sms_record {

    private String msg_start_time;
    private String msg_type;
    private String msg_other_num;
    private String msg_channel;
    private String msg_biz_name;
    private String msg_address;
    private String msg_cost;
    private String contact_type;
    private String contact_name;
    private String contact_area;

    public String getMsg_start_time() {
        return msg_start_time;
    }

    public void setMsg_start_time(String msg_start_time) {
        this.msg_start_time = msg_start_time;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getMsg_other_num() {
        return msg_other_num;
    }

    public void setMsg_other_num(String msg_other_num) {
        this.msg_other_num = msg_other_num;
    }

    public String getMsg_channel() {
        return msg_channel;
    }

    public void setMsg_channel(String msg_channel) {
        this.msg_channel = msg_channel;
    }

    public String getMsg_biz_name() {
        return msg_biz_name;
    }

    public void setMsg_biz_name(String msg_biz_name) {
        this.msg_biz_name = msg_biz_name;
    }

    public String getMsg_address() {
        return msg_address;
    }

    public void setMsg_address(String msg_address) {
        this.msg_address = msg_address;
    }

    public String getMsg_cost() {
        return msg_cost;
    }

    public void setMsg_cost(String msg_cost) {
        this.msg_cost = msg_cost;
    }

    public String getContact_type() {
        return contact_type;
    }

    public void setContact_type(String contact_type) {
        this.contact_type = contact_type;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_area() {
        return contact_area;
    }

    public void setContact_area(String contact_area) {
        this.contact_area = contact_area;
    }

    @Override
    public String toString() {
        return "Sms_record{" + "msg_start_time='" + msg_start_time + '\'' + ", msg_type='" + msg_type + '\''
            + ", msg_other_num='" + msg_other_num + '\'' + ", msg_channel='" + msg_channel + '\'' + ", msg_biz_name='"
            + msg_biz_name + '\'' + ", msg_address='" + msg_address + '\'' + ", msg_cost='" + msg_cost + '\''
            + ", contact_type='" + contact_type + '\'' + ", contact_name='" + contact_name + '\'' + ", contact_area='"
            + contact_area + '\'' + '}';
    }
}
