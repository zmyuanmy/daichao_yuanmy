package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

import java.util.List;

@Data
public class Detail {
    private String policy_id;
    private List<GroupRule> group_rules;
    private List<BoolRule> bool_rules;

}
