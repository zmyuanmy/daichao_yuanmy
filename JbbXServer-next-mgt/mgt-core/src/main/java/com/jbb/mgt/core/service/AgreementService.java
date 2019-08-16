package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.Agreement;

public interface AgreementService {

    // 获取相关协议
    List<Agreement> getAgreement(int orgId);
}
