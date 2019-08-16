package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.OrgRechargeDetail;
import com.jbb.mgt.core.domain.TeleMarketing;

public interface OrgRechargeDetailService {

    void insertOrgRechargeDetail(OrgRechargeDetail orgRechargeDetail);

    OrgRechargeDetail selectOrgRechargeDetail(String tradeNo);

    List<OrgRechargeDetail> selectOrgRechargeDetailById(int accountId);

    List<OrgRechargeDetail> selectAllOrgRechargeDetail(int orgId);

    void updateOrgRechargeDetail(OrgRechargeDetail orgRechargeDetail);

    /**
     * 处理消费短信接口
     * 
     * @param channelCode
     * @param phoneNumber
     * @param smsPrice
     * @return
     */
    String handleConsumeSms(String channelCode, String phoneNumber, Integer smsPrice);

    String handleConsumeData(int orgId, int userId, int price);

    String consumePhoneNumberCheck(TeleMarketing teleMarketing);

    String handleConsumeTbRep(int accountId, Integer userId, int updatePric);

    String handleConsumeYxRep(int accountId, Integer userId, int updatePric);

    String handleConsumeTdRep(int accountId, int userId, int updatePric);

    String manualRechage(int accountId, Integer amount, Integer newType, int accountId2);

    String handleConsumeXjlReMsg(int accountId, String phoneNumber, Integer smsPrice);

}
