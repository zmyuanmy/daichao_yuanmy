package com.jbb.mgt.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.AccountDao;
import com.jbb.mgt.core.dao.ChannelDao;
import com.jbb.mgt.core.dao.OrgRechargeDetailDao;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.OrgRechargeDetail;
import com.jbb.mgt.core.domain.TeleMarketing;
import com.jbb.mgt.core.service.OrgRechargeDetailService;
import com.jbb.mgt.server.core.util.StringUtils;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.util.DateUtil;

import net.sf.json.JSONObject;

@Service("OrgRechargeDetailService")
public class OrgRechargeDetailServiceImpl implements OrgRechargeDetailService {

    // 进件
    private static int OP_TYPE_ENTRY_USER = 21;
    // 短信
    private static int OP_TYPE_SMS = 22;
    // 多头
    private static int OP_TYPE_REPORT_TD = 31;
    // 天贝
    private static int OP_TYPE_REPORT_TB = 32;
    // 聚信立借条
    private static int OP_TYPE_REPORT_YX = 33;
    // 电销号处理
    private static int OP_TYPE_TELE_MOBILE = 34;
    // 小金条短信提醒
    private static int OP_TYPE_REMIND_XJL = 35;

    @Autowired
    private OrgRechargeDetailDao orgRechargeDetailDao;
    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private AccountDao accountDao;

    @Override
    public void insertOrgRechargeDetail(OrgRechargeDetail orgRechargeDetail) {
        orgRechargeDetailDao.insertOrgRechargeDetail(orgRechargeDetail);
    }

    @Override
    public OrgRechargeDetail selectOrgRechargeDetail(String tradeNo) {
        return orgRechargeDetailDao.selectOrgRechargeDetail(tradeNo);
    }

    @Override
    public List<OrgRechargeDetail> selectOrgRechargeDetailById(int accountId) {
        return orgRechargeDetailDao.selectOrgRechargeDetailById(accountId);
    }

    @Override
    public List<OrgRechargeDetail> selectAllOrgRechargeDetail(int orgId) {
        return orgRechargeDetailDao.selectAllOrgRechargeDetail(orgId);
    }

    @Override
    public void updateOrgRechargeDetail(OrgRechargeDetail orgRechargeDetail) {
        orgRechargeDetailDao.updateOrgRechargeDetail(orgRechargeDetail);
    }

    @Override
    public String handleConsumeSms(String channelCode, String phoneNumber, Integer smsPrice) {
        Channel channel = channelDao.selectChannelByCode(channelCode);
        if (null == channel) {
            throw new ObjectNotFoundException("jbb.mgt.exception.channel.notFound");
        }
        OrgRechargeDetail orgRechargeDetail = new OrgRechargeDetail();
        orgRechargeDetail.setAccountId(channel.getCreator());
        orgRechargeDetail.setAmount(smsPrice);
        orgRechargeDetail.setCreationDate(DateUtil.getCurrentTimeStamp());
        orgRechargeDetail.setOpType(OP_TYPE_SMS);
        orgRechargeDetail.setStatus(1);
        orgRechargeDetail.setTradeNo(StringUtils.getRandomString(32));

        Map<String, Object> mapInfo = new HashMap<>();
        mapInfo.put("phoneNumber", phoneNumber);
        mapInfo.put("sendDate", DateUtil.getCurrentTime());
        mapInfo.put("channelCode", channelCode);
        JSONObject jsonObj = JSONObject.fromObject(mapInfo);
        orgRechargeDetail.setParams(jsonObj.toString());
        orgRechargeDetailDao.insertOrgRechargeDetail(orgRechargeDetail);
        return orgRechargeDetail.getTradeNo();
    }

    @Override
    public String handleConsumeData(int orgId, int userId, int price) {
        OrgRechargeDetail orgRechargeDetail = new OrgRechargeDetail();
        Account orgAdmin = accountDao.selectOrgAdminAccount(orgId, null);
        orgRechargeDetail.setAccountId(orgAdmin.getAccountId());
        orgRechargeDetail.setAmount(price);
        orgRechargeDetail.setCreationDate(DateUtil.getCurrentTimeStamp());
        orgRechargeDetail.setOpType(OP_TYPE_ENTRY_USER);
        orgRechargeDetail.setStatus(1);
        orgRechargeDetail.setTradeNo(StringUtils.getRandomString(32));

        Map<String, Object> mapInfo = new HashMap<>();
        mapInfo.put("orgId", orgId);
        mapInfo.put("userId", userId);
        JSONObject jsonObj = JSONObject.fromObject(mapInfo);
        orgRechargeDetail.setParams(jsonObj.toString());
        orgRechargeDetailDao.insertOrgRechargeDetail(orgRechargeDetail);
        return orgRechargeDetail.getTradeNo();
    }

    @Override
    public String consumePhoneNumberCheck(TeleMarketing teleMarketing) {
        return orgRechargeDetailDao.consumePhoneNumberCheck(teleMarketing);
    }

