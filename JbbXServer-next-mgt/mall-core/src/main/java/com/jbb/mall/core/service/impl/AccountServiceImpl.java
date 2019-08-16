package com.jbb.mall.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jbb.mall.core.dao.AccountDao;
import com.jbb.mall.core.dao.domain.Account;
import com.jbb.mall.core.service.AccountService;
import com.jbb.server.common.exception.WrongUserKeyException;

@Service("AccountService")
public class AccountServiceImpl implements AccountService {
    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Account login(String userKey) throws WrongUserKeyException {
        if (userKey == null)
            throw new WrongUserKeyException();

        Account account = accountDao.authenticate(userKey);

        if (account == null) {
            throw new WrongUserKeyException();
        }

        loginProcess(account);

        return account;
    }

    private void loginProcess(Account account) {
        // 获取用户权限
        account.setPermissions(accountDao.getAccountPermissions(account.getAccountId()));
    }

 
}
