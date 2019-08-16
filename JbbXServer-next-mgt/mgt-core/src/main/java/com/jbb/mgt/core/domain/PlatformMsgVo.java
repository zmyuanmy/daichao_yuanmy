package com.jbb.mgt.core.domain;

import java.util.List;

import lombok.Getter;

@Getter
public class PlatformMsgVo {
    private int accountId;
    private String phoneNumber;
    private List<Platform> platforms;
    private int platformId;
}
