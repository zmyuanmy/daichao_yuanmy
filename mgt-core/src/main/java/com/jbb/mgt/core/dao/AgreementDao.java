package com.jbb.mgt.core.dao;

import java.util.List;

import com.jbb.mgt.core.domain.Agreement;

public interface AgreementDao {

    // 获取相关协议
    List<Agreement> selectAgreement(int orgId);
    
}
