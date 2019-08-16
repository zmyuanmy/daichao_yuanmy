package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.jbb.mgt.core.dao.AccountDao;
import com.jbb.mgt.core.dao.OrgRechargeDetailDao;
import com.jbb.mgt.core.dao.OrgRechargesDao;
import com.jbb.mgt.core.dao.TeleMarketingDao;
import com.jbb.mgt.core.domain.OrgRecharges;
import com.jbb.mgt.core.domain.TeleMarketing;
import com.jbb.mgt.core.domain.TeleMarketingDetail;
import com.jbb.mgt.core.domain.TeleMarketingInit;
import com.jbb.mgt.core.service.OrgRechargeDetailService;
import com.jbb.mgt.core.service.TeleMarketingService;
import com.jbb.server.common.PropertyManager;

/**
 * 电销批次service实现类
 * 
 * @author wyq
 * @date 2018/04/29
 */
@Service("TeleMarketingService")
public class TeleMarketingServiceImpl implements TeleMarketingService {

    private static Logger logger = LoggerFactory.getLogger(TeleMarketingServiceImpl.class);
    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    int unitPrice = PropertyManager.getIntProperty("jbb.mgt.phonecheck.price", 5);
    @Autowired
    private TeleMarketingDao dao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private OrgRechargesDao orgRechargesDao;
    @Autowired
    private OrgRechargeDetailDao orgRechargeDetailDao;
    @Autowired
    private PlatformTransactionManager txManager;

    @Override
    public List<TeleMarketing> selectTeleMarketings(int orgId) {
        return dao.selectTeleMarketings(orgId);
    }

    @Override
    public TeleMarketing selectTeleMarkByBatchId(int batchId, boolean b) {
        return dao.selectTeleMarkByBatchId(batchId, b);
    }

    @Override
    public TeleMarketing selectMaxTeleMarketings(int accountId) {
        return dao.selectMaxTeleMarketings(accountId);
    }

    @Override
    public void insertTeleMarketing(TeleMarketing batchInfo) {
        dao.insertTeleMarketing(batchInfo);
    }

    @Override
    public int insertMobiles(int batchId, List<TeleMarketingDetail> mobiles) {
        return dao.insertMobiles(batchId, mobiles);
    }

    @Override
    public boolean updateMobile(TeleMarketingDetail mobile) {
        return dao.updateMobile(mobile);
    }

    @Override
    public List<TeleMarketingDetail> selectMobiles(Integer batchId, int orgId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TeleMarketingInit> selectMobilesByAccountId(Integer accountId) {
        return dao.selectMobilesByAccountId(accountId);
    }

    @Override
    public int insertTeleInits(List<TeleMarketingInit> mobileInits) {
        return dao.insertTeleInits(mobileInits);
    }

    @Override
    public List<TeleMarketingInit> selectTeleInits(int accountId, boolean detail, Boolean isMarked) {
        return dao.selectTeleInits(accountId, detail, isMarked);
    }

    @Override
    public void updateTeleMarketingInitByIdAndOpCommet(Integer id, String opCommet) {
        dao.updateTeleMarketingInitByIdAndOpCommet(id, opCommet);
    }

    @Override
    public TeleMarketingInit selectTeleMarketingInitById(Integer id) {
        return dao.selectTeleMarketingInitById(id);
    }

    @Override
    public List<TeleMarketing> selectTeleMarkBystatus(int status) {
        return dao.selectTeleMarkBystatus(status);
    }

    @Override
    public void updateTeleMarketing(TeleMarketing teleMarketing) {
        dao.updateTeleMarketing(teleMarketing);
    }

    @Override
    public void finishTeleMarketing(TeleMarketing teleMarketing) {
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            dao.updateTeleMarketing(teleMarketing);
            int orgId = accountDao.selectAccountById(teleMarketing.getAccountId(), null).getOrgId();
            OrgRecharges o = orgRechargesDao.selectOrgRechargesForUpdate(orgId);
            int totalSmsExpense = o.getTotalSmsExpense() + teleMarketing.getCnt() * unitPrice;
            o.setTotalSmsExpense(totalSmsExpense);
            orgRechargeDetailDao.consumePhoneNumberCheck(teleMarketing);
            orgRechargesDao.updateOrgRecharges(o);
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            // roll back not committed transaction (release user lock)
            rollbackTransaction(txStatus);
        }
        logger.debug("<saveUserLoanRecord()");
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
