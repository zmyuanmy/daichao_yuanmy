package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

@Data
public class CallHistoryList {
    private String mobile;
    private String time;
    private int duration;
    private int type;
}
