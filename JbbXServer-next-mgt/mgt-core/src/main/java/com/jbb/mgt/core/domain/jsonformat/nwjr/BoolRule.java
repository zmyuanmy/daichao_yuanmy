package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

import java.util.List;

@Data
public class BoolRule {
    private String hit;
    private String rule_name;
    private String rule_id;
    private double score;
    private List<CellruleDetail> data;
}
