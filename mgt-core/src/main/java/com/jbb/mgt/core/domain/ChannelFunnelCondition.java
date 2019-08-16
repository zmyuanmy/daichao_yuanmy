package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChannelFunnelCondition {
    @JsonIgnore
    private int conditionId;
    private String property;
    private String operator;
    private String value;
}
