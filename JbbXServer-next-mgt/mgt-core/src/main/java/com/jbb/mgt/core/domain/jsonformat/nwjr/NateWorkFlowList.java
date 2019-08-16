package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

@Data
public class NateWorkFlowList {
    private String fee;
    private String net_type;
    private String net_way;
    private String preferential_fee;
    private String start_time;
    private String total_time;
    private String total_traffic;
    private String trade_addr;
}
