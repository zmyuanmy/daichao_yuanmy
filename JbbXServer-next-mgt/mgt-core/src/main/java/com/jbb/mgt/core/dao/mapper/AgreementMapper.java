package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.Agreement;

public interface AgreementMapper {

    // 获取相关协议
    List<Agreement> selectAgreement(@Param("orgId") int orgId);
}
