package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.AccountOpLogDao;
import com.jbb.mgt.core.domain.AccountOpLog;
import com.jbb.mgt.core.service.AccountOpLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账号操作日志service实现类
 * 
 * @author wyq
 * @date 2018-4-23 15:43:18
 *
 */
@Service("AccountOpLogService")
public class AccountOpLogServiceImpl implements AccountOpLogService {

    @Autowired
    private AccountOpLogDao accountOpLogServiceDao;

    @Override
    public void createOpLog(AccountOpLog accountOpLog) {
        accountOpLogServiceDao.insertOpLog(accountOpLog);

    }

    @Override
    public List<AccountOpLog> selectAccountOpLogByApplyId(Integer applyId) {
        return accountOpLogServiceDao.selectAccountOpLogByApplyId(applyId);
    }

    @Override
    public Integer getCount(Integer accountId, Integer[] ops, Boolean newtype ) {
         return accountOpLogServiceDao.getCount(accountId,ops,newtype);
    }

}
