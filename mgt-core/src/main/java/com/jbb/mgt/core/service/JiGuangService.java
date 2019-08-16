package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.JiGuangMessage;
import com.jbb.mgt.core.domain.JiGuangUserDevice;

public interface JiGuangService {

    void saveUserDevice(JiGuangUserDevice userDevice);

    void jiGuangPush(JiGuangMessage jiGuangMessage);

}
