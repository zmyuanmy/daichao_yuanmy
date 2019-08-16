package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

@Data
public class MessageRecordList {
    private String send_time;
    private int trade_way;
    private String receiver_phone;
    private String business_name;
    private String fee;
    private String trade_addr;
    private String trade_type;
}
