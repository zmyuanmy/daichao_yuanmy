package com.jbb.mgt.core.domain;

import lombok.Data;

@Data
public class PlatformMsgGroup {
    private String groupName;
    private int totalBalance;
    private String phoneNumbers;
}
