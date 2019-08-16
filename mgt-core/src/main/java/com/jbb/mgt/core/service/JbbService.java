package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.IouStatus;
import com.jbb.mgt.core.domain.LoanRecordUpdateRsp;
import com.jbb.mgt.core.domain.ReMgtIou;
import com.jbb.mgt.core.domain.UserResponse;

public interface JbbService {

    LoanRecordUpdateRsp updateIouStatus(IouStatus iouStatus);

    UserResponse getUserReportByPhoneNumber(String phoneNumber);

    Integer check(Integer jbbUserId, String nickname);

    String fillIou(ReMgtIou reMgtIou);

    Integer checkSend(Integer accountId, Integer jbbUserId);
}