    // 天贝扣费
    @Override
    public String handleConsumeTbRep(int accountId, Integer userId, int updatePric) {
        OrgRechargeDetail orgRechargeDetail = new OrgRechargeDetail();
        orgRechargeDetail.setAccountId(accountId);
        orgRechargeDetail.setAmount(updatePric);
        orgRechargeDetail.setCreationDate(DateUtil.getCurrentTimeStamp());
        orgRechargeDetail.setOpType(OP_TYPE_REPORT_TB);
        orgRechargeDetail.setStatus(1);
        orgRechargeDetail.setTradeNo(StringUtils.getRandomString(32));

        Map<String, Object> mapInfo = new HashMap<>();
        mapInfo.put("accountId", accountId);
        mapInfo.put("userId", userId);
        JSONObject jsonObj = JSONObject.fromObject(mapInfo);
        orgRechargeDetail.setParams(jsonObj.toString());
        orgRechargeDetailDao.insertOrgRechargeDetail(orgRechargeDetail);
        return orgRechargeDetail.getTradeNo();
    }

    // 借条数据扣费
    @Override
    public String handleConsumeYxRep(int accountId, Integer userId, int updatePric) {
        OrgRechargeDetail orgRechargeDetail = new OrgRechargeDetail();
        orgRechargeDetail.setAccountId(accountId);
        orgRechargeDetail.setAmount(updatePric);
        orgRechargeDetail.setCreationDate(DateUtil.getCurrentTimeStamp());
        orgRechargeDetail.setOpType(OP_TYPE_REPORT_YX);
        orgRechargeDetail.setStatus(1);
        orgRechargeDetail.setTradeNo(StringUtils.getRandomString(32));

        Map<String, Object> mapInfo = new HashMap<>();
        mapInfo.put("accountId", accountId);
        mapInfo.put("userId", userId);
        JSONObject jsonObj = JSONObject.fromObject(mapInfo);
        orgRechargeDetail.setParams(jsonObj.toString());
        orgRechargeDetailDao.insertOrgRechargeDetail(orgRechargeDetail);
        return orgRechargeDetail.getTradeNo();
    }

    // 多头数据扣费
    @Override
    public String handleConsumeTdRep(int accountId, int userId, int updatePric) {
        OrgRechargeDetail orgRechargeDetail = new OrgRechargeDetail();
        orgRechargeDetail.setAccountId(accountId);
        orgRechargeDetail.setAmount(updatePric);
        orgRechargeDetail.setCreationDate(DateUtil.getCurrentTimeStamp());
        orgRechargeDetail.setOpType(OP_TYPE_REPORT_TD);
        orgRechargeDetail.setStatus(1);
        orgRechargeDetail.setTradeNo(StringUtils.getRandomString(32));

        Map<String, Object> mapInfo = new HashMap<>();
        mapInfo.put("accountId", accountId);
        mapInfo.put("userId", userId);
        JSONObject jsonObj = JSONObject.fromObject(mapInfo);
        orgRechargeDetail.setParams(jsonObj.toString());
        orgRechargeDetailDao.insertOrgRechargeDetail(orgRechargeDetail);
        return orgRechargeDetail.getTradeNo();
    }

    @Override
    public String manualRechage(int accountId, Integer amount, Integer newType, int orgAccountId) {
        OrgRechargeDetail orgRechargeDetail = new OrgRechargeDetail();
        orgRechargeDetail.setAccountId(orgAccountId);
        orgRechargeDetail.setAmount(amount);
        orgRechargeDetail.setCreationDate(DateUtil.getCurrentTimeStamp());
        orgRechargeDetail.setOpType(newType);
        orgRechargeDetail.setStatus(1);
        orgRechargeDetail.setTradeNo(StringUtils.getRandomString(32));

        Map<String, Object> mapInfo = new HashMap<>();
        mapInfo.put("accountId", accountId);
        mapInfo.put("desc", "jbb manual");
        JSONObject jsonObj = JSONObject.fromObject(mapInfo);
        orgRechargeDetail.setParams(jsonObj.toString());
        orgRechargeDetailDao.insertOrgRechargeDetail(orgRechargeDetail);
        return orgRechargeDetail.getTradeNo();

    }

    // 小金条短信提醒扣费
    @Override
    public String handleConsumeXjlReMsg(int accountId, String phoneNumber, Integer smsPrice) {
        OrgRechargeDetail orgRechargeDetail = new OrgRechargeDetail();
        orgRechargeDetail.setAccountId(accountId);
        orgRechargeDetail.setAmount(smsPrice);
        orgRechargeDetail.setCreationDate(DateUtil.getCurrentTimeStamp());
        orgRechargeDetail.setOpType(OP_TYPE_REMIND_XJL);
        orgRechargeDetail.setStatus(1);
        orgRechargeDetail.setTradeNo(StringUtils.getRandomString(32));
        Map<String, Object> mapInfo = new HashMap<>();
        mapInfo.put("accountId", accountId);
        mapInfo.put("phoneNumber", phoneNumber);
        mapInfo.put("sendDate", DateUtil.getCurrentTime());
        JSONObject jsonObj = JSONObject.fromObject(mapInfo);
        orgRechargeDetail.setParams(jsonObj.toString());
        orgRechargeDetailDao.insertOrgRechargeDetail(orgRechargeDetail);
        return orgRechargeDetail.getTradeNo();
    }

}
