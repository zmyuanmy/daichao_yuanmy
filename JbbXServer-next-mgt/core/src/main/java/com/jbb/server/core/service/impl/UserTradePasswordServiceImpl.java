package com.jbb.server.core.service.impl;

import com.jbb.server.common.exception.AccessDeniedException;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.dao.AccountDao;
import com.jbb.server.core.dao.MessageCodeDao;
import com.jbb.server.core.domain.User;
import com.jbb.server.core.service.UserTradePasswordService;
import com.jbb.server.core.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by inspironsun on 2018/6/6
 */
@Service("UserTradePasswordService")
public class UserTradePasswordServiceImpl implements UserTradePasswordService {

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    private static Logger logger = LoggerFactory.getLogger(UserTradePasswordServiceImpl.class);

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private MessageCodeDao messageCodeDao;

    @Override
    public void setUserTradePassword(int userId, String password) {
        logger.debug(">setUserTradePassword ");
        User user = accountDao.getUser(userId);
        this.updateUserTradePassword(user, password);
        logger.debug("<setUserTradePassword ");
    }

    @Override
    public void updateUserTradePassword(int userId, String msgCode, String idCard, String password, String step) {
        logger.debug(">updateUserTradePassword");
        User user = accountDao.getUser(userId);
        String phoneNumber = user.getPhoneNumber();
        if (StringUtil.isEmpty(phoneNumber)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "phoneNumber");
        }
        boolean isMsgCodeOk = messageCodeDao.checkMsgCode(phoneNumber, msgCode, DateUtil.getCurrentTimeStamp());
        if (!isMsgCodeOk) {
            throw new AccessDeniedException("jbb.error.exception.accessDenied.msgCodeExpire");
        }
        // 检验身份证信息是否一致
        String userIdcard = user.getIdCardNo();
        if (StringUtil.isEmpty(userIdcard)) {
            throw new MissingParameterException("jbb.error.exception.idCardNotExist", "zh");
        }
        if (!idCard.equals(userIdcard)) {
            throw new WrongParameterValueException("jbb.error.exception.idCardError");
        }
        // 保存新的交易密码
        if (step.equals("1")) {
            // 第一步 只验证
        } else {
            // 第二步 保存
            if (!PasswordUtil.isValidTradePassword(password)) {
                throw new WrongParameterValueException("jbb.error.exception.tradePasswordError");
            }
            this.updateUserTradePassword(user, password);
        }
        logger.debug("<updateUserTradePassword");
    }

    private void updateUserTradePassword(User user, String password) {
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            user.setTradePassword(PasswordUtil.passwordHash(password));
            accountDao.updateUser(user);
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            // roll back not committed transaction (release user lock)
            rollbackTransaction(txStatus);
        }
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
