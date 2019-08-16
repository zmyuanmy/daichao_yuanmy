package com.jbb.mgt.core.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class XjlAntiFraudResult implements Serializable{
    private static final long serialVersionUID = 1123655922325892354L;
    private int id;
    private int userId;
    private int orderId;
    private String antiFraudResult;
    private int status;
    private Date createTime;
}
