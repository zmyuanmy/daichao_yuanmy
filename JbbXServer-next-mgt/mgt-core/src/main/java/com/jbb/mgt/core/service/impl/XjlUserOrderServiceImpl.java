package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.XjlApplyRecordDao;
import com.jbb.mgt.core.dao.XjlUserDao;
import com.jbb.mgt.core.dao.XjlUserOrderDao;
import com.jbb.mgt.core.domain.*;
import com.jbb.mgt.core.service.XjlUserOrderService;
import com.jbb.mgt.core.service.XjlUserService;
import com.jbb.mgt.server.core.util.StringUtils;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.DateUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service("XjlUserOrderService")
public class XjlUserOrderServiceImpl implements XjlUserOrderService {

    @Autowired
    private XjlUserOrderDao xjlUserOrderDao;

    @Autowired
    private XjlApplyRecordDao xjlApplyRecordDao;

    @Autowired
    private XjlUserDao xjlUserDao;

    private static Logger logger = LoggerFactory.getLogger(XjlUserOrderServiceImpl.class);

    private static DefaultTransactionDefinition NEW_TX_DEFINITION =
            new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private PlatformTransactionManager txManager;

    @Override
    public XjlUserOrder selectXjlUserOrder(int userId, int applyId, String cardNo, String payProductId, String type,
        Timestamp expiryDate) {
        return xjlUserOrderDao.selectXjlUserOrder(userId, applyId, cardNo, payProductId, type, expiryDate);
    }

    @Override
    public void insertXjlUserOrder(XjlUserOrder xjlUserOrder) {
        xjlUserOrderDao.insertXjlUserOrder(xjlUserOrder);
    }

    @Override
    public void updateXjlUserOrder(String orderId, String status) {
        xjlUserOrderDao.updateXjlUserOrder(orderId, status);
    }

    @Override
    public XjlUserOrder selectXjlUserOrderById(String orderId) {
        return xjlUserOrderDao.selectXjlUserOrderById(orderId);
    }

    @Override
    public boolean notifyHeLiPay(String orderId) {
        TransactionStatus txStatus = null;
        boolean flag = false;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            xjlUserOrderDao.updateXjlUserOrder(orderId, "FINISH");
            XjlUserOrder xjlUserOrder = xjlUserOrderDao.selectXjlUserOrderById(orderId);
            XjlApplyRecord applyRecord = xjlApplyRecordDao.selectXjlApplyRecordByApplyId(xjlUserOrder.getApplyId(), null, null);
            applyRecord.setStatus(99);//表示已完成
            applyRecord.setActualRepaymentDate(DateUtil.getCurrentTimeStamp());
            xjlApplyRecordDao.updateXjlApplyRecord(applyRecord);
            xjlUserDao.updateUserLoanCnt(applyRecord.getOrgId(), applyRecord.getUserId());
            txManager.commit(txStatus);
            txStatus = null;
            flag = true;
        } finally {
            // roll back not committed transaction (release user lock)
            rollbackTransaction(txStatus);
        }

        return flag;
    }

    private void rollbackTransaction(TransactionStatus txStatus) {
        if (txStatus == null) {
            return;
        }
        try {
            txManager.rollback(txStatus);
        } catch (Exception er) {
            logger.warn("Cannot rollback transaction", er);
        }
    }
}
