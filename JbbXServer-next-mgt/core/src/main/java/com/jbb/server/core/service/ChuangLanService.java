package com.jbb.server.core.service;

import com.jbb.server.core.domain.ChuangLanPhoneNumberRsp;
import com.jbb.server.core.domain.ChuangLanWoolCheckRsp;

public interface ChuangLanService {

    void sendMsgCode(String phoneNumber);

    ChuangLanWoolCheckRsp woolCheck(String mobile, String ipAddress);

    ChuangLanPhoneNumberRsp validateMobile(String mobile, boolean saveData);

}
