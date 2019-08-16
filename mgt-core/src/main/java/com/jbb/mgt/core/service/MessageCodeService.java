 package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.MessageCode;

public interface MessageCodeService {
     
     MessageCode getMessageCode(String phoneNumber, String channelCode);

}
