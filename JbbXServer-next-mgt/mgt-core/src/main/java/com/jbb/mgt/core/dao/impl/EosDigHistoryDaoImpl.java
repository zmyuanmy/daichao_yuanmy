package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.EosDigHistoryDao;
import com.jbb.mgt.core.dao.mapper.EosDigHistoryMapper;
import com.jbb.mgt.core.domain.EosDigHistory;

@Repository("EosDigHistoryDao")
public class EosDigHistoryDaoImpl implements EosDigHistoryDao {

    @Autowired
    private EosDigHistoryMapper mapper;

    @Override
    public void insertEosDigHistory(EosDigHistory eosDigHistory) {
        mapper.insertEosDigHistory(eosDigHistory);
    }

    @Override
    public List<EosDigHistory> selectEosDigHistory() {
        return mapper.selectEosDigHistory();
    }

    @Override
    public EosDigHistory selectEosDigHistoryProfit(String tokenName) {
        return mapper.selectEosDigHistoryProfit(tokenName);
    }

    @Override
    public boolean checkEosDigHistory(String miner, String txNo) {
        return mapper.checkEosDigHistory(miner, txNo) > 0;
    }

}
