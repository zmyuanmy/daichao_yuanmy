package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.PlatformMsgGroup;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.MsgToSalesDao;
import com.jbb.mgt.core.domain.Platform;
import com.jbb.mgt.core.domain.PlatformMsgVo;
import com.jbb.mgt.core.service.ChuangLanService;
import com.jbb.mgt.core.service.MsgToSalesService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Service("MsgToSalesService")
@Slf4j
public class MsgToSalesServiceImpl implements MsgToSalesService {
    @Autowired
    private ChuangLanService chuangLanService;
    @Autowired
    private MsgToSalesDao msgToSalesDao;

    @Override
    public void sendMsgToSales(Integer type) {
        Timestamp startTs = new Timestamp(DateUtil.getTodayStartTs());
        String signName = PropertyManager.getProperty("jbb.mgt.platform.msgSignName");

        if (type == 1) {
            sendMsgToSalesByBalance(startTs, signName, null);
        } else if (type == 2) {
            sendMsgToSalesByCnt(startTs, signName, null);
        }

    }

    // 预期数量
    private void sendMsgToSalesByCnt(Timestamp startDate, String signName, String CodeTemplate) {
        Integer cnt = PropertyManager.getIntProperty("jbb.mgt.platform.msgCnt", 0);
        CodeTemplate = PropertyManager.getProperty("jbb.platform.msgCnt.template");
        List<PlatformMsgVo> platformMsgVos = msgToSalesDao.getPlatformMsgVo(startDate, null, cnt);
        if (platformMsgVos != null && platformMsgVos.size() > 0) {
            for (int i = 0; i < platformMsgVos.size(); i++) {
                String phoneNumber = platformMsgVos.get(i).getPhoneNumber();
                List<Platform> platforms = platformMsgVos.get(i).getPlatforms();
                String s = "";
                for (int j = 0; j < platforms.size(); j++) {
                    s += "[" + platforms.get(j).getPlatformId() + "-" + platforms.get(j).getName() + "-"
                        + platforms.get(j).getShortName() + "]";
                }
                final String content = signName + StringUtil.replace(CodeTemplate, "CodeTemplate", s);
                Thread asyncThread = new Thread("asyncDataHandlerThread") {
                    public void run() {
                        log.debug("异步调用预设...");
                        try {
                            sendMsgCode(phoneNumber, content);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                };
                asyncThread.start();

            }
        }

    }

    // 余额
    private void sendMsgToSalesByBalance(Timestamp startDate, String signName, String CodeTemplate) {
        Timestamp startTs = new Timestamp(DateUtil.getTodayStartTs());
        Integer balance = PropertyManager.getIntProperty("jbb.mgt.platform.msgBalance", 100000);
        List<PlatformMsgGroup> platformMsgGroups = msgToSalesDao.getPlatformMsgGroup(startTs);
        for (int i = 0; i < platformMsgGroups.size(); i++) {
            if (platformMsgGroups.get(i).getTotalBalance() <= balance) {
                String[] phoneNumbers = null;
                if (!StringUtil.isEmpty(platformMsgGroups.get(i).getPhoneNumbers())) {
                    phoneNumbers = platformMsgGroups.get(i).getPhoneNumbers().split(",");
                }
                String s = platformMsgGroups.get(i).getGroupName();
                CodeTemplate = PropertyManager.getProperty("jbb.platform.msgBalance.template");
                final String content = signName + StringUtil.replace(CodeTemplate, "CodeTemplate", s);
                final String[] newPhoneNumbers = phoneNumbers;
                Thread asyncThread = new Thread("asyncDataHandlerThread") {
                    public void run() {
                        log.debug("异步调用余额...");
                        try {
                            sendMsgCode(newPhoneNumbers, content);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                };
                asyncThread.start();
            }
        }

    }

    private void sendMsgCode(String phoneNumber, String content) {

        String[] opMobiles = PropertyManager.getProperties("jbb.mgt.platform.op.mobiles");

        if (!StringUtil.isEmpty(phoneNumber) && !CommonUtil.inArray(phoneNumber, opMobiles)) {
            opMobiles = ArrayUtils.add(opMobiles, phoneNumber);

        }

        if (opMobiles == null || opMobiles.length == 0) {
            return;
        }

        for (int i = 0; i < opMobiles.length; i++) {
            chuangLanService.sendMsgCodeWithContent(opMobiles[i], content);
            log.debug(opMobiles[i] + ":" + content);
        }
    }

    private void sendMsgCode(String[] phoneNumber, String content) {

        String[] opMobiles = PropertyManager.getProperties("jbb.mgt.platform.op.mobiles");

        if (phoneNumber != null) {
            opMobiles = CommonUtil.getUnion(opMobiles, phoneNumber);

        }

        if (opMobiles == null || opMobiles.length == 0) {
            return;
        }

        for (int i = 0; i < opMobiles.length; i++) {
            chuangLanService.sendMsgCodeWithContent(opMobiles[i], content);
            log.debug(opMobiles[i] + ":" + content);
        }
    }
}
