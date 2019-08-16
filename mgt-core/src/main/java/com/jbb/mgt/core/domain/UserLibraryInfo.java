 package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

import lombok.Data;
@Data
public class UserLibraryInfo {
     private Integer userId;
     private String phoneNumber;
     private String channelCode;
     private String channelName;
     private String ipAddress;
     private Timestamp creationDate;

}
