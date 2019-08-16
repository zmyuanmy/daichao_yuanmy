package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ChannelFunnelFilter {
    private int filterId;
    private String filterName;
    @JsonIgnore
    private String filterScript;
    private String style;
    private Integer index;
    private List<ChannelFunnelCondition> conditions;
}
