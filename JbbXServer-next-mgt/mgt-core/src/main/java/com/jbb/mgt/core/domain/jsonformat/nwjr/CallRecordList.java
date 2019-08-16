package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

@Data
public class CallRecordList {
    private String trade_type;
    private String trade_time;
    private String call_time;
    private String trade_addr;
    private String receive_phone;
    private String call_type;
    private String business_name;
    private String fee;
    private String special_offer;

}
