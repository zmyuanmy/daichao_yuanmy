package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class FinOpLog {

    public static int CHANNEL_FLAG = 1;
    public static int PLATFORM_FLAG = 2;
    public static final int PLATFORM_UPLOAD_FLAG = 3;
    private int logId;
    private Integer accountId;
    private String sourceId;
    private Integer type;
    private Timestamp opDate;
    private String params;
}
