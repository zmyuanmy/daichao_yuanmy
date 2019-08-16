package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

@Data
public class CalculationResultMsgBody {
    private String order_id;
    private String model_name;
    private String transaction_id;
    private int status;
    private String message;
    private String spend_time;
    private Result result;
}
