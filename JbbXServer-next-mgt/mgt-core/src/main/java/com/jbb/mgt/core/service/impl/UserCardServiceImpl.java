package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.UserCardDao;
import com.jbb.mgt.core.domain.UserCard;
import com.jbb.mgt.core.service.UserCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service("UserCardService")
public class UserCardServiceImpl implements UserCardService {

    @Autowired
    private UserCardDao userCardDao;

    private static Logger logger = LoggerFactory.getLogger(UserCardServiceImpl.class);

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private PlatformTransactionManager txManager;

    @Override
    public List<UserCard> selectUserCards(Integer userId, String payProductId) {
        return userCardDao.selectUserCards(userId, payProductId, false);
    }

    @Override
    public void insertUserCard(UserCard userCard) {
        userCardDao.insertUserCard(userCard);
    }

    @Override
    public void updateUserCard(UserCard userCard) {
        userCardDao.updateUserCard(userCard);
    }

    @Override
    public UserCard selectUserCardByCardNo(int userId, String cardNo, String payProductId) {
        return userCardDao.selectUserCardByCardNo(userId, cardNo, payProductId);
    }

    @Override
    public void acceptUserCard(Integer userId, String cardNo, String payProductId) {
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            userCardDao.removeUserCardAccept(userId);
            userCardDao.acceptUserCard(cardNo, payProductId);
            txManager.commit(txStatus);
            txStatus = null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("acceptUserCard false", e);
        } finally {
            // roll back not committed transaction
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

    @Override
    public UserCard selectAcceptUserCard(int userId, String payProductId) {
        List<UserCard> list = userCardDao.selectUserCards(userId, payProductId, true);
        UserCard u = null;
        if (list != null && list.size() > 0) {
            u = list.get(0);
        }
        return u;
    }
}
