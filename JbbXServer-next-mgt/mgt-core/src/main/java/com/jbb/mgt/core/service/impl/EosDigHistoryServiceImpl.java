package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.EosDigHistoryDao;
import com.jbb.mgt.core.domain.EosDigHistory;
import com.jbb.mgt.core.service.EosDigHistoryService;

@Service("EosDigHistoryService")
public class EosDigHistoryServiceImpl implements EosDigHistoryService {

    @Autowired
    private EosDigHistoryDao eosDigHistoryDao;

    @Override
    public void insertEosDigHistory(EosDigHistory eosDigHistory) {
        eosDigHistoryDao.insertEosDigHistory(eosDigHistory);
    }

    @Override
    public List<EosDigHistory> getEosDigHistory() {
        return eosDigHistoryDao.selectEosDigHistory();
    }

    @Override
    public EosDigHistory getEosDigHistoryProfit(String tokenName) {
        return eosDigHistoryDao.selectEosDigHistoryProfit(tokenName);
    }

    @Override
    public boolean checkEosDigHistory(String miner, String txNo) {
        return eosDigHistoryDao.checkEosDigHistory(miner, txNo);
    }

}
