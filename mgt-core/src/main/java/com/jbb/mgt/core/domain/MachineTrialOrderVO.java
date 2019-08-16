package com.jbb.mgt.core.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MachineTrialOrderVO {

    private String jbbUserId; //帮帮ID
    private String userName;  //用户名
    private String phoneNumber; //手机号
    private String platform; //来源
    private Date registerDate; //注册日期
    private Date creationDate;   //申请时间
    private int amount;          //申请金额
    private String claim; //领取人
    private String status; //状态

}
