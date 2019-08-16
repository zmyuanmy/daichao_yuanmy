package com.jbb.mall.core.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jbb.mall.core.dao.AccountDao;
import com.jbb.mall.core.dao.domain.Account;
import com.jbb.mall.core.dao.mapper.AccountMapper;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;

@Repository("AccountDao")
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private AccountMapper mapper;

    @Override
    public Account authenticate(String userKey) {
        return mapper.selectAccountByKey(userKey, DateUtil.getCurrentTimeStamp());
    }

    @Override
    public int[] getAccountPermissions(int accountId) {
        return mapper.selectAccountPermissions(accountId);
    }

    

}
