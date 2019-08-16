package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.OrgRechargeDetail;
import com.jbb.mgt.core.domain.TeleMarketing;
import org.apache.ibatis.annotations.Param;

public interface OrgRechargeDetailDao {

    void insertOrgRechargeDetail(OrgRechargeDetail orgRechargeDetail);

    OrgRechargeDetail selectOrgRechargeDetail(String tradeNo);

    List<OrgRechargeDetail> selectOrgRechargeDetailById(int accountId);

    List<OrgRechargeDetail> selectAllOrgRechargeDetail(int orgId);

    void updateOrgRechargeDetail(OrgRechargeDetail orgRechargeDetail);

    String consumePhoneNumberCheck(TeleMarketing teleMarketing);

    Integer selectAllOrgRechargeCountByDate(int orgId, int opType, Timestamp startDate, Timestamp endDate);
}
