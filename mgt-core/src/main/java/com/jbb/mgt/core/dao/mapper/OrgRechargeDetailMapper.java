package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.OrgRechargeDetail;

public interface OrgRechargeDetailMapper {

    void insertOrgRechargeDetail(OrgRechargeDetail orgRechargeDetail);

    OrgRechargeDetail selectOrgRechargeDetail(@Param(value = "tradeNo") String tradeNo);

    List<OrgRechargeDetail> selectAllOrgRechargeDetail(@Param(value = "orgId") int orgId);
    
    List<OrgRechargeDetail> selectOrgRechargeDetailById(@Param(value = "accountId") int accountId);

    void updateOrgRechargeDetail(OrgRechargeDetail orgRechargeDetail);

    Integer selectAllOrgRechargeCountByDate(@Param(value = "orgId") int orgId,@Param(value = "opType") int opType,@Param(value = "startDate") Timestamp startDate,@Param(value = "endDate") Timestamp endDate);

}
