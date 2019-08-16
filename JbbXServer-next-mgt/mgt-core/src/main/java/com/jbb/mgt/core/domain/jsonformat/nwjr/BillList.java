package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

@Data
public class BillList {
    private String month;
    private String call_pay;
    private String package_fee;
    private String msg_fee;
    private String tel_fee;
    private String net_fee;
    private String addtional_fee;
    private String preferential_fee;
    private String generation_fee;
    private String other_fee;
    private String score;
}
